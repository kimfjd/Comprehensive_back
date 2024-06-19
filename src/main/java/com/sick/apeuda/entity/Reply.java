package com.sick.apeuda.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "Reply")
public class Reply {
    @Id
    @Column(name = "reply_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long replyId;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @Lob
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime regDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Getters and Setters
}
