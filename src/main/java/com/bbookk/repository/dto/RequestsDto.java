package com.bbookk.repository.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class RequestsDto {
    //사진 , 책이름, 대여신청자
    private Long orderId;
    private String imgSource;
    private String bookName;
    private String borrowerName;

    @QueryProjection
    public RequestsDto(Long orderId, String imgSource, String bookName, String borrowerName) {
        this.orderId = orderId;
        this.imgSource = imgSource;
        this.bookName = bookName;
        this.borrowerName = borrowerName;
    }
}
