package com.sick.apeuda.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table
public class ReadMessage {

    @Id
    @Column(name = "msg_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member1_id")
    private Member member1;

    @ManyToOne
    @JoinColumn(name = "member2_id")
    private Member member2;

    private boolean readCheck;
}
