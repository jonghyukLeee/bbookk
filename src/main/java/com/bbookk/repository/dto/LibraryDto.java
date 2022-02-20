package com.bbookk.repository.dto;

import com.bbookk.entity.BookStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;


@Data
public class LibraryDto {
    private String imgSource;
    private String bookName;
    private String author;
    private String publisher;
    private String isbn;
    private String status;

    @QueryProjection
    public LibraryDto(String imgSource, String bookName, String author, String publisher, String isbn, BookStatus status) {
        this.imgSource = imgSource;
        this.bookName = bookName;
        this.author = author;
        this.publisher = publisher;
        this.isbn = isbn;
        this.status = status.toString(status);
    }
}
