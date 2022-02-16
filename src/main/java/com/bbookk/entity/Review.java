package com.bbookk.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Review {

    @Id @GeneratedValue
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String name;
    private String comment;
    private int point;
}
