package com.yuina.survey.vo;

import java.util.List;

public class StatisticsRes extends BasicRes{

    private List<StatisticsVo> statisticsVoList;

    public StatisticsRes() {
    }

    public StatisticsRes(int code, String message) {
        super(code, message);
    }

    public StatisticsRes(List<StatisticsVo> statisticsVoList) {
        this.statisticsVoList = statisticsVoList;
    }

    public StatisticsRes(int code, String message, List<StatisticsVo> statisticsVoList) {
        super(code, message);
        this.statisticsVoList = statisticsVoList;
    }

    public List<StatisticsVo> getStatisticsVoList() {
        return statisticsVoList;
    }
}
