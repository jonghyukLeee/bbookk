package com.bbookk.repository.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class NoneBooksDto {
    private String imgSource;
    private String bookName;
    private String author;
    private String publisher;
    private String name;

    @QueryProjection
    public NoneBooksDto(String imgSource, String bookName, String author, String publisher, String name) {
        this.imgSource = imgSource;
        this.bookName = bookName;
        this.author = author;
        this.publisher = publisher;
        this.name = name;
    }
}
