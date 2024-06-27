package com.sick.apeuda.controller;


import com.sick.apeuda.dto.FriendDto;
import com.sick.apeuda.entity.Member;
import com.sick.apeuda.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friends")
public class FriendController {

    @Autowired
    private FriendService friendService;

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
    public List<FriendDto> getPendingFriendRequests(@RequestParam String memberEmail) {
        // 이메일을 사용하여 사용자 객체를 가져온다고 가정
        Member member = new Member();
        member.setEmail(memberEmail);

        return friendService.getPendingFriendRequests(member);
    }

    /**
     * 친구 요청을 수락합니다.
     *
     * @param memberEmail   친구 요청을 받는 사용자의 이메일
     * @param toMemberEmail 친구 요청을 보낸 사용자의 이메일
     */
    @PostMapping("/accept")
    public void acceptFriendRequest(@RequestParam String memberEmail, @RequestParam String toMemberEmail) {
        Member member = new Member();
        member.setEmail(memberEmail);

        Member toMember = new Member();
        toMember.setEmail(toMemberEmail);

        friendService.acceptFriendRequest(member, toMember);
    }

    /**
     * 친구 요청을 거절하고 요청을 삭제합니다.
     *
     * @param memberEmail   친구 요청을 받는 사용자의 이메일
     * @param toMemberEmail 친구 요청을 보낸 사용자의 이메일
     */
    @PostMapping("/reject")
    public void rejectFriendRequest(@RequestParam String memberEmail, @RequestParam String toMemberEmail) {
        // 이메일을 사용하여 사용자 객체를 가져온다고 가정
        Member member = new Member();
        member.setEmail(memberEmail);

        Member toMember = new Member();
        toMember.setEmail(toMemberEmail);

        friendService.rejectFriendRequest(member, toMember);
    }

    @GetMapping("/list")
    public List<FriendDto> getFriends(@RequestParam String memberEmail) {
        Member member = new Member();
        member.setEmail(memberEmail);
        return friendService.getFriends(member);
    }

    @GetMapping("/delete")
    public void deleteFriend(@RequestParam String memberEmail, @RequestParam String friendEmail) {
        // 이메일을 사용하여 사용자 객체를 가져온다고 가정
        Member member = new Member();
        member.setEmail(memberEmail);
        Member friend = new Member();
        friend.setEmail(friendEmail);

        friendService.deleteFriend(member, friend);
    }
}
