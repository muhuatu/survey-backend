package com.yuina.survey.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Valid
public class FillInReq {

    @JsonProperty("quiz_id")
    @Positive(message = "必須為正數")
    private int quizId;

    @NotNull(message = "名稱不可為空")
    private String username;

    //@Size(min = 10, max = 10, message = "手機長度為 10 個數字")
    //@Pattern(regexp = "^\\d{10}$", message = "手機必須為 10 個數字")
    private String phone;

    @NotNull(message = "信箱不可為空")
    @Email(message = "必須是有效的電子信箱")
    private String email;

    private int age;

    // 多選答案前端用 map 變成字串過來
    // 題號 與 選項
    private Map<Integer, List<String>> answers;

    @JsonProperty("fill_in_date")
    private LocalDate fillInDate;

    public FillInReq() {
    }

    public FillInReq(int quizId, String username, String phone, String email, int age, Map<Integer, List<String>> answers, LocalDate fillInDate) {
        this.quizId = quizId;
        this.username = username;
        this.phone = phone;
        this.email = email;
        this.age = age;
        this.answers = answers;
        this.fillInDate = fillInDate;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
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

    public Map<Integer, List<String>> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<Integer, List<String>> answers) {
        this.answers = answers;
    }

    public LocalDate getFillInDate() {
        return fillInDate;
    }

    public void setFillInDate(LocalDate fillInDate) {
        this.fillInDate = fillInDate;
    }
}
