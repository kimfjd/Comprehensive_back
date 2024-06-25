package com.sick.apeuda.entity;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "project")
public class Project {
    @Id
    @Column(name = "project_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long projectId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String job;

    private LocalDateTime projectTime;

    private String projectName;
    private String projectTitle;
    private String projectPassword;
    private LocalDateTime regDate;
    @Lob
    @Column(length = 10000) // 예시로 10000자를 지정
    private String projectContent;

    private String nickName;
    private String imgPath;
    private String profileImage;
    @ManyToMany
    @JoinTable(
            name = "project_skills",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private List<Skill> skills;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "project_id")
    private List<Reply> reply;

//    @OneToMany
//    private List<SkillDto> skills;

}
