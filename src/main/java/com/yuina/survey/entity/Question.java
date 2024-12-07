package com.yuina.survey.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "question")
@IdClass(value = QuestionId.class) // 複合PK(@Id)的話，要新增類別管理
public class Question {

    @Id
    @Column(name = "question_id")
    @JsonProperty("question_id")
    private int questionId;

    @Id
    @Column(name = "quiz_id")
    @JsonProperty("quiz_id")
    private int quizId;

    @Column(name = "title")
    private String title;

    @Column(name = "type")
    private String type;

    @Column(name = "necessary")
    private boolean necessary;

    @Column(name = "choice")
    @JsonProperty("option_list")
    private String optionList;

    public Question() {
    }

    public Question(int quizId, String title, String type, boolean necessary, String optionList) {
        this.quizId = quizId;
        this.title = title;
        this.type = type;
        this.necessary = necessary;
        this.optionList = optionList;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isNecessary() {
        return necessary;
    }

    public void setNecessary(boolean necessary) {
        this.necessary = necessary;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public String getOptionList() {
        return optionList;
    }

    public void setOptionList(String optionList) {
        this.optionList = optionList;
    }

}
