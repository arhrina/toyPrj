package com.motok.motoK.domain.entity;

import com.motok.motoK.domain.audit.AuditingDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CustomerEntity extends AuditingDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seqNo;
    private Long 주행거리;
    private String 기종;
    private String 차량정보;
    private String 고객이름;
    private String 고객연락처;
    private String 비고;
    private String 정비내역;
    private String 작업자;
    // TODO 생성일, 최종 업데이트일
}
