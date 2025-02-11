package com.yuina.survey.controller;

import com.yuina.survey.constants.ResMessage;
import com.yuina.survey.entity.Login;
import com.yuina.survey.entity.Quiz;
import com.yuina.survey.service.ifs.LoginService;
import com.yuina.survey.service.ifs.QuizService;
import com.yuina.survey.vo.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("admin/")
@CrossOrigin
public class QuizController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private LoginService loginService;

    /**
     * @param req
     * @param session 每個不同的 Client 端(電腦或網頁)與同一台 Server 溝通後得到的 session_id 都不同
     * @return
     */
    @PostMapping(value = "login")
    public BasicRes login(@Valid @RequestBody LoginReq req, HttpSession session) {
        // 確認是否已有先登入了
        // 如果已登入成功，就有在 session 暫存帳號，也表示已確認帳密
        Login attr = (Login) session.getAttribute("user");
        if (attr != null && attr.getEmail().equals(req.getEmail())) {
            return new LoginRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(), attr);
        }
        LoginRes res = loginService.login(req);
        Login user = null;
        if (res.getCode() == 200) {
            session.setMaxInactiveInterval(7200); // 2小時
            session.setAttribute("user", res.getLogin());
            user = (Login) session.getAttribute("user");
        }
        return new LoginRes(res.getCode(), res.getMessage(), user);
    }

    @PostMapping(value = "logout")
    public BasicRes logout(HttpSession session) {
        // 讓 session 失效。再次登入後，會有新的 session_id
        session.invalidate();
        return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
    }

    @PostMapping(value = "add-info")
    public BasicRes add(@Valid @RequestBody LoginReq req) {
        return loginService.add(req);
    }

    // 新增
    @PostMapping(value = "create")
    public BasicRes create(@RequestBody CreateUpdateReq req) {
        return quizService.create(req);
    }

    // 更新
    @PostMapping(value = "update")
    public BasicRes update(@RequestBody CreateUpdateReq req) {
        return quizService.update(req);
    }

    // 刪除(用quiz_id)
    @PostMapping(value = "delete")
    public BasicRes delete(@RequestBody DeleteReq req, HttpSession session) {
        String attr = (String) session.getAttribute("account");
        if (attr == null) { // 登入失敗的話
            return new BasicRes(ResMessage.PLEASE_LOGIN_FIRST.getCode(),
                    ResMessage.PLEASE_LOGIN_FIRST.getMessage());
        }
        return quizService.delete(req);
    }

    // 查詢多份問卷(不含問題，用在首頁搜尋)
    @PostMapping(value = "search")
    public BasicRes search(@RequestBody SearchReq req) {
        // 因為 service 中有使用 cache，所以必須要先確認 req 中所有參數的值都不是 null

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
