package com.yuina.survey.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yuina.survey.entity.Question;

import java.time.LocalDate;
import java.util.List;

public class GetQuizRes extends BasicRes {

    private int id;

    private String name;

    private String description;

    @JsonProperty("start_date")
    private LocalDate startDate;

    @JsonProperty("end_date")
    private LocalDate endDate;

    private boolean published;

    @JsonProperty("question_list")
    private List<Question> questionList;

    public GetQuizRes(int code, String message) {
        super(code, message);
    }

    public GetQuizRes(int code, String message, int id, String name,
                      String description, LocalDate startDate, LocalDate endDate,
                      boolean published, List<Question> questionList) {
        super(code, message);
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.published = published;
        this.questionList = questionList;
    }

    public GetQuizRes(int id, String name, String description,
                      LocalDate startDate, LocalDate endDate,
                      boolean published, List<Question> questionList) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.published = published;
        this.questionList = questionList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }
}
