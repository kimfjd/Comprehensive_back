package com.sick.apeuda.entity;
import lombok.*;

import javax.persistence.*;
@Getter
@Setter
@ToString
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "User")
public class User {
    @Id
    @Column(name = "user_id")
    private String email;

    @Column(nullable = false)
    private String name;
    private String password;

    @Column
    private String nickname;

    @Column(length = 7, nullable = false)
    private String identityNumber;

    @Lob
    private String profileImgPath;


    @Column
    private String skill;

    @Lob
    private String myInfo;
}
