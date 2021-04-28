package com.study.QueryDSL.repository;

import com.study.QueryDSL.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.TransactionScoped;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TransactionScoped
public class MemberRepositoryTest {
    @Autowired
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void basicTest() {
        Member member = new Member("member1", 10);
        memberRepository.save(member);

        Member findMember = memberRepository.findById(member.getId()).get();
        assertThat(findMember).isEqualTo(member);

        List<Member> resultList = memberRepository.findAll();
        assertThat(resultList).contains(member);

        List<Member> result2 = memberRepository.findByUsername("member1");
        assertThat(result2).contains(member);
    }
}
