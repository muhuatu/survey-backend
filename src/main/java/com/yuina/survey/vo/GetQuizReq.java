package com.yuina.survey.vo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Valid
public class GetQuizReq {

    @NotNull
    @Positive(message = "必須為正數")
    private int id;

    public int getId() {
        return id;
    }
}
