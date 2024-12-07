package com.yuina.survey.vo;

import java.time.LocalDate;

public class ResponseDTO{

    private int responseId; // PK

    private int quizId;

    private LocalDate fillInDate;

    private String quizName;

    private String description;

    private String username; // 使用者名稱

    private String phone;

    private String email;

    private int age;

    private int questionId;

    private String title; // 問題名稱

    private String answerStr;

    public ResponseDTO() {
    }

    public ResponseDTO(int responseId, int quizId, LocalDate fillInDate, String quizName, String quizDescription,
                       String username, String phone, String email, int age,
                       int questionId, String title, String answerStr) {
        this.responseId = responseId;
        this.quizId = quizId;
        this.fillInDate = fillInDate;
        this.quizName = quizName;
        this.description = quizDescription;
        this.username = username;
        this.phone = phone;
        this.email = email;
        this.age = age;
        this.questionId = questionId;
        this.title = title;
        this.answerStr = answerStr;
    }

    public int getResponseId() {
        return responseId;
    }

    public void setResponseId(int responseId) {
        this.responseId = responseId;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public LocalDate getFillInDate() {
        return fillInDate;
    }

    public void setFillInDate(LocalDate fillInDate) {
        this.fillInDate = fillInDate;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnswerStr() {
        return answerStr;
    }

    public void setAnswerStr(String answerStr) {
        this.answerStr = answerStr;
    }
}
