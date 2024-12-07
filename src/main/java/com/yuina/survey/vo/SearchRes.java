package com.yuina.survey.vo;

import com.yuina.survey.entity.Quiz;

import java.util.List;

public class SearchRes extends BasicRes{

    private List<Quiz> quizList;

    public SearchRes() {
    }

    public SearchRes(List<Quiz> quizList) {
        this.quizList = quizList;
    }

    public SearchRes(int code, String message, List<Quiz> quizList) {
        super(code, message);
        this.quizList = quizList;
    }

    public List<Quiz> getQuizList() {
        return quizList;
    }
}
