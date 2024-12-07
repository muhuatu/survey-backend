package com.yuina.survey.vo;

import java.util.Map;

public class StatisticsVo {

    private String quizName;

    private int questionId;

    private String title;

    private Map<String, Integer> optionCountMap;

    public StatisticsVo() {
    }

    public StatisticsVo(String quizName, int questionId, String title,
                        Map<String, Integer> optionCountMap) {
        this.quizName = quizName;
        this.questionId = questionId;
        this.title = title;
        this.optionCountMap = optionCountMap;
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

    public Map<String, Integer> getOptionCountMap() {
        return optionCountMap;
    }

    public void setOptionCountMap(Map<String, Integer> optionCountMap) {
        this.optionCountMap = optionCountMap;
    }
}
