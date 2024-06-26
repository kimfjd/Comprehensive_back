package com.sick.apeuda.repository;

import com.sick.apeuda.entity.Friend;
import com.sick.apeuda.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    // 특정 사용자(toUser)가 받은 대기 중인 친구 요청 목록을 반환합니다.
    List<Friend> findByToUserAndCheckFriend(User toUser, Boolean checkFriend);

    // 특정 사용자(user)와 친구가 될 사용자(toUser) 사이의 친구 요청을 삭제합니다.
    void deleteByUserAndToUser(User user, User toUser);

    // 친구 요청을 수락합니다 (checkFriend 필드를 true로 업데이트).
    @Modifying
    @Query("update Friend f set f.checkFriend = true where f.user = :user and f.toUser = :toUser")
    void acceptFriendRequest(@Param("user") User user, @Param("toUser") User toUser);

    // 특정 사용자(user)와 친구가 될 사용자(toUser) 사이의 친구 요청을 반환합니다.
    Friend findByUserAndToUser(User user, User toUser);


    // checkFriend가 true인 모든 친구 관계를 가져오는 메서드
    @Query("SELECT f FROM Friend f WHERE (f.user = :user OR f.toUser = :user) AND f.checkFriend = true")
    List<Friend> findAllFriends(@Param("user") User user);

    // 특정 사용자(user)와 친구(friend) 사이의 친구 관계를 삭제합니다.
    @Modifying
    @Query("delete from Friend f where (f.user = :user and f.toUser = :friend) or (f.user = :friend and f.toUser = :user)")
    void deleteFriend(@Param("user") User user, @Param("friend") User friend);
}