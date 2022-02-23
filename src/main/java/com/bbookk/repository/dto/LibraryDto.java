package com.bbookk.repository.dto;

import com.bbookk.entity.OrderStatus;
import com.bbookk.entity.Orders;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;


@Data
public class LibraryDto {
    private String imgSource;
    private String bookName;
    private String author;
    private String publisher;
    private String isbn;

    @QueryProjection
    public LibraryDto(String imgSource, String bookName, String author, String publisher, String isbn) {
        this.imgSource = imgSource;
        this.bookName = bookName;
        this.author = author;
        this.publisher = publisher;
        this.isbn = isbn;
    }
}
