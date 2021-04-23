package com.study.QueryDSL.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA는 기본생성자가 필요하다. protected level까지
@ToString(of = {"id", "username", "age"}) // TEAM은 들어가면 안된다. 환형 무한참조에 빠진다
public class Member { // 1 멤버는 1개의 팀에만 속할 수 있다

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String username;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id") // FK 칼럼 이름
    private Team team; // 연관관계 주인에 세팅. N 테이블


    /*
    기본생성자 3개
     */
    public Member(String username, int age, Team team) {
        this.username = username;
        this.age = age;
        if(team != null) {
            changeTeam(team);
        }
    }

    public Member(String username, int age) {
        this(username, age, null);
    }

    public Member(String username) {
        this(username, 0);
    }
    /*
    기본생성자 3개 완성
     */

    private void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }
}
