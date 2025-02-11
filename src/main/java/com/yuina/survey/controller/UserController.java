package com.yuina.survey.controller;

import com.yuina.survey.service.ifs.QuizService;
import com.yuina.survey.vo.BasicRes;
import com.yuina.survey.vo.FillInReq;
import com.yuina.survey.vo.SearchReq;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private QuizService quizService;

    // 查詢
    @PostMapping(value = "search")
    public BasicRes search(@RequestBody SearchReq req){
        // 1. 獲取來自前台的 req 資料(問卷名稱、開始與結束時間)
        String name = req.getName();
        LocalDate start = req.getStartDate();
        LocalDate end = req.getEndDate();

        // 2. 檢查參數
        // 2-1. 假設 name 為空白，當作要取得全部
        if (!StringUtils.hasText(name)) {
            name = "";
            req.setName(name);
        }
        // 2-2. 假設日期為空
        if (start == null) {
            start = LocalDate.of(1970, 1, 1);
            req.setStartDate(start);
        }
        if (end == null) {
            end = LocalDate.of(9999, 1, 1);
            req.setEndDate(end);
        }
        // 3. 查詢
        //List<Quiz> quizList = quizDao.search(name, start, end);
        return quizService.search(req);
    }

    // 填寫問卷
    @PostMapping(value = "fillIn")
    public BasicRes fillIn(@Valid @RequestBody FillInReq req){
        return quizService.fillIn(req);
    }

    @GetMapping(value = "check_email")
    public boolean checkEmail(@RequestParam String email, @RequestParam int quizId){
        return quizService.checkEmail(email, quizId);
    }


}
