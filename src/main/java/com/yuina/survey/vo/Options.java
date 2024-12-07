package com.yuina.survey.vo;

public class Options {

    private int optionNumber;

    private String option;

    public Options() {
    }

    public Options(int optionNumber, String option) {
        this.optionNumber = optionNumber;
        this.option = option;
    }

    public int getOptionNumber() {
        return optionNumber;
    }

    public void setOptionNumber(int optionNumber) {
        this.optionNumber = optionNumber;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}
