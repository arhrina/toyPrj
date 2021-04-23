package com.study.QueryDSL;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.QueryDSL.entity.Hello;
import com.study.QueryDSL.entity.QHello;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*; // static 등록해서 클래스 명시 제거

@SpringBootTest
@Transactional
class QueryDslApplicationTests {
	/**
	 * 1. 최초 실행은 gradle에 위임한다. preference-gradle-gradle projects에 run using을 intellij로 변경하면 인텔리제이가 실행
	 * 2. LOMBOK은 플러그인을 설치하고 preference-annotation processors에서 enable annotation processing을 누르고 재시작해야 적용
	 * 3. querydsl은 q-type을 뽑아내서 사용한다. Entity를 만들고, gradle-other-build java 또는 build querydsl을 하면 gradle에 설정한 경로에 qtype이 생성되서 사용할 수 있다
	 * 4. library 버전이 올라가면 세부 내용이 변경될 수 있으므로 q type은 형상관리에 사용되지 않도록 주의
	 */

	// Spring 외에 다른 container를 사용할 일이 있으면 @PersistenceContext
	@Autowired
	EntityManager em;


	@Test
	void contextLoads() {
		Hello hello = new Hello();
		em.persist(hello);

		JPAQueryFactory query = new JPAQueryFactory(em);
		QHello qHello = new QHello("h");

		Hello result = query
				.selectFrom(qHello)
				.fetchOne();

		assertThat(result).isEqualTo(hello);
	}

}
