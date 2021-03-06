package com.test.repository;

import com.test.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
    public List<Member> findByNameAndAge(String name, int age);
    public Member findByName(String username);
}
