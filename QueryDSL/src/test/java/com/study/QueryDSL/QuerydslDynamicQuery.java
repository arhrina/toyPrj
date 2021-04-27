package com.study.QueryDSL;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
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
import static com.study.QueryDSL.entity.QMember.*;

@SpringBootTest
@Transactional
public class QuerydslDynamicQuery {

    @Autowired
    EntityManager em;

    JPAQueryFactory queryFactory;

    @BeforeEach
    public void before() {
        queryFactory = new JPAQueryFactory(em);

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
    public void dynamicQuery_BooleanBuilder() {
        String usernameParam = "member1";
        Integer ageParam = 10;

        List<Member> result = searchMember(usernameParam, ageParam);
        assertThat(result.size()).isEqualTo(1);
    }

    // 매개변수의 값이 null이면 해당 내용은 제외하고 검색하는 동적쿼리
    private List<Member> searchMember(String usernameParam, Integer ageParam) {

        BooleanBuilder builder = new BooleanBuilder();

        if(usernameParam != null) {
            builder.and(member.username.eq(usernameParam));
        }

        if(ageParam != null) {
            builder.and(member.age.eq(ageParam));
        }
        // builder.and(member.age.eq(ageParam).or(member.username.eq(usernameParam))); 이와 같이 and (age or name) 같은 동작도 가능능

       return queryFactory
                .selectFrom(member)
                .where(builder)
                .fetch();
    }

    @Test
    public void dynamicQuery_WhereParam() {
        String usernameParam = "member1";
        Integer ageParam = 10;

        List<Member> result = searchMemberWhere(usernameParam, ageParam);
        assertThat(result.size()).isEqualTo(1);
    }

    private List<Member> searchMemberWhere(String usernameParam, Integer ageParam) {
        return queryFactory
                .selectFrom(member)
                .where(usernameParamEq(usernameParam), ageParamEq(ageParam))
                .fetch();
    }

    private BooleanExpression usernameParamEq(String usernameParam) {
        return usernameParam != null ? member.username.eq(usernameParam) : null;
    }

    private BooleanExpression ageParamEq(Integer ageParam) {
        return ageParam != null ? member.age.eq(ageParam) : null;
    }



    /**
     * BooleanExpression 타입을 활용한 메서드로 다중 where 조건을 묶어 조립/재사용을 할 수 있다
     */
    private BooleanExpression allEq(String usernameParam, Integer ageParam) {
        return usernameParamEq(usernameParam).and(ageParamEq(ageParam));
    }


    @Test
    public void sqlFunction() { // sql의 함수 사용
        List<String> result = queryFactory
                .select(Expressions.stringTemplate("function('replace', {0}, {1}, {2})", member.username, "member1", "M"))
                .from(member)
                .fetch();
    }
}
