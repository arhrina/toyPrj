package com.motok.motoK.repository;

import com.motok.motoK.domain.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    public LocalDateTime findByCreatedDate(AccountEntity entity);
}
