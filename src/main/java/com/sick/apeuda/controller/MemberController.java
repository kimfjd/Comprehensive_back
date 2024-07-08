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
//    @PutMapping("/membermodify/{email}")
//    public ResponseEntity<MemberResDto> memberModify(@RequestBody MemberReqDto memberReqDto) {
//        return ResponseEntity.ok(memberService.modifyMember(memberReqDto));
//    }
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



//    @PostMapping("/membermodify/{email}")
//    public ResponseEntity<MemberResDto> memberModify(@RequestBody MemberReqDto requestDto) {
//        return ResponseEntity.ok(memberService.memUpdate(requestDto));
//    }

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
