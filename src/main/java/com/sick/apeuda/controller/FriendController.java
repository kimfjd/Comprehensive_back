package com.sick.apeuda.controller;


import com.sick.apeuda.dto.FriendDto;
import com.sick.apeuda.entity.User;
import com.sick.apeuda.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friends")
public class FriendController {

    @Autowired
    private FriendService friendService;


    @GetMapping("/list")
    public List<FriendDto> getFriends(@RequestParam String userEmail) {
        User user = new User();
        user.setEmail(userEmail);
        return friendService.getFriends(user);
    }

    /**
     * 친구 요청을 보냅니다.
     *
     * @param userEmail   친구 요청을 보내는 사용자의 이메일
     * @param toUserEmail 친구 요청을 받는 사용자의 이메일
     */
    @PostMapping("/request")
    public void sendFriendRequest(@RequestParam String userEmail, @RequestParam String toUserEmail) {
        // 이메일을 사용하여 사용자 객체를 가져온다고 가정
        User user = new User();
        user.setEmail(userEmail);

        User toUser = new User();
        toUser.setEmail(toUserEmail);

        friendService.sendFriendRequest(user, toUser);
    }

    /**
     * 대기 중인 친구 요청 목록을 가져옵니다.
     *
     * @param userEmail 친구 요청을 받는 사용자의 이메일
     * @return 대기 중인 친구 요청 목록의 DTO 리스트
     */
    @GetMapping("/findrequest")
    public List<FriendDto> getPendingFriendRequests(@RequestParam String userEmail) {
        // 이메일을 사용하여 사용자 객체를 가져온다고 가정
        User user = new User();
        user.setEmail(userEmail);

        return friendService.getPendingFriendRequests(user);
    }

    /**
     * 친구 요청을 수락합니다.
     *
     * @param userEmail   친구 요청을 받는 사용자의 이메일
     * @param toUserEmail 친구 요청을 보낸 사용자의 이메일
     */
    @PostMapping("/accept")
    public void acceptFriendRequest(@RequestParam String userEmail, @RequestParam String toUserEmail) {
        User user = new User();
        user.setEmail(userEmail);

        User toUser = new User();
        toUser.setEmail(toUserEmail);

        friendService.acceptFriendRequest(user, toUser);
    }

    /**
     * 친구 요청을 거절하고 요청을 삭제합니다.
     *
     * @param userEmail   친구 요청을 받는 사용자의 이메일
     * @param toUserEmail 친구 요청을 보낸 사용자의 이메일
     */
    @PostMapping("/reject")
    public void rejectFriendRequest(@RequestParam String userEmail, @RequestParam String toUserEmail) {
        // 이메일을 사용하여 사용자 객체를 가져온다고 가정
        User user = new User();
        user.setEmail(userEmail);

        User toUser = new User();
        toUser.setEmail(toUserEmail);

        friendService.rejectFriendRequest(user, toUser);
    }


    @GetMapping("/delete")
    public void deleteFriend(@RequestParam String userEmail, @RequestParam String friendEmail) {
        // 이메일을 사용하여 사용자 객체를 가져온다고 가정
        User user = new User();
        user.setEmail(userEmail);
        User friend = new User();
        friend.setEmail(friendEmail);

        friendService.deleteFriend(user, friend);
    }
}
