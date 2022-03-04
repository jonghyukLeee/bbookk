package com.bbookk.controller.dto;

import lombok.Data;

@Data
public class MypageDto {
    private String memberName;
    private int cash;

    public MypageDto(String memberName, int cash) {
        this.memberName = memberName;
        this.cash = cash;
    }
}
