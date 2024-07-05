package com.sick.apeuda.dto;


import com.sick.apeuda.constant.Authority;
import com.sick.apeuda.entity.Member;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberReqDto {
    private String email;
    private String password;
    private String name;
    private String nickname;
    private String identityNumber;
    private String profileImgPath;
    private String skill;
    private String myInfo;


    public Member toEntity(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(email)
                .name(name)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .identityNumber(identityNumber)
                .profileImgPath(profileImgPath)
                .skill(skill)
                .myInfo(myInfo)
                .authority(Authority.ROLE_USER)
                .build();
    }
    public Member memUpdate(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .profileImgPath(profileImgPath)
                .skill(skill)
                .myInfo(myInfo)
                .authority(Authority.ROLE_USER)
                .build();
    }



    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}