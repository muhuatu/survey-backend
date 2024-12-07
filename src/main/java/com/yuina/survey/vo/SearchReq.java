package com.yuina.survey.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class SearchReq {

    private String name;

    @JsonProperty("start_date")
    private LocalDate startDate;

    @JsonProperty("end_date")
    private LocalDate endDate;


    // 只要 GET 方法

    public String getName() {
        return name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

}
