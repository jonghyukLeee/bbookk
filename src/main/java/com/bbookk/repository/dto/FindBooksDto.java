package com.bbookk.repository.dto;

import com.bbookk.entity.BookStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class FindBooksDto {
    private String imgSource;
    private String bookName;
    private String author;
    private String publisher;
    private String name;
    private String status;

    @QueryProjection
    public FindBooksDto(String imgSource, String bookName, String author, String publisher, String name, BookStatus status) {
        this.imgSource = imgSource;
        this.bookName = bookName;
        this.author = author;
        this.publisher = publisher;
        this.name = name;
        this.status = status.toString(status);
    }
}
