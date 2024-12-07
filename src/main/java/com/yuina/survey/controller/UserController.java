package com.yuina.survey.controller;

import com.yuina.survey.service.ifs.QuizService;
import com.yuina.survey.vo.BasicRes;
import com.yuina.survey.vo.FillInReq;
import com.yuina.survey.vo.SearchReq;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private QuizService quizService;

    // 查詢
    @PostMapping(value = "search")
    public BasicRes search(@RequestBody SearchReq req){
        return quizService.search(req);
    }

    // 填寫問卷
    @PostMapping(value = "fillIn")
    public BasicRes fillIn(@Valid @RequestBody FillInReq req){
        return quizService.fillIn(req);
    }

    @GetMapping(value = "check_email")
    public boolean checkEmail(@RequestParam String email){
        return quizService.checkEmail(email);
    }


}
