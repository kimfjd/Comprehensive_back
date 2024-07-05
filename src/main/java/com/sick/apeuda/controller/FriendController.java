package com.sick.apeuda.controller;


import com.sick.apeuda.dto.FriendDto;
import com.sick.apeuda.dto.MemberDto;
import com.sick.apeuda.entity.Member;
import com.sick.apeuda.service.FriendService;
import com.sick.apeuda.service.PostMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friends")
public class FriendController {

    @Autowired
    private FriendService friendService;
    private PostMsgService postMsgService;

    /**
     * 친구 요청을 보냅니다.
     *
     * @param memberEmail   친구 요청을 보내는 사용자의 이메일
     * @param toMemberEmail 친구 요청을 받는 사용자의 이메일
     */
    @PostMapping("/request")
    public void sendFriendRequest(@RequestParam String memberEmail, @RequestParam String toMemberEmail) {
        // 이메일을 사용하여 사용자 객체를 가져온다고 가정
        Member member = new Member();
        member.setEmail(memberEmail);

        Member toMember = new Member();
        toMember.setEmail(toMemberEmail);

        friendService.sendFriendRequest(member, toMember);
    }

    /**
     * 대기 중인 친구 요청 목록을 가져옵니다.
     *
     * @param memberEmail 친구 요청을 받는 사용자의 이메일
     * @return 대기 중인 친구 요청 목록의 DTO 리스트
     */

    @GetMapping("/findrequest")
    public ResponseEntity<List<FriendDto>> getPendingFriendRequests() {
        // 이메일을 사용하여 사용자 객체를 가져온다고 가정
        String memberEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = new Member();
        member.setEmail(memberEmail);

        List<FriendDto> friends = friendService.getPendingFriendRequests(member);
        return ResponseEntity.ok(friends);
    }



    /**
     * 친구 요청을 수락합니다.
     *
     * @param memberEmail   친구 요청을 받는 사용자의 이메일
     * @param toMemberEmail 친구 요청을 보낸 사용자의 이메일
     *
     */


    @GetMapping("/accept")
    public ResponseEntity<List<FriendDto>> acceptFriendRequest(@RequestParam String memberEmail) {
        String toMemberEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = new Member();
        member.setEmail(memberEmail);

        Member toMember = new Member();
        toMember.setEmail(toMemberEmail);

        List<FriendDto> friends = friendService.acceptFriendRequest(member, toMember);
        return ResponseEntity.ok(friends);
    }

    /**
     * 친구 요청을 거절하고 요청을 삭제합니다.
     *
     * @param memberEmail   친구 요청을 받는 사용자의 이메일
     * @param toMemberEmail 친구 요청을 보낸 사용자의 이메일
     */
    @GetMapping("/reject")
    public ResponseEntity<List<FriendDto>>rejectFriendRequest(@RequestParam String memberEmail) {
        String toMemberEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = new Member();
        member.setEmail(memberEmail);

        Member toMember = new Member();
        toMember.setEmail(toMemberEmail);

        List<FriendDto> friends = friendService.rejectFriendRequest(member, toMember);
        return ResponseEntity.ok(friends);
    }


    @GetMapping("/list")
    public ResponseEntity<List<FriendDto>> getFriends() {
        String memberEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = new Member();
        member.setEmail(memberEmail);
        List<FriendDto> friends = friendService.getFriends(member);
        return ResponseEntity.ok(friends);
    }


    @GetMapping("/delete")
    public ResponseEntity<List<FriendDto>> deleteFriend(@RequestParam String friendEmail) {
        // 현재 인증된 사용자의 이메일을 가져옴
        String memberEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        // 이메일을 사용하여 사용자 객체를 생성
        Member member = new Member();
        member.setEmail(memberEmail);
        Member friend = new Member();
        friend.setEmail(friendEmail);

        // 친구 삭제 서비스 호출
        friendService.deleteFriend(member, friend);

        // 성공적인 응답 반환
        return ResponseEntity.noContent().build();
    }
}
