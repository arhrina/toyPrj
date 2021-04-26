package com.study.QueryDSL;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.QueryDSL.entity.Member;
import com.study.QueryDSL.entity.QMember;
import com.study.QueryDSL.entity.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import java.util.List;

import static com.study.QueryDSL.entity.QMember.*;
import static com.study.QueryDSL.entity.QTeam.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class QuerydslJoin {

    @Autowired
    EntityManager em;

    JPAQueryFactory queryFactory;

    @BeforeEach
    public void before() {

        queryFactory = new JPAQueryFactory(em); // field 변수 생성자 주입

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

    /**
     * Team A에 소속된 모든 회원 조회
     */
    @Test
    public void join() {
        List<Member> result = queryFactory
                .selectFrom(member)
                .join(member.team, team) // ANSI SQL INNER JOIN
                // .innerjoin, .leftjoin, .rightjoin
                .where(team.name.eq("teamA"))
                .fetch();

        assertThat(result)
                .extracting("username")
                .containsExactly("member1", "member2");
    }

    @Test
    public void theta_join() { // 연관관계 없이 하는 조인. 외부 조인(outer) 불가 -> hibernate 5 이후에서 on을 통해서만 가능
        em.persist(new Member("teamA"));
        em.persist(new Member("teamB"));

        queryFactory
                .select(member)
                .from(member, team) // cardinality join
                .where(member.username.eq(team.name)) // 사람이름과 팀명이 같은 조건
                .fetch();
    }

    /**
     * 회원과 팀 조인. 팀 이름이 teamA인 팀, 모든 회원
     *
     * JPQL String : select m, t from Member m left join m.team t on t.name = 'teamA'
     */
    @Test
    public void join_on_filtering() {
        List<Tuple> result = queryFactory
                .select(member, team) // select type이 여러개라 tuple type으로 ret
                .from(member)
                .leftJoin(member.team, team).on(team.name.eq("teamA")) // inner join where와 inner join on의 결과는 같음. outer join의 경우에만 on 사용하도록
                .fetch();

        for(Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }
    }

    @Test
    public void join_on_no_relation() { // 연관관계 없이 하는 조인
        em.persist(new Member("teamA"));
        em.persist(new Member("teamB"));

        List<Tuple> list = queryFactory
                .select(member, team)
                .from(member) // cardinality join
                .leftJoin(team).on(member.username.eq(team.name)) // member.team, team을 전달인자로 넣어주면 pk와 fk를 매칭해주는데 하나만 하면 해당 사항 없다
                .fetch();
        for(Tuple tuple : list) {
            System.out.println("tuple = " + tuple);
        }
    }

    @PersistenceUnit
    EntityManagerFactory emf;

    @Test
    public void fetchJoinNo() {
        em.flush();
        em.clear();

        Member findMember = queryFactory
                .selectFrom(member)
                .where(member.username.eq("member1"))
                .fetchOne();

        assertThat(emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam())).isFalse(); // fetch join 미적용이므로 loading이 되지 않은 상태여야 한다
    }

    @Test
    public void fetchJoinYes() {
        em.flush();
        em.clear();

        Member findMember = queryFactory
                .selectFrom(member)
                .join(member.team, team).fetchJoin()
                .where(member.username.eq("member1"))
                .fetchOne();

        assertThat(emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam())).isTrue(); // fetch join 적용이므로 loading이 된 상태여야 한다
    }
}
