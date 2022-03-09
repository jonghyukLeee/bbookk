package com.bbookk.repository.dto;

import lombok.Data;

@Data
public class BorrowDetailsDto {
    private String imgSource;
    private String bookName;
    private String lenderName;
    private String status;

    public BorrowDetailsDto(String imgSource, String bookName, String lenderName) {
        this.imgSource = imgSource;
        this.bookName = bookName;
        this.lenderName = lenderName;
    }
}
