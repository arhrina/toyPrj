package com.test.repository;

import com.test.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    public List<Member> findAll();
    public List<Member> findByNameAndAge(String name, int age);
    public Member findByName(String username);
}
