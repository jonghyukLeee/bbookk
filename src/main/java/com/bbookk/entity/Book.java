package com.bbookk.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Book {

    @Id @GeneratedValue
    @Column(name = "book_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String bookName;
    private String genre;
    private String isbn;

    @Enumerated(EnumType.STRING)
    private BookStatus status;

}
