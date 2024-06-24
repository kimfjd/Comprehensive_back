package com.sick.apeuda.repository;// ChatRoomRepository.interface
import com.sick.apeuda.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//package com.sick.apeuda.repository;
public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {
    Optional<ChatRoom> findByRoomId(String roomId);
    Optional<ChatRoom> findByRoomName(String roomName);
}