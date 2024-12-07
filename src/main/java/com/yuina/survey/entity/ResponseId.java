package com.yuina.survey.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ResponseId implements Serializable {

    private int responseId;

    private int questionId;

    private int quizId;

    private String email;

    public ResponseId() {
    }

    public ResponseId(int questionId, int quizId, String email) {
        this.questionId = questionId;
        this.quizId = quizId;
        this.email = email;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getResponseId() {
        return responseId;
    }

    public void setResponseId(int responseId) {
        this.responseId = responseId;
    }
}
