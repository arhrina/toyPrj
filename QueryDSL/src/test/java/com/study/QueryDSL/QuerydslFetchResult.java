package com.study.QueryDSL;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.QueryDSL.entity.Member;
import com.study.QueryDSL.entity.QMember;
import com.study.QueryDSL.entity.QTeam;
import com.study.QueryDSL.entity.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static com.study.QueryDSL.entity.QMember.*; // import static으로 사용

@SpringBootTest
@Transactional
public class QuerydslFetchResult {

    @Autowired
    EntityManager em;

    JPAQueryFactory jpaQueryFactory;

    @BeforeEach
    public void before() {

        jpaQueryFactory = new JPAQueryFactory(em); // field 변수 생성자 주입

        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 21, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 20, teamB);
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        // 초기화
        em.flush();
        em.clear();
    }

    @Test
    public void resultFetch() {
        List<Member> memberList = jpaQueryFactory
                .selectFrom(member)
                .fetch();

        Member findMember = jpaQueryFactory
                .selectFrom(member)
                .fetchOne();

        Member limit1 = jpaQueryFactory
                .selectFrom(member)
                .limit(1)
                .fetchOne(); // 결과가 둘 이상이면 NonUniqueResultException 발생

        Member fetchFirst = jpaQueryFactory
                .selectFrom(member)
                .fetchFirst();

        assertThat(limit1.equals(fetchFirst)); // 두개는 같은 결과임. object는 다름


        QueryResults<Member> results = jpaQueryFactory
                .selectFrom(member)
                .fetchResults(); // paging을 위한 쿼리. 성능이 중요한 페이지에서는 사용하지 말 것
        long id = jpaQueryFactory
                .selectFrom(member)
                .fetchCount();


        results.getTotal();
        results.getOffset();
        List<Member> content = results.getResults(); // 실제 내용물
    }

    /**
     * 정렬 순서
     * 1. 나이 내림차순
     * 2. 이름 오름차순
     * 단, 2에서 이름이 없으면 마지막 출력(nulls last)
     */
    @Test
    public void sort() {
        em.persist(new Member(null, 100));
        em.persist(new Member("member5", 100));
        em.persist(new Member("member6", 100));
        em.persist(new Member(null, 70));

        List<Member> fetchList = jpaQueryFactory
                .selectFrom(member)
                .where(member.age.eq(100))
                .orderBy(member.age.desc(), member.username.asc().nullsLast())
                .fetch();

        Member member5 = fetchList.get(0);
        Member member6 = fetchList.get(1);
        Member nullMember = fetchList.get(2);

        assertThat(member5.getUsername()).isEqualTo("member5");
        assertThat(member6.getUsername()).isEqualTo("member6");
        assertThat(nullMember.getUsername()).isNull();
    }

    @Test
    public void paging1() {
        List<Member> result = jpaQueryFactory
                .selectFrom(member)
                .orderBy(member.username.desc())
                .offset(1) // 시작값은 0
                .limit(2) // 가져오는 개수
                .fetch();

        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    public void paging2() {
        // query 2회 실행. count query, content용 query. 2개의 쿼리 모두 조건이 붙게 되므로, count query를 별도로 사용하는 경우는 따로 작성
        QueryResults<Member> result = jpaQueryFactory
                .selectFrom(member)
                .orderBy(member.username.desc())
                .offset(1) // 시작값은 0
                .limit(2) // 가져오는 개수
                .fetchResults();


        assertThat(result.getTotal()).isEqualTo(4);
        assertThat(result.getOffset()).isEqualTo(1);
        assertThat(result.getLimit()).isEqualTo(2);
        assertThat(result.getResults().size()).isEqualTo(2);
    }

    @Test
    public void aggregation() { // 집합은 튜플로 리턴된다. Tuple을 직접 쓰지 않고 Dto로 변환해서 사용
        List<Tuple> tuples = jpaQueryFactory
                .select(
                        member.count(),
                        member.age.sum(),
                        member.age.avg(),
                        member.age.max(),
                        member.age.min()
                )
                .from(member)
                .fetch();

        Tuple tuple = tuples.get(0);
        assertThat(tuple.get(member.count())).isEqualTo(4);
    }

    /**
     * 팀의 이름과 각 팀의 평균 연령
     * @throws Exception
     */
    @Test
    public void group() throws Exception {
        List<Tuple> result = jpaQueryFactory
                .select(QTeam.team.name, member.age.avg())
                .from(member)
                .join(member.team, QTeam.team)
                .groupBy(QTeam.team.name)
                // .having
                .fetch();

        Tuple teamA = result.get(0);
        Tuple teamB = result.get(1);

        assertThat(teamA.get(QTeam.team.name)).isEqualTo("teamA");
        assertThat(teamB.get(QTeam.team.name)).isEqualTo("teamB");
    }
}
