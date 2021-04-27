package com.study.QueryDSL.repository;

import com.study.QueryDSL.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * querydsl
 *
 * 1. spring data jparepository extends
 * 2. interface repository makes
 * 3. class name is interface name + Impl -> interface repository implemenets, implement it
 * 4. no1 repository extends no2 interface
 */
public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
    List<Member> findByUsername(String username);
    // select m from Member m where m.username = :username
}
