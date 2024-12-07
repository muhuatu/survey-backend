package com.yuina.survey.vo;

// 給 SQL 裝的容器
public class StatisticsDTO {

    private String quizName;

    private int questionId;

    private String title; // 問題題目

    private String type; // 問題類型

    private String optionStr; // 來自DB Question的每一題選項

    private String answerStr; // 來自DB Response的每一題答案

    public StatisticsDTO() {
    }

    public StatisticsDTO(String quizName, int questionId, String title,
                         String type, String optionStr, String answerStr) {
        this.quizName = quizName;
        this.questionId = questionId;
        this.title = title;
        this.type = type;
        this.optionStr = optionStr;
        this.answerStr = answerStr;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
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

    public String getOptionStr() {
        return optionStr;
    }

    public void setOptionStr(String optionStr) {
        this.optionStr = optionStr;
    }

    public String getAnswerStr() {
        return answerStr;
    }

    public void setAnswerStr(String answerStr) {
        this.answerStr = answerStr;
    }
}
