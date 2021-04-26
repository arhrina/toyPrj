package com.study.QueryDSL.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberDTO { // projection으로 2개만 콕 찝어 가져오려는 목적의 dto
    private String username;
    private int age;

    public MemberDTO(String username, int age) {
        this.username = username;
        this.age = age;
    }
}
