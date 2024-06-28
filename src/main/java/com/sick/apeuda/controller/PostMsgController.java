package com.sick.apeuda.controller;

import com.sick.apeuda.dto.PostMsgDto;
import com.sick.apeuda.entity.Member;
import com.sick.apeuda.entity.PostMsg;
import com.sick.apeuda.service.PostMsgService;
import com.sick.apeuda.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class PostMsgController {

    private final PostMsgService postMsgService;
    private final MemberService memberService;

    @PostMapping("/write")
    public ResponseEntity<PostMsg> writeMessage(@RequestBody PostMsgDto postMsgDto) {
        PostMsg postMsg = postMsgService.msgWrite(postMsgDto);
        return ResponseEntity.ok(postMsg);
    }

    @GetMapping("/received")
    public List<PostMsg> getReceivedMessages(@RequestParam("receiveEmail") String receiveEmail, @RequestParam("sendEmail") String sendEmail) {
        Member member = new Member();
        member.setEmail(receiveEmail);

        Member fromMember = new Member();
        fromMember.setEmail(sendEmail);

        return postMsgService.receivedMessage(member, fromMember);
    }
}
