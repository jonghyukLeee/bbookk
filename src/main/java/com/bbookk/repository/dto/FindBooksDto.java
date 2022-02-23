package com.bbookk.repository.dto;

import com.bbookk.entity.OrderStatus;
import com.bbookk.entity.Orders;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class FindBooksDto {
    private Long lenderId;
    private String imgSource;
    private String bookName;
    private String author;
    private String publisher;
    private String name;

    @QueryProjection
    public FindBooksDto(Long id, String imgSource, String bookName, String author, String publisher, String name) {
        this.lenderId = id;
        this.imgSource = imgSource;
        this.bookName = bookName;
        this.author = author;
        this.publisher = publisher;
        this.name = name;
    }
}
