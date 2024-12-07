package com.yuina.survey.constants;

public enum QuestionType {

    SINGLE("S"),//
    MULTI("M"),//
    TEXT("T"),//
    ;//

    private String type;

    QuestionType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    // 要加 static 才能全域使用
    public static boolean checkType(String type) {
        // 遍歷列舉中三種類型，再判斷是否正確
        // QuestionType.values() 此方法可獲取 enum 所有的 type
        for (QuestionType item : QuestionType.values()) {
            if (item.getType().equalsIgnoreCase(type)) {
                return true;
            }
        }
        return false;
    }
}
