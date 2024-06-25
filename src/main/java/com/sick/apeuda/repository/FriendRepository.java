package com.sick.apeuda.repository;

import com.sick.apeuda.entity.Friend;
import com.sick.apeuda.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    List<Friend> findByToUserAndCheckFriend(User toUser, Boolean checkFriend);

    void deleteByUserAndToUser(User user, User toUser);

    @Modifying
    @Query("update Friend f set f.checkFriend = true where f.user = :user and f.toUser = :toUser")
    void acceptFriendRequest(@Param("user") User user, @Param("toUser") User toUser);

    Friend findByUserAndToUser(User user, User toUser); // 추가된 메서드
}