package com.yuina.survey.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DeleteReq {

    @JsonProperty("quizId_list")
    private List<Integer> quizIdList;

    public DeleteReq() {
    }

    public DeleteReq(List<Integer> quizIdList) {
        this.quizIdList = quizIdList;
    }

    public List<Integer> getQuizIdList() {
        return quizIdList;
    }

    public void setQuizIdList(List<Integer> quizIdList) {
        this.quizIdList = quizIdList;
    }


}
