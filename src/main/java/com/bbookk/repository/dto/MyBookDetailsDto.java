package com.bbookk.repository.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class MyBookDetailsDto {
    private String imgSource;
    private String bookName;
    private String status;

    public MyBookDetailsDto(String imgSource, String bookName) {
        this.imgSource = imgSource;
        this.bookName = bookName;
    }
}
