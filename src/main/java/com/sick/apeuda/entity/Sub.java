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
@Table(name = "Sub")
public class Sub {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Long subId;

    @MapsId("userId")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @Temporal(TemporalType.DATE)
    private LocalDateTime startDate;

    @Temporal(TemporalType.DATE)
    private LocalDateTime endDate;

}



