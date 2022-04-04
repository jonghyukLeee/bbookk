package com.bbookk.controller.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddressForm {
    private String si;
    @NotNull
    private String gu;
    private String dong;
}
