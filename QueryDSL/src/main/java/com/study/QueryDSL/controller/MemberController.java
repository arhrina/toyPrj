package com.study.QueryDSL.controller;

import com.study.QueryDSL.dto.MemberSearchCondition;
import com.study.QueryDSL.dto.MemberTeamDTO;
import com.study.QueryDSL.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberJpaRepository memberJpaRepository;

    @GetMapping("/v1/members")
    public List<MemberTeamDTO> searchMemberV1(MemberSearchCondition condition) {
        return memberJpaRepository.searchByWhereParam(condition);
    }
}
