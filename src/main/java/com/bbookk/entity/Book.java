package com.bbookk.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book {

    @Id @GeneratedValue()
    @Column(name = "book_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String imgSource;
    private String bookName;
    private String author;
    private String publisher;
    private String isbn;

    @Enumerated(EnumType.STRING)
    private BookStatus status;

    public void setMember(Member member)
    {
        this.member = member;
        member.getBooks().add(this);
    }
    public Book(String imgSource, String bookName, String author, String publisher, String isbn) {
        this.imgSource = imgSource;
        this.bookName = bookName;
        this.author = author;
        this.publisher = publisher;
        this.isbn = isbn;
        this.status = BookStatus.NONE;
    }

}
