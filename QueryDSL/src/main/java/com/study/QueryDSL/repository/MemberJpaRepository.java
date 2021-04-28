package com.study.QueryDSL.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.QueryDSL.dto.MemberSearchCondition;
import com.study.QueryDSL.dto.MemberTeamDTO;
import com.study.QueryDSL.dto.QMemberTeamDTO;
import com.study.QueryDSL.entity.Member;
import com.study.QueryDSL.entity.QMember;
import com.study.QueryDSL.entity.QTeam;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberJpaRepository { // 순수JPA

    private final EntityManager em;
    private JPAQueryFactory queryFactory;

    public MemberJpaRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public void save(Member member) {
        em.persist(member);
    }

    public Optional<Member> findById(Long id) {
        Member findMember = em.find(Member.class, id);
        return Optional.ofNullable(findMember);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findAll_Querydsl() {
        return queryFactory
                .selectFrom(QMember.member)
                .fetch();
    }

    public List<Member> findByUsername(String name) {
        return em.createQuery("select m from Member m where m.username = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

    public List<Member> findByUsername_Querydsl(String username) {
        return queryFactory
                .selectFrom(QMember.member)
                .where(QMember.member.username.eq(username))
                .fetch();
    }

    public List<MemberTeamDTO> searchByBuilder(MemberSearchCondition condition) {

        BooleanBuilder builder = new BooleanBuilder();
        if(StringUtils.hasText(condition.getUsername())) { // null, "" 모두 체크
            builder.and(QMember.member.username.eq(condition.getUsername()));
        }

        if (StringUtils.hasText(condition.getTeamName())) {
            builder.and(QTeam.team.name.eq(condition.getTeamName()));
        }

        if (condition.getAgeGoe() != null) {
            builder.and(QMember.member.age.goe(condition.getAgeGoe()));
        }

        if (condition.getAgeLoe() != null) {
            builder.and(QMember.member.age.loe(condition.getAgeLoe()));
        }

        return queryFactory
                .select(new QMemberTeamDTO(
                        QMember.member.id.as("memberId"),
                        QMember.member.username,
                        QMember.member.age,
                        QTeam.team.id.as("teamId"),
                        QTeam.team.name.as("teamName")
                ))
                .from(QMember.member)
                .leftJoin(QMember.member.team, QTeam.team)
                .where(builder)
                .fetch();
    }

    public List<MemberTeamDTO> searchByWhereParam(MemberSearchCondition condition) {
        return queryFactory
                .select(new QMemberTeamDTO(
                        QMember.member.id.as("memberId"),
                        QMember.member.username,
                        QMember.member.age,
                        QTeam.team.id.as("teamId"),
                        QTeam.team.name.as("teamName")
                ))
                .from(QMember.member)
                .leftJoin(QMember.member.team, QTeam.team)
                .where(
                        usernameEq(condition.getUsername()),
                        teamNameEq(condition.getTeamName()),
                        ageGoe(condition.getAgeGoe()),
                        ageLoe(condition.getAgeLoe())
                        )
                .fetch();
    }

    public List<Member> searchEntityByWhereParam(MemberSearchCondition condition) {
        return queryFactory
                .selectFrom(QMember.member)
                .leftJoin(QMember.member.team, QTeam.team)
                .where(
                        usernameEq(condition.getUsername()),
                        teamNameEq(condition.getTeamName()),
                        ageGoe(condition.getAgeGoe()),
                        ageLoe(condition.getAgeLoe())
                )
                .fetch();
    }

    // 기본형 Predicate보다 BooleanExpression이 조합이 가능하고, 재사용이 가능하므로 더 낫다
    private BooleanExpression ageBetween(int ageLoe, int ageGoe) {
        return ageGoe(ageGoe).and(ageLoe(ageLoe));
    }


    private BooleanExpression ageLoe(Integer ageLoe) {
        return ageLoe != null ? QMember.member.age.loe(ageLoe) : null;
    }

    private BooleanExpression ageGoe(Integer ageGoe) {
        return ageGoe != null ? QMember.member.age.goe(ageGoe) : null;
    }

    private BooleanExpression teamNameEq(String teamName) {
        return StringUtils.hasText(teamName) ? QTeam.team.name.eq(teamName) : null;
    }

    private BooleanExpression usernameEq(String username) {
        return StringUtils.hasText(username) ? QMember.member.username.eq(username) : null;
    }


}
