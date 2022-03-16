package com.bbookk.repository.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class RequestedBooksDto {
    //사진 , 책이름, 대여신청자
    private String imgSource;
    private String bookName;
    private String borrowerName;

    @QueryProjection
    public RequestedBooksDto(String imgSource, String bookName) {
        this.imgSource = imgSource;
        this.bookName = bookName;
    }
}
