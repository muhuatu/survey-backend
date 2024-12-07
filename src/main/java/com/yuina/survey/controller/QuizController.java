package com.yuina.survey.controller;

import com.yuina.survey.service.ifs.QuizService;
import com.yuina.survey.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin/")
@CrossOrigin
public class QuizController {

    @Autowired
    private QuizService quizService;

    // 新增
    @PostMapping(value = "create&update")
    public BasicRes create(@RequestBody CreateUpdateReq req){
        return quizService.createUpdate(req);
    }

    // 刪除(用quiz_id)
    @PostMapping(value = "delete")
    public BasicRes delete(@RequestBody DeleteReq req){
        return quizService.delete(req);
    }

    // 查詢多份問卷(不含問題，用在首頁搜尋)
    @PostMapping(value = "search")
    public BasicRes search(@RequestBody SearchReq req){
        return quizService.search(req);
    }

    // 查詢整份問卷(用在首頁帶資料到問題設定)
    @PostMapping(value = "search_quiz")
    public GetQuizRes searchQuiz(@RequestBody GetQuizReq req){
        return quizService.searchQuiz(req);
    }

    // 藉由問卷ID找出所有人的填答
    @GetMapping(value = "get_all_response_by_id")
    public ResponseRes getAllResponse(@RequestParam int quizId){
        return quizService.getAllResponse(quizId);
    }

    // 藉由問卷ID和信箱找出該用戶的填答
    @GetMapping(value = "get_response_by_id")
    public ResponseRes getResponse(@RequestParam int quizId, @RequestParam int responseId){
        return quizService.getResponse(quizId, responseId);
    }

    // 藉由問卷ID找出所有用戶的統計資料
    @GetMapping(value = "get_statistics")
    public StatisticsRes getStatistics(@RequestParam int quizId){
        return quizService.getStatistics(quizId);
    }

    @GetMapping(value = "test")
    public StatisticsRes test(@RequestParam int quizId){
        return quizService.test(quizId);
    }

}
