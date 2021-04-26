package com.study.QueryDSL;

import com.querydsl.jpa.JPAExpressions;
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

import static com.study.QueryDSL.entity.QMember.*;
import static com.study.QueryDSL.entity.QTeam.*;
import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
public class QuerydslSubquery {
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
     * 나이가 가장 많은 회원 조회
     */
    @Test
    public void subQuery() {

        QMember sMember = new QMember("memberSub"); // alias를 분리해줘야되므로 다시 타입 선언

        List<Member> result = queryFactory
                .selectFrom(member)
                .where(member.age.eq(
                        JPAExpressions.select(sMember.age.max())
                                .from(sMember)
                ))
                .fetch();

        assertThat(result).extracting("age").containsExactly(100);
    }

    /**
     * 나이가 평균 이상인 회원 조회
     */
    @Test
    public void subQueryGoe() {

        QMember sMember = new QMember("memberSub"); // alias를 분리해줘야되므로 다시 타입 선언

        List<Member> result = queryFactory
                .selectFrom(member)
                .where(member.age.goe(
                        JPAExpressions.select(sMember.age.avg())
                                .from(sMember)
                ))
                .fetch();

        assertThat(result).extracting("age").containsExactly(30, 50);
    }

    @Test
    public void subQueryIn() {

        QMember sMember = new QMember("memberSub"); // alias를 분리해줘야되므로 다시 타입 선언

        List<Member> result = queryFactory
                .selectFrom(member)
                .where(member.age.in(
                        JPAExpressions.select(sMember.age)
                                .from(sMember)
                                .where(sMember.age.gt(10))
                ))
                .fetch();

        assertThat(result).extracting("age").containsExactly(10, 20, 30, 50);
    }

    @Test
    public void selectSubQuery() {
        QMember sMember = new QMember("subMember");
        queryFactory
                .select(member.username,
                            JPAExpressions
                                .select(sMember.age.avg())
                                .from(sMember)
                )
                .from(member)
                .fetch();
    }
}
