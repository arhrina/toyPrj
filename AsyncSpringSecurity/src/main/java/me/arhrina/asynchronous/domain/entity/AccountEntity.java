package me.arhrina.asynchronous.domain.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class AccountEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String accoutnId;
    private String password;
}
