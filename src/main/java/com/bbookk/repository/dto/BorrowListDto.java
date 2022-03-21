package com.bbookk.repository.dto;

import com.bbookk.entity.OrderStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class BorrowListDto {
    private String imgSource;
    private String bookName;
    private String date;
    private String status;

    @QueryProjection
    public BorrowListDto(String imgSource, String bookName, LocalDateTime date, OrderStatus status) {
        this.imgSource = imgSource;
        this.bookName = bookName;
        this.date = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.status = status == OrderStatus.REQUESTED ? "요청중" : "대여중";
    }
}
