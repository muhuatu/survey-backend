package com.yuina.survey.controller;

import com.yuina.survey.constants.ResMessage;
import com.yuina.survey.service.ifs.LoginService;
import com.yuina.survey.service.ifs.QuizService;
import com.yuina.survey.vo.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin/")
@CrossOrigin
public class QuizController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private LoginService loginService;

    /**
     *
     * @param req
     * @param session 每個不同的 Client 端(電腦或網頁)與同一台 Server 溝通後得到的 session_id 都不同
     * @return
     */
    @PostMapping(value = "login")
    public BasicRes login(@Valid @RequestBody LoginReq req, HttpSession session) {

        // 確認是否已有先登入了
        // 如果已登入成功，就有在 session 暫存帳號，也表示已確認帳密
        String attr = (String) session.getAttribute("account");
        if (attr != null && attr.equals(req.getEmail())) { // 登入成功的話
            return new BasicRes(ResMessage.SUCCESS.getCode(),
                    ResMessage.SUCCESS.getMessage());
        }
        // 設定 session 有效時間（預設30分鐘），單位：秒
        // 0 或 負數：表示永遠不會過期
        session.setMaxInactiveInterval(600); // 600秒=10分鐘
        BasicRes res = loginService.login(req);
        // 若登入成功，使用 session 將 account 暫存
        if (res.getCode() == 200) {
            session.setAttribute("account", req.getEmail());
        }
        return res;
    }

    @PostMapping(value = "logout")
    public BasicRes logout(HttpSession session){
        // 讓 session 失效。再次登入後，會有新的 session_id
        session.invalidate();
        return new BasicRes(ResMessage.SUCCESS.getCode(),
                ResMessage.SUCCESS.getMessage());
    }

    @PostMapping(value = "add-info")
    public BasicRes add(@Valid @RequestBody LoginReq req) {
        return loginService.add(req);
    }

    // 新增
    @PostMapping(value = "create&update")
    public BasicRes create(@RequestBody CreateUpdateReq req) {
        return quizService.createUpdate(req);
    }

    // 刪除(用quiz_id)
    @PostMapping(value = "delete")
    public BasicRes delete(@RequestBody DeleteReq req) {
//        String attr = (String) session.getAttribute("account");
//        if (attr == null) { // 登入成功的話
//            return new BasicRes(ResMessage.PLEASE_LOGIN_FIRST.getCode(),
//                    ResMessage.PLEASE_LOGIN_FIRST.getMessage());
//        }
        return quizService.delete(req);
    }

    // 查詢多份問卷(不含問題，用在首頁搜尋)
    @PostMapping(value = "search")
    public BasicRes search(@RequestBody SearchReq req) {
        return quizService.search(req);
    }

    // 查詢整份問卷(用在首頁帶資料到問題設定)
    @PostMapping(value = "search_quiz")
    public GetQuizRes searchQuiz(@RequestBody GetQuizReq req) {
        return quizService.searchQuiz(req);
    }

    // 藉由問卷ID找出所有人的填答
    @GetMapping(value = "get_all_response_by_id")
    public ResponseRes getAllResponse(@RequestParam int quizId) {
        return quizService.getAllResponse(quizId);
    }

    // 藉由問卷ID和信箱找出該用戶的填答
    @GetMapping(value = "get_response_by_id")
    public ResponseRes getResponse(@RequestParam int quizId, @RequestParam int responseId) {
        return quizService.getResponse(quizId, responseId);
    }

    // 藉由問卷ID找出所有用戶的統計資料
    @GetMapping(value = "get_statistics")
    public StatisticsRes getStatistics(@RequestParam int quizId) {
        return quizService.getStatistics(quizId);
    }

    @GetMapping(value = "test")
    public StatisticsRes test(@RequestParam int quizId) {
        return quizService.test(quizId);
    }

}
