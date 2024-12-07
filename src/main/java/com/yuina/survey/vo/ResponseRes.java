package com.yuina.survey.vo;

import java.util.List;

public class ResponseRes extends BasicRes{

    private List<ResponseDTO> responseDTOList;

    public ResponseRes() {
    }

    public ResponseRes(int code, String message) {
        super(code, message);
    }

    public ResponseRes(int code, String message, List<ResponseDTO> responseDTOList) {
        super(code, message);
        this.responseDTOList = responseDTOList;
    }

    public ResponseRes(List<ResponseDTO> responseDTOList) {
        this.responseDTOList = responseDTOList;
    }

    public List<ResponseDTO> getResponseDTOList() {
        return responseDTOList;
    }

    public void setResponseDTOList(List<ResponseDTO> responseDTOList) {
        this.responseDTOList = responseDTOList;
    }
}
