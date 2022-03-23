package com.bbookk.repository.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class BorrowListDetailsDto {
    private String imgSource;
    private String bookName;
    private String date;
    private String lenderName;

    @QueryProjection
    public BorrowListDetailsDto(String imgSource, String bookName, LocalDateTime date) {
        this.imgSource = imgSource;
        this.bookName = bookName;
        this.date = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
