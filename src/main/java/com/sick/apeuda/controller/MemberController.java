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
import org.springframework.security.core.context.SecurityContextHolder;
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

    // 회원정보 가져오기 토큰 사용해서 이메일로 조회(localStroage에 이메일 저장할 필요x) Axios회원정보 불러오실때 이걸로 하시면 됩니다
    // 아직 프론트랑은 안 이어봐서 위에꺼는 살려놨어요
    @GetMapping("/memberinfo2")
    public ResponseEntity<MemberDto> memberInfo2 (){
        MemberDto memberDto = memberService.getMemberInfo(SecurityContextHolder.getContext().getAuthentication().getName());
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
    @GetMapping("/delmember")
    public ResponseEntity<Boolean> memberDelete() {
        boolean isTrue = memberService.deleteMember(SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.ok(isTrue);
    }

    @GetMapping("/list")
    public ResponseEntity<List<MemberDto>> memberList(){
        List<MemberDto> list = memberService.getMemberList();
        return ResponseEntity.ok(list);

    }

}
