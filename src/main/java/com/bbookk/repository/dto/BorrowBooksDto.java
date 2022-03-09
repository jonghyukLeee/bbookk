package com.bbookk.repository.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class BorrowBooksDto {
    private Long lenderId;
    private String imgSource;
    private String bookName;
    private String author;
    private String publisher;
    private String nickname;

    @QueryProjection
    public BorrowBooksDto(Long lenderId,String imgSource, String bookName, String author, String publisher, String nickname) {
        this.lenderId = lenderId;
        this.imgSource = imgSource;
        this.bookName = bookName;
        this.author = author;
        this.publisher = publisher;
        this.nickname = nickname;
    }
}
