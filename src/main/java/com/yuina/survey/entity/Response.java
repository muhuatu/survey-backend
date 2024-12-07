package com.yuina.survey.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "response")
@IdClass(value = Response.class)
public class Response {

    @Id
    @Column(name = "response_id")
    @JsonProperty("response_id")
    private int responseId;

    @Id
    @Column(name = "quiz_id")
    @JsonProperty("quiz_id")
    private int quizId;

    @Id
    @Column(name = "question_id")
    @JsonProperty("question_id")
    private int questionId;

    @Column(name = "username")
    private String username;

    @Column(name = "phone")
    private String phone;

    @Id
    @Column(name = "email")
    private String email;

    @Column(name = "age")
    private int age;

    // 多選答案前端用陣列過來，後端用分號變成字串
    @Column(name = "answers")
    private String answers;

    @Column(name = "fill_in_date")
    private LocalDate fillInDate;

    public Response() {
    }

    public Response(int quizId, int questionId, String username, String phone, String email, int age, String answers, LocalDate fillInDate) {
        this.quizId = quizId;
        this.questionId = questionId;
        this.username = username;
        this.phone = phone;
        this.email = email;
        this.age = age;
        this.answers = answers;
        this.fillInDate = fillInDate;
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

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
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

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    public LocalDate getFillInDate() {
        return fillInDate;
    }

    public void setFillInDate(LocalDate fillInDate) {
        this.fillInDate = fillInDate;
    }
}
