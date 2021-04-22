package com.test;

import com.test.entity.Member;
import com.test.repository.MemberRepository;
import com.test.repository.PureJpaRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional // (readOnly = true) 읽기 전용으로 만들어주는 transaction. select만 있을 때 사용. insert 등은 상정하지 않으므로 select에 최적화하여 성능 개선
@Rollback(value = true) // JPA는 테스트에서만 돌고, DB에는 반영하지 않는 옵션
public class JpaApplicationTests {

	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private PureJpaRepository pureJpaRepository;

	@Test
	void contextLoads() {
		memberRepository.save(new Member("member1", 10L));
		memberRepository.save(new Member("member2", 18L));
//		Optional<Member> byId = pureJpaRepository.findById(1);
//		try {
//			byId.orElseThrow(IllegalAccessException::new);
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		}

		Member member1 = memberRepository.findByName("member1");
		member1.setName("member3");

		assertEquals("member3", member1.getName());
	}

}
