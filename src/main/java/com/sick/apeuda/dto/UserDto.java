package com.sick.apeuda.dto;

import com.sick.apeuda.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDto {
    private String email;
    private String password;
    private String name;
    private String nickname;
    private String identityNumber;
    private String profileImgPath;
    private String skill;
    private String myInfo;

    public static UserDto of(User user) {
        return UserDto.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .nickname(user.getNickname())
                .identityNumber(user.getIdentityNumber())
                .profileImgPath(user.getProfileImgPath())
                .skill(user.getSkill())
                .myInfo(user.getMyInfo())
                .build();

    }
}
