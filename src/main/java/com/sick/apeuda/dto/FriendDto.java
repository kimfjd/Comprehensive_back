package com.sick.apeuda.dto;

import com.sick.apeuda.entity.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendDto {
    private Long friendId;
    private Member member;
    private Member toMember;
    private Boolean checkFriend;
}
