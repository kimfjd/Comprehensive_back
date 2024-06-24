// ChatMsgRepository
package com.sick.apeuda.repository;

import com.sick.apeuda.entity.ChatMsg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMsgRepository extends JpaRepository<ChatMsg, Long> {
    List<ChatMsg> findByRoomId(String roomId);
}
