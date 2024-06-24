package com.sick.apeuda.entity;
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
    private String projectPassword;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "project_skill",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private List<Skill> skills;
}
