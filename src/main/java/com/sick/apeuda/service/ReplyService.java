package com.sick.apeuda.service;

import com.sick.apeuda.entity.Reply;
import com.sick.apeuda.entity.User;
import com.sick.apeuda.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import static com.sick.apeuda.security.SecurityUtil.getCurrentMemberId;
@Service
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepository;
    // user단 만들어지고 해야됨 reply의 CUD만 이 서비스단에서 제공 R은 board단
//    public boolean insertReply(){
//        Reply reply =new Reply();
//        Long memberId = getCurrentMemberId();
//        User user = user
}
