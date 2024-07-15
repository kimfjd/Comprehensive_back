//MemberController.java
package com.sick.apeuda.controller;


import com.sick.apeuda.dto.MemberDto;
import com.sick.apeuda.dto.MemberReqDto;
import com.sick.apeuda.dto.MemberResDto;
import com.sick.apeuda.dto.TokenDto;
import com.sick.apeuda.entity.Member;
import com.sick.apeuda.repository.MemberRepository;
import com.sick.apeuda.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;



    // 회원정보 가져오기
    @GetMapping("/memberinfo2")
    public ResponseEntity<MemberDto> memberInfo2 (){
        MemberDto memberDto = memberService.getMemberInfo(SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.ok(memberDto);
    }

    @PostMapping("/membermodify")
    public ResponseEntity<MemberResDto> memberModify(@RequestBody MemberReqDto memberReqDto) {
        try {
            log.info("회원 정보 수정 요청: {}", memberReqDto.getEmail());
            Member member = memberRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(
                    () -> new RuntimeException("해당 회원이 존재하지 않습니다.")
            );
            memberReqDto.updateMember(member, passwordEncoder);
            log.info("회원 정보 수정 완료: {}", member);
            return ResponseEntity.ok(MemberResDto.of(memberRepository.save(member)));
        } catch (Exception e) {
            log.error("회원 정보 수정 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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
