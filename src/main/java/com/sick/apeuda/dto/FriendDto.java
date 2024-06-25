package com.sick.apeuda.dto;

import com.sick.apeuda.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendDto {
    private Long friendId;
    private User user;
    private User toUser;
    private Boolean checkFriend;
}
