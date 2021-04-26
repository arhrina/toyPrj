package com.study.QueryDSL.dto;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
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

import static org.assertj.core.api.Assertions.*;
import static com.study.QueryDSL.entity.QMember.*; // import static으로 사용

@Transactional
@SpringBootTest
class QuerydslTOdto {
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
    public void findDtoByJPQL() {
        // JPQL new operation 문법. 패키지명을 다 적어야되고 생성자 방식만 지원
        List<MemberDTO> resultList = em.createQuery(
                "select new com.study.QueryDSL.dto.MemberDTO(m.username, m.age)" +
                        " from Member m", MemberDTO.class)
                .getResultList();
        for(MemberDTO m : resultList) {
            System.out.println("memberDTO = " + m);
        }
    }

    /**
     * Querydsl은 3가지 방법으로 dto로 변경
     * 1. 프로퍼티 접근
     * 2. 필드 직접 접근
     * 3. 생성자 사용
     */

    @Test
    public void findDTOBySetter() {
        queryFactory
                .select(
                        Projections.bean(MemberDTO.class), // NoSuchMethodException -> 기본생성자 필요
                        member.username,
                        member.age
                )
                .from(member)
                .fetch();
    }

    @Test
    public void findDTOByField() {
        queryFactory
                .select(
                        Projections.fields(MemberDTO.class), // DTO의 field에 직접
                        member.username,
                        member.age
                )
                .from(member)
                .fetch();
    }

    @Test
    public void findDTOByConstructor() {
        queryFactory
                .select(
                        Projections.constructor(MemberDTO.class), // 이하 member.username, member.age랑 MemberDTO랑 type이 맞아야 실행됨
                        member.username,
                        member.age
                )
                .from(member)
                .fetch();
    }

    @Test
    public void findUserDTOByField() { // 필드와 엔티티 이름이 다른 dto는 이름을 맞춰야된다. 생성자는 type만 맞추면 된다
        QMember sMember = new QMember("memberSub");
        queryFactory
                .select(
                        Projections.fields(UserDTO.class), // 필드 이름이 같아야 됨
                        member.username.as("name"), // alias 지정

                        // subquery로 projection하여 alias 지정
                        ExpressionUtils.as(
                                JPAExpressions
                                .select(sMember.age.max())
                                .from(member), "age")
                        )
                .from(member)
                .fetch();
    }
}