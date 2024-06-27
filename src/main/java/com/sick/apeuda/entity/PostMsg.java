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
@Table(name = "PostMsg")
public class PostMsg {
    @Id
    @Column(name = "post_msg_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long postMsgId;

    @Lob
    private String content;

    private LocalDateTime receiveTime;

    @ManyToOne
    @JoinColumn(name = "send_id")
    private Member sendMember;

    @ManyToOne
    @JoinColumn(name = "receive_id")
    private Member receiveMember;

    private Boolean readStatus;

}
