package com.yuina.survey.constants;

public enum ResMessage {

    // 2. 再寫列舉
    SUCCESS(200,"成功"),//
    FAIL_EXCEPTION(400,"處理異常"),//

    // 問卷
    PARAM_ID_ERROR(400,"問卷ID參數錯誤"),//
    QUIZ_ID_MISMATCH(400,"問卷ID不相符"),//
    QUIZ_NOT_FOUND(404,"找不到此問卷"),//
    PARAM_NAME_ERROR(400,"問卷名稱參數錯誤"),//
    PARAM_DESCRIPTION_ERROR(400,"問卷說明參數錯誤"),//
    PARAM_START_DATE_ERROR(400,"開始日期參數錯誤"),//
    PARAM_END_DATE_ERROR(400,"結束日期參數錯誤"),//
    QUIZ_UPDATE_FAILED(400,"此問卷無法編輯"),//
    EMAIL_DUPLICATED(400,"此信箱已填寫過問卷"),//

    // 問題
    QUESTION_NOT_FOUND(404,"找不到此問卷中的問題"),//
    PARAM_QUESTION_NOT_FOUND(404,"找不到此問題選項參數"),//
    PARAM_QUESTION_ID_ERROR(400,"問題ID參數錯誤"),//
    PARAM_TITLE_ERROR(400,"問題標題參數錯誤"),//
    PARAM_TYPE_ERROR(400,"問題類型參數錯誤"),//
    PARAM_OPTIONS_ERROR(400,"問題選項參數錯誤"),//
    QUESTION_ID_DUPLICATED(400,"此問已填寫過問卷"),//

    // 回答
    ANSWER_NOT_FOUND(404,"找不到此回覆"),//
    STATUS_DATE_RANGE_ERROR(400,"填寫日期範圍或發布狀態錯誤"),//
    ANSWER_IS_NECESSARY(404,"請確認必填項"),//
    ANSWER_ERROR(404,"單選或簡答題不可有多個答案"),//
    OPTIONS_TRANSFER_ERROR(404,"選項轉換錯誤"),//
    OPTIONS_MISMATCH(404,"回答選項與資料庫選項不符"),//

    // 管理員帳戶
    ACCOUNT_ERROR(400,"帳戶資訊錯誤"),//
    ACCOUNT_NOT_EXIST(404,"此帳戶不存在"),//
    ACCOUNT_IS_EXIST(400,"此帳戶已存在"),//
    INVALID_PASSWORD(400,"密碼錯誤"),//
    ADD_INFO_FAILED(400,"增加資訊失敗"),
    ACCOUNT_OR_PASSWORD_ERROR(404,"帳號或密碼錯誤"),//
    ;

    // 1. 先建立屬性
    private int code;

    private String message;

    private ResMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
