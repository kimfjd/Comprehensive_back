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
    public void updateMember(Member member, PasswordEncoder passwordEncoder) {
        if (this.password != null && !this.password.isEmpty()) {
            member.setPassword(passwordEncoder.encode(this.password));
        }
        if (this.nickname != null) {
            member.setNickname(this.nickname);
        }
        if (this.profileImgPath != null) {
            member.setProfileImgPath(this.profileImgPath);
        }
        if (this.skill != null) {
            member.setSkill(this.skill);
        }
        if (this.myInfo != null) {
            member.setMyInfo(this.myInfo);
        }
    }



    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}