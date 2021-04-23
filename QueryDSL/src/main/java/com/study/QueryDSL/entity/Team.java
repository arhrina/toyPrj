package com.study.QueryDSL.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA는 기본생성자가 필요하다. protected level까지
@ToString(of = {"id", "name"}) // 환형무한참조를 피하기 위해 Member를 보면 안된다
public class Team {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @OneToMany(mappedBy = "team") // 연관관계 주인을 참조. 1 테이블
    List<Member> members = new ArrayList<>();

    public Team(String name) {
        this.name = name;
    }
}
