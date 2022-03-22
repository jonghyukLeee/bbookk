package com.bbookk.repository.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class ProfileDto {
    private String name;
    private String gu;
    private int cash;

    @QueryProjection
    public ProfileDto(String name, String gu, int cash) {
        this.name = name;
        this.gu = gu;
        this.cash = cash;
    }
}
