package com.yuina.survey.service.ifs;

// 此註解只能提醒該介面只能定義"一個"方法，若有第二個方法時會報錯
// 不須等到用 lambda 重新定義後，才發現無法使用
@FunctionalInterface
public interface QuizService2 {

    public void test();
}
