package com.study.QueryDSL.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberDTO { // projection으로 2개만 콕 찝어 가져오려는 목적의 dto
    private String username;
    private int age;

    @QueryProjection // gradle compile -> dto도 Qtype으로 생성. 생성된 Qtype의 public method 사용. querydsl 아키텍처에 종속적이게 되는 문제
    public MemberDTO(String username, int age) {
        this.username = username;
        this.age = age;
    }
}
