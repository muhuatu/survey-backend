package com.yuina.survey.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
// 告訴編譯器：雖然 QuestionId 類實現了 Serializable 接口，但是我們不想在這裡處理 serialVersionUID 這個警告。
public class QuestionId implements Serializable {

    private int questionId;

    private int quizId;

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
}
