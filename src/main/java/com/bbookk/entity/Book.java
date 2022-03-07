package com.bbookk.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

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

    @OneToOne
    @JoinColumn(name = "order_id")
    private Orders order;

    private String imgSource;
    private String bookName;
    private String author;
    private String publisher;
    private String isbn;

    public void setMember(Member member)
    {
        this.member = member;
        member.getBooks().add(this);
    }

    public void setOrder(Orders order)
    {
        this.order = order;
    }

    public boolean getStatus()
    {
        return this.order == null;
    }

    public Book(String imgSource, String bookName, String author, String publisher, String isbn) {
        this.imgSource = imgSource;
        this.bookName = bookName;
        this.author = author;
        this.publisher = publisher;
        this.isbn = isbn;
    }


}
