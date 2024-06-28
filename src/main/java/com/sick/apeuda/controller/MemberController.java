//MemberController.java
package com.sick.apeuda.controller;


import com.sick.apeuda.dto.MemberDto;
import com.sick.apeuda.dto.MemberReqDto;
import com.sick.apeuda.dto.MemberResDto;
import com.sick.apeuda.dto.TokenDto;
import com.sick.apeuda.entity.Member;
import com.sick.apeuda.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    // 회원 여부 확인
    @GetMapping("/check")
    public ResponseEntity<Boolean> isMember(@RequestParam String email) {
        boolean isEmail = memberService.isMember(email);
        return ResponseEntity.ok(isEmail);
    }



    // 회원정보 가져오기
    @GetMapping("/memberinfo")
    public ResponseEntity<MemberDto> memberInfo(@RequestParam String email) {
        MemberDto memberDto = memberService.getMemberInfo(email);
        return ResponseEntity.ok(memberDto);
    }

    // 회원 수정
    @PutMapping("/membermodify/{email}")
    public ResponseEntity<Boolean> memberModify(@RequestBody MemberDto memberDto) {
        Member member = Member.builder()
                .email(memberDto.getEmail())
                .password(memberDto.getPassword())
                .name(memberDto.getName())
                .identityNumber(memberDto.getIdentityNumber())
                .nickname(memberDto.getNickname())
                .profileImgPath(memberDto.getProfileImgPath())
                .skill(memberDto.getSkill())
                .myInfo(memberDto.getMyInfo())
                .build();
        boolean isTrue = memberService.saveMember(member);
        return ResponseEntity.ok(isTrue);
    }

    // 회원 삭제
    @DeleteMapping("/delmember/{email}")
    public ResponseEntity<Boolean> memberDelete(@PathVariable String email) {
        boolean isTrue = memberService.deleteMember(email);
        return ResponseEntity.ok(isTrue);
    }

    @GetMapping("/list")
    public ResponseEntity<List<MemberDto>> memberList(){
        List<MemberDto> list = memberService.getMemberList();
        return ResponseEntity.ok(list);

    }

}
