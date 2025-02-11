package com.yuina.survey.service.ifs;

import com.yuina.survey.vo.*;

public interface QuizService {

    /**
     * 新增&更新問卷
     * @param req
     */
    public BasicRes create(CreateUpdateReq req);

    /**
     * 更新問卷
     * @param req
     * @return
     */
    public BasicRes update(CreateUpdateReq req);

    /**
     * 刪除問卷
     * @param req
     * @return
     */
    public BasicRes delete(DeleteReq req);

    /**
     * 查詢問卷
     * @param req
     */
    public SearchRes search(SearchReq req);

    /**
     * 獲取問卷所有內容
     * @param req
     * @return
     */
    public GetQuizRes searchQuiz(GetQuizReq req);

    /**
     * 獲取回答
     * @param req
     * @return
     */
    public BasicRes fillIn(FillInReq req);

    /**
     * 藉由問卷ID找出所有人的填答
     * @param quizId
     * @return
     */
    public ResponseRes getAllResponse(int quizId);

    /**
     * 藉由問卷ID和回覆ID找出該用戶的填答
     * @param quizId
     * @param responseId
     * @return
     */
    public ResponseRes getResponse(int quizId, int responseId);

    /**
     * 藉由問卷ID找出所有用戶的統計資料
     * @param quizId
     * @return
     */
    public StatisticsRes getStatistics(int quizId);

    /**
     * 確認填寫人的信箱是否有重複
     * @param email
     * @return
     */
    public boolean checkEmail(String email, int quizId);

    // 測試
    public StatisticsRes test(int quizId);

}
