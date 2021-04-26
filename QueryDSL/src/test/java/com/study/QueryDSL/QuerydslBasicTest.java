package com.study.QueryDSL;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
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
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class QuerydslBasicTest {

    @Autowired
    EntityManager em;

    @BeforeEach
    public void before() {
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
    public void startJPQL() {
        // member1 검색
        Member findByJPQL = em.createQuery("select m from Member m where m.username = :username", Member.class)
                .setParameter("username", "member1")
                .getSingleResult();

        // 문자열로 query가 생성되어 나가기 때문에, 실제 오류가 발생하는 시점은 유저가 실행했을 시 runtime error 발생
        assertThat(findByJPQL.getUsername()).isEqualTo("member1");
    }

    @Test
    public void startQuerydsl() {
        // member1 검색
        JPAQueryFactory queryFactory = new JPAQueryFactory(em); // queryFactory를 field level로 빼고 before에서 인스턴스 선언으로 대체 가능. 멀티 스레드의 동시성문제는 스프링이 해결
        QMember m = new QMember("m"); // m 문자열을 준 것이 구분하는 별칭. 중요하지 않다. QMember.member로 querydsl의 기본사양으로 내부에 만들어져 있어 사용가능하고 후자를 더 권장

        Member member1 = queryFactory.selectFrom(m)
                .from(m)
                .where(m.username.eq("member1")) // parameter bind type을 기재하지 않아도 알아서 binding 실행
                .fetchOne();
        // compile error가 발생하므로 ide에서 에러를 찾을 수 있어 사용자가 사용 전 미연에 방지
        assertThat(member1.getUsername()).isEqualTo("member1");
    }

    @Test
    public void compactStartQuerydsl() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
//        QMember m = QMember.member;

        Member findMember = queryFactory
                .selectFrom(QMember.member) // import static하여 더 짧게 사용 가능
                .from(QMember.member)
                .where(QMember.member.username.eq("member1")) // 기본값이 member1이고, 생성자로 별칭을 별도로 주는 경우는 알아서 변경된다. 같은 테이블을 join할 때 별도로 선언하여 사용
                .fetchOne();

        // querydsl은 JPQL의 builder. 결국 실행되는건 JPQL

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    public void search() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        Member member1 = queryFactory.selectFrom(QMember.member)
                .where(QMember.member.username.eq("member1")
                                .and((QMember.member.age.eq(10)))
//                        .or()
                )
                .fetchOne();
        assertThat(member1.getUsername()).isEqualTo("member1");
        // JPQL이 제공하는 모든 검색 조건 제공
        /*
        like("member%")
        contains("member") -> like '%member%'
        startsWith("member") -> like 'member%'
         */
    }

    @Test
    public void searchAndParam() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        Member member1 = queryFactory.selectFrom(QMember.member)
                .where(QMember.member.username.eq("member1"), // ... param은 and로 작동. 중간에 null이 들어가면 null 무시. 동적쿼리 사용시 good
                        (QMember.member.age.eq(10))
                )
                .fetchOne();
        assertThat(member1.getUsername()).isEqualTo("member1");
    }

    @Test
    public void constant() { // 상수 사용
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        List<Tuple> result = queryFactory
                .select(QMember.member.username, Expressions.constant("A"))
                .from(QMember.member)
                .fetch();
        for(Tuple t : result) {
            System.out.println("result = " + t);
        }
    }

    @Test
    public void concat() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        // username_age 로 문자열 더하기
        List<String> result = queryFactory
                .select(QMember.member.username.concat("_").concat(QMember.member.age.stringValue())) // type이 다르므로 변경. enum 처리시 stringValue 자주 사용하게 된다
                .from(QMember.member)
                .where(QMember.member.username.eq("member1"))
                .fetch();
        for(String s : result) {
            System.out.println("RESULT = " + s);
        }
    }
}
