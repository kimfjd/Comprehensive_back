// ChatRoomRepository.interface
package com.sick.apeuda.repository;
import com.sick.apeuda.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {
    Optional<ChatRoom> findByRoomId(String roomId);
    Optional<ChatRoom> findByRoomName(String roomName);
}