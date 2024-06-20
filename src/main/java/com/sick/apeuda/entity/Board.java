package com.sick.apeuda.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "board")
public class Board {
    @Id
    // AUTO로 했으나 auto_increment로 테이블 생성이 되지않아서 IDENTITY로 변경
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long boardId;

    private String title;

    @Lob
    @Column(length = 10000) // 예시로 10000자를 지정
    private String content;
    private String imgPath;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

//    @OneToMany
//    @JoinColumn(name = "board_id")
//    private List<Reply> reply;

    private LocalDateTime regDate;


    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Skill> skill;



}
