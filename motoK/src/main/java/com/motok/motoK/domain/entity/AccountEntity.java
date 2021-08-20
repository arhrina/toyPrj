package com.motok.motoK.domain.entity;

import com.motok.motoK.domain.audit.AuditingDate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class AccountEntity extends AuditingDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seqNo;
    private Long 매출;
    private Long 지출;
}
