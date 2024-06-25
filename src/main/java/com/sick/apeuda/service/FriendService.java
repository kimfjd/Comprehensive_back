package com.sick.apeuda.service;

import com.sick.apeuda.dto.FriendDto;
import com.sick.apeuda.entity.Friend;
import com.sick.apeuda.entity.User;
import com.sick.apeuda.repository.FriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FriendService {

    @Autowired
    private FriendRepository friendRepository;

    /**
     * 친구 요청을 보냅니다.
     * @param user 친구 요청을 보내는 사용자 객체
     * @param toUser 친구 요청을 받는 사용자 객체
     */
    @Transactional
    public void sendFriendRequest(User user, User toUser) {
        Friend friend = new Friend();
        friend.setUser(user);
        friend.setToUser(toUser);
        friendRepository.save(friend);
    }

    /**
     * 대기 중인 친구 요청 목록을 가져옵니다.
     * @param user 친구 요청을 받는 사용자 객체
     * @return 대기 중인 친구 요청 목록의 DTO 리스트
     */
    public List<FriendDto> getPendingFriendRequests(User user) {
        List<Friend> friends = friendRepository.findByToUserAndCheckFriend(user, false);
        return friends.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * 친구 요청을 수락합니다.
     * @param user 친구 요청을 받는 사용자 객체
     * @param toUser 친구 요청을 보낸 사용자 객체
     */
    @Transactional
    public void acceptFriendRequest(User user, User toUser) {
        // friendRepository를 사용하여 특정 사용자(user)와 친구가 될 사용자(toUser) 사이의 친구 요청을 찾습니다.
        Friend friend = friendRepository.findByUserAndToUser(user, toUser);

        // 만약 친구 요청이 존재하면 (null이 아니면)
        if (friend != null) {
            // 친구 요청의 checkFriend 필드를 true로 설정합니다.
            friend.setCheckFriend(true);

            // friendRepository를 사용하여 변경된 상태를 데이터베이스에 저장합니다.
            friendRepository.save(friend);
        }
    }

    /**
     * 친구 요청을 거절하고 요청을 삭제합니다.
     * @param user 친구 요청을 받는 사용자 객체
     * @param toUser 친구 요청을 보낸 사용자 객체
     */
    @Transactional
    public void rejectFriendRequest(User user, User toUser) {
        friendRepository.deleteByUserAndToUser(user, toUser);
    }

    /**
     * Friend 엔티티를 FriendDto로 변환합니다.
     * @param friend Friend 엔티티 객체
     * @return 변환된 FriendDto 객체
     */
    private FriendDto convertToDto(Friend friend) {
        FriendDto friendDTO = new FriendDto();
        friendDTO.setFriendId(friend.getFriendId());
        friendDTO.setUser(friend.getUser());
        friendDTO.setToUser(friend.getToUser());
        friendDTO.setCheckFriend(friend.getCheckFriend());
        return friendDTO;
    }
}
