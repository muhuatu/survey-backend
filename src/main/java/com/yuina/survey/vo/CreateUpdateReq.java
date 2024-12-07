package com.yuina.survey.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yuina.survey.entity.Question;
import com.yuina.survey.entity.Quiz;

import java.time.LocalDate;
import java.util.List;

public class CreateUpdateReq extends Quiz {

    // 問卷已繼承

    // 問題
    @JsonProperty("question_list")
    private List<Question> questionList;

    public CreateUpdateReq() {
    }

    public CreateUpdateReq(String name, String description,
                           LocalDate startDate, LocalDate endDate, boolean published) {
        super(name, description, startDate, endDate, published);
    }

    public CreateUpdateReq(int id, String name, String description,
                           LocalDate startDate, LocalDate endDate,
                           boolean published, List<Question> questionList) {
        super(id, name, description, startDate, endDate, published);
        this.questionList = questionList;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }
}
