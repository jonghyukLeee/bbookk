package com.bbookk.repository.dto;

import lombok.Data;

@Data
public class BookDetailsDto {
    private String imgSource;
    private String bookName;
    private String lenderName;
    private String status;

    public BookDetailsDto(String imgSource, String bookName, String lenderName,String status) {
        this.imgSource = imgSource;
        this.bookName = bookName;
        this.lenderName = lenderName;
        this.status = status;
    }
}
