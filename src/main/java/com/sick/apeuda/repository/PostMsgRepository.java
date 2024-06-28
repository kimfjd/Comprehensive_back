package com.sick.apeuda.repository;

import com.sick.apeuda.entity.Member;
import com.sick.apeuda.entity.PostMsg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostMsgRepository extends JpaRepository<PostMsg,Long> {
    List<PostMsg> findAllBySendMember(Member member);
    List<PostMsg> findAllByReceiveMemberAndSendMemberOrderByReceiveTimeDesc(Member receiveMember, Member sendMember);
}
