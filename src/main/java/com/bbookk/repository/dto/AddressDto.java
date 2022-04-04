package com.bbookk.repository.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class AddressDto {
    private String si;
    private String gu;
    private String dong;

    @QueryProjection
    public AddressDto(String si, String gu, String dong) {
        this.si = si;
        this.gu = gu;
        this.dong = dong;
    }
}
