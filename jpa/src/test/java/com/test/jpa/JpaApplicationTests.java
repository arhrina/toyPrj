package com.test.jpa;

import com.test.entity.Member;
import com.test.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
@Transactional
class JpaApplicationTests {

	MemberRepository memberRepository;

	@Test
	void contextLoads() {
		memberRepository.save(new Member("member1", 10));
		memberRepository.save(new Member("member2", 18));

		Member member1 = memberRepository.findByName("member1");
		member1.setName("member3");
	}

}
