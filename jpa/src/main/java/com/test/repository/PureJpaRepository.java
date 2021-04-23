package com.test.repository;

import com.test.entity.Member;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PureJpaRepository extends CrudRepository<Member, Integer> {
}
