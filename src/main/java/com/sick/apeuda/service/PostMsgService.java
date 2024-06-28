package com.sick.apeuda.service;

import com.sick.apeuda.dto.PostMsgDto;
import com.sick.apeuda.entity.Member;
import com.sick.apeuda.entity.PostMsg;
import com.sick.apeuda.repository.MemberRepository;
import com.sick.apeuda.repository.PostMsgRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostMsgService {
    private  final PostMsgRepository msgRepository;
    private  final MemberRepository memberRepository;

    @Transactional
    public PostMsg msgWrite(PostMsgDto postMsgDto) {
        Member receiver = memberRepository.findByEmail(postMsgDto.getReceiverMemberName())
                .orElseThrow(() -> new IllegalArgumentException("Receiver not found"));
        Member sender = memberRepository.findByEmail(postMsgDto.getSenderMemberName())
                .orElseThrow(() -> new IllegalArgumentException("Sender not found"));
        log.info("receiver = {}", receiver);
        log.info("sender = {}", sender);

        PostMsg postMsg = new PostMsg();
        postMsg.messageSave(sender, receiver, postMsgDto.getContent());
        postMsg.setReceiveTime(LocalDateTime.now()); // 현재 시간 설정
        postMsg.setReadStatus(false); // 기본값으로 false 설정

        msgRepository.save(postMsg);
        return postMsg;
    }
    @Transactional(readOnly = true)
    public List<PostMsg> receivedMessage(Member receiveMember, Member sendMember) {
        List<PostMsg> postMsgs = msgRepository.findAllByReceiveMemberAndSendMember(receiveMember, sendMember);
        return postMsgs;
    }
}