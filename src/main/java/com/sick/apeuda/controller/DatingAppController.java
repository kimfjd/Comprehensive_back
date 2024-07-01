//DatingAppController.java
package com.sick.apeuda.controller;

import com.sick.apeuda.dto.MemberDto;
import com.sick.apeuda.entity.Member;
import com.sick.apeuda.service.DatingAppService;
import com.sick.apeuda.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/datingapp")
public class DatingAppController {

    @Autowired
    private FriendService friendService;
    @Autowired
    private DatingAppService datingAppService;

    @PostMapping("/request")
    public void sendFriendRequest(@RequestParam String memberEmail, @RequestParam String toMemberEmail) {
        // 이메일을 사용하여 사용자 객체를 가져온다고 가정
        Member member = new Member();
        member.setEmail(memberEmail);

        Member toMember = new Member();
        toMember.setEmail(toMemberEmail);

        friendService.sendFriendRequest(member, toMember);
    }

    @PostMapping("/cardlist") // 현재 localStorage에 저장된 이메일을 전달받고 그계정과 친구를 제외한 전체 유저정보 반환
    public ResponseEntity<List<MemberDto>> memberList(@RequestParam String myEmail){
        List<MemberDto> list = datingAppService.getMemberListExcludingCurrentUserAndFriends(myEmail);
        return ResponseEntity.ok(list);
    }
}
