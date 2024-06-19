package com.sick.apeuda.entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
@Getter
@Setter
@ToString
@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private String email;

    private String name;
    private String nickname;
    private String password;

    @Column(length = 7)
    private String identityNumber;

    @Lob
    private String profile;

    @ManyToOne
    @JoinColumn(name = "skill_id")
    private Skill skill;

    @Lob
    private String myInfo;

    // Getters and Setters
}
