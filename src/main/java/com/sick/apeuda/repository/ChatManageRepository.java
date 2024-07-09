//ChatManageRepository
package com.sick.apeuda.repository;

import com.sick.apeuda.entity.ChatManage;
import com.sick.apeuda.entity.ChatRoom;
import com.sick.apeuda.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatManageRepository extends JpaRepository<ChatManage, Long> {
    List<ChatManage> findByMember(Member member); // ChatManage 테이블에서 유저아이디 검색
    Optional<ChatManage> findByChatRoomAndMember(ChatRoom chatRoom, Member member); // 유저가 이미 참여하고 있는지 조회
    Optional<ChatManage> findByChatRoomAndMemberAndHost(ChatRoom chatRoom, Member member, boolean host); // 채팅방에 입장해있는 유저이고 호스트인지 DB 확인
}
