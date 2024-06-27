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
     * @throws IllegalStateException 이미 친구인 경우 예외를 발생시킵니다.
     */
    @Transactional
    public void sendFriendRequest(User user, User toUser) {
        //자기 자신에게 신청 불가
        if (user.getEmail().equals(toUser.getEmail())) {
            throw new IllegalStateException("자기 자신에게 친구 요청을 보낼 수 없습니다.");
        }

        // 이미 친구인 경우 친구 요청을 보내지 않도록 합니다.
        if (areFriends(user, toUser)) {
            throw new IllegalStateException("이미 친구인 상태입니다.");
        }

        Friend existingRequest = friendRepository.findByUserAndToUser(user, toUser);
        if (existingRequest != null && !existingRequest.getCheckFriend()) {
            throw new IllegalStateException("이미 친구 요청을 보냈습니다.");
        }

        // 동일한 사용자에게 요청을 여러 번 보내지 않도록 체크
        Friend existingRequestToUser = friendRepository.findByUserAndToUser(toUser, user);
        if (existingRequestToUser != null && !existingRequestToUser.getCheckFriend()) {
            throw new IllegalStateException("이 사용자에게 이미 친구 요청을 받았습니다.");
        }

        // 새로운 친구 요청을 생성하고 저장합니다.
        Friend friend = new Friend();
        friend.setUser(user);
        friend.setToUser(toUser);
        friendRepository.save(friend);
    }




    /**
     * 사용자의 친구 목록을 가져옵니다.
     * @param user 친구 목록을 가져올 사용자 객체
     * @return 사용자의 친구 목록의 DTO 리스트
     */
    public List<FriendDto> getFriends(User user) {
        List<Friend> friends = friendRepository.findAllFriends(user);
        return friends.stream()
                .map(friend -> {
                    // 상대방을 찾습니다.
                    User friendUser = friend.getUser().equals(user) ? friend.getToUser() : friend.getUser();
                    return convertToFriendDto(friendUser, friend);
                })
                .map(friendDto -> {
                    // 자신의 아이디를 기반으로 비교하여 필터링
                    if (friendDto.getUser().getEmail().equals(user.getEmail())) {
                        friendDto.setUser(null);
                    }
                    if (friendDto.getToUser().getEmail().equals(user.getEmail())) {
                        friendDto.setToUser(null);
                    }
                    return friendDto;
                })
                .collect(Collectors.toList());
    }

    /**
     * Friend 엔티티를 FriendDto로 변환합니다.
     * @param friendUser 친구 관계의 상대방 사용자 객체
     * @param friend Friend 엔티티 객체
     * @return 변환된 FriendDto 객체
     */
    private FriendDto convertToFriendDto(User friendUser, Friend friend) {
        FriendDto friendDTO = new FriendDto();
        friendDTO.setFriendId(friend.getFriendId());
        friendDTO.setUser(friend.getUser()); // 추가
        friendDTO.setToUser(friend.getToUser()); // 추가
        friendDTO.setCheckFriend(friend.getCheckFriend());
        return friendDTO;
    }


    /**
     * 두 사용자가 친구인지 확인합니다.
     * @param user 첫 번째 사용자 객체
     * @param toUser 두 번째 사용자 객체
     * @return 두 사용자가 친구인 경우 true, 그렇지 않은 경우 false
     */
    public boolean areFriends(User user, User toUser) {
        // 두 사용자가 친구인지 확인합니다 (양방향 확인).
        Friend friend1 = friendRepository.findByUserAndToUser(user, toUser);
        Friend friend2 = friendRepository.findByUserAndToUser(toUser, user);

        // 둘 중 하나라도 친구 관계가 성립되면 true 반환
        if ((friend1 != null && friend1.getCheckFriend()) ||
                (friend2 != null && friend2.getCheckFriend())) {
            return true;
        }
        return false;
    }

    /**
     * 대기 중인 친구 요청 목록을 가져옵니다.
     * @param user 친구 요청을 받는 사용자 객체
     * @return 대기 중인 친구 요청 목록의 DTO 리스트
     */
    public List<FriendDto> getPendingFriendRequests(User user) {
        // 사용자가 받은 대기 중인 친구 요청 목록을 가져옵니다.
        List<Friend> friends = friendRepository.findByToUserAndCheckFriend(user, false);
        // Friend 엔티티를 FriendDto로 변환하여 반환합니다.
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
        // 특정 사용자(user)와 친구가 될 사용자(toUser) 사이의 친구 요청을 삭제합니다.
        friendRepository.deleteByUserAndToUser(user, toUser);
    }

    /**
     * Friend 엔티티를 FriendDto로 변환합니다.
     * @param friend Friend 엔티티 객체
     * @return 변환된 FriendDto 객체
     */
    private FriendDto convertToDto(Friend friend) {
        // Friend 엔티티의 필드를 FriendDto로 복사하여 반환합니다.
        FriendDto friendDTO = new FriendDto();
        friendDTO.setFriendId(friend.getFriendId());
        friendDTO.setUser(friend.getUser());
        friendDTO.setToUser(friend.getToUser());
        friendDTO.setCheckFriend(friend.getCheckFriend());
        return friendDTO;
    }

    /**
     * 친구 관계를 삭제합니다.
     * @param user 친구 관계를 삭제할 사용자 객체
     * @param friend 친구 관계를 삭제할 친구 객체
     */
    @Transactional
    public void deleteFriend(User user, User friend) {
        // 친구 관계를 삭제합니다.
        friendRepository.deleteFriend(user, friend);
    }
}