package com.yuina.survey.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuina.survey.constants.QuestionType;
import com.yuina.survey.constants.ResMessage;
import com.yuina.survey.entity.Question;
import com.yuina.survey.entity.Quiz;
import com.yuina.survey.entity.Response;
import com.yuina.survey.repository.QuestionDao;
import com.yuina.survey.repository.QuizDao;
import com.yuina.survey.repository.ResponseDao;
import com.yuina.survey.service.ifs.QuizService;
import com.yuina.survey.vo.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.*;

@Service
public class QuizServiceImpl implements QuizService {

    @Autowired
    private QuizDao quizDao;

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private ResponseDao responseDao;

    ObjectMapper mapper = new ObjectMapper();

    // 一般來說，碰到資料庫的程式碼比較耗時，其他都耗費毫秒而已

    // 新增
    @Transactional // 一個方法裡有兩個以上Dao時，必須加上此註解
    @Override
    public BasicRes createUpdate(CreateUpdateReq req) {
        // 新增問卷時，ID要為 0
        if (req.getId() < 0) {
            return new BasicRes(ResMessage.PARAM_ID_ERROR.getCode(),
                    ResMessage.PARAM_ID_ERROR.getMessage());
        }
        // 檢查參數
        if (req.getId() == 0) {
            BasicRes checkResult = checkParams(req);
            if (checkResult != null) {
                return checkResult;
            }
            try {
                // 1. 新增問卷
                quizDao.createQuiz(req.getName(),
                        req.getDescription(),
                        req.getStartDate(),
                        req.getEndDate(),
                        req.isPublished());

                // req.getId()會一直取到0，要另外寫語法取得最新ID
                int quizId = quizDao.getLastInsertId();

                // 用Dao新增問卷資料後，務必更新問卷ID，不然資料會被覆蓋
                req.setId(quizId);

                // 2. 新增問題集
                for (Question item : req.getQuestionList()) {
                    questionDao.createQuestion(quizId, item.getQuestionId(), item.getTitle(),
                            item.getType(), item.isNecessary(), item.getOptionList());
                }
            } catch (Exception e) {
                return new BasicRes(ResMessage.FAIL_EXCEPTION.getCode(),
                        ResMessage.FAIL_EXCEPTION.getMessage());
            }
            return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());

        } else {
            int quizId = req.getId();
            // 檢查此問卷是否有內容
            Optional<Quiz> op = quizDao.findById(quizId);
            if (op.isEmpty()) {
                return new BasicRes(ResMessage.QUIZ_NOT_FOUND.getCode(),
                        ResMessage.QUIZ_NOT_FOUND.getMessage());
            }
            // 檢查發布
            // 1. 尚未發布：!quiz.isPublished()
            // 2. 已發布尚未開始：quiz.isPublished() && req.getStartDate().isAfter(LocalDate.now())
            Quiz quiz = op.get();
            if (!(!quiz.isPublished() ||
                    (quiz.isPublished() && req.getStartDate().isAfter(LocalDate.now())))) {
                return new BasicRes(ResMessage.QUIZ_UPDATE_FAILED.getCode(),
                        ResMessage.QUIZ_UPDATE_FAILED.getMessage());
            }
            // 更新問卷
            quizDao.updateQuiz(req.getId(),
                    req.getName(),
                    req.getDescription(),
                    req.getStartDate(),
                    req.getEndDate(),
                    req.isPublished());
            // 更新問題：刪除所有問題並更新
            List<Question> questionList = req.getQuestionList();
            questionDao.deleteByQuizId(quizId);
            for (Question item : questionList) {
                item.setQuizId(quizId);
                questionDao.createQuestion(item.getQuizId(), item.getQuestionId(), item.getTitle(),
                        item.getType(), item.isNecessary(), item.getOptionList());
            }
            return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
        }
    }

    // 刪除
    @Transactional
    @Override
    public BasicRes delete(DeleteReq req) {
        List<Integer> quizIdList = req.getQuizIdList();
        // 刪除問卷
        quizDao.deleteByIdIn(quizIdList);
        // 刪此 quiz_id 問卷的所有問題
        questionDao.deleteByQuizIdIn(quizIdList);
        responseDao.deleteByQuizIdIn(quizIdList);
        return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
    }

    // 查詢
    @Override
    public SearchRes search(SearchReq req) {
        // 1. 獲取來自前台的 req 資料(問卷名稱、開始與結束時間)
        String name = req.getName();
        LocalDate start = req.getStartDate();
        LocalDate end = req.getEndDate();

        // 2. 檢查參數
        // 2-1. 假設 name 為空白，當作要取得全部
        if (!StringUtils.hasText(name)) {
            name = "";
        }
        // 2-2. 假設日期為空
        if (start == null) {
            start = LocalDate.of(1970, 1, 1);
        }
        if (end == null) {
            end = LocalDate.of(9999, 1, 1);
        }
        // 3. 查詢
        List<Quiz> quizList = quizDao.search(name, start, end);

        return new SearchRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(),
                quizList);
    }

    // 獲取該問卷所有資料
    @Override
    public GetQuizRes searchQuiz(GetQuizReq req) {

        int quizId = req.getId();

        // 檢查問卷是否有內容
        Optional<Quiz> op = quizDao.findById(quizId);
        if (op.isEmpty()) {
            return new GetQuizRes(ResMessage.QUIZ_NOT_FOUND.getCode(),
                    ResMessage.QUIZ_NOT_FOUND.getMessage());
        }
        // 取出問卷
        Quiz quiz = op.get();
        // 取出問題
        List<Question> questionList = questionDao.getQuestionsById(quizId);
        // 返回結果
        return new GetQuizRes(ResMessage.SUCCESS.getCode(),
                ResMessage.SUCCESS.getMessage(),
                quiz.getId(), quiz.getName(), quiz.getDescription(),
                quiz.getStartDate(), quiz.getEndDate(), quiz.isPublished(), questionList);
    }

    @Override
    public BasicRes fillIn(FillInReq req) {

        int quizId = req.getQuizId();

        // 1. 確認問卷ID -> @Positive(message = "必須為正數")

        // 2. 確認回答不可為空
        if (CollectionUtils.isEmpty(req.getAnswers())) {
            return new GetQuizRes(ResMessage.ANSWER_NOT_FOUND.getCode(),
                    ResMessage.ANSWER_NOT_FOUND.getMessage());
        }
        // 3. 確認問卷填寫者不可有重複email
        if (responseDao.existsByQuizIdAndEmail(req.getQuizId(), req.getEmail())) {
            return new GetQuizRes(ResMessage.EMAIL_DUPLICATED.getCode(),
                    ResMessage.EMAIL_DUPLICATED.getMessage());
        }
        // 4-1. 確認問卷是否有內容
        // 4-2. findByIdActivePublishedTrue 此SQL語法可檢查發布狀態為true
        // 4-3. 確認日期
        Quiz quiz = quizDao.findByIdActivePublishedTrueBetween(quizId, req.getFillInDate());
        if (quiz == null) {
            return new BasicRes(ResMessage.STATUS_DATE_RANGE_ERROR.getCode(),
                    ResMessage.STATUS_DATE_RANGE_ERROR.getMessage());
        }
        // 5. 確認問題
        List<Question> questionList = questionDao.getQuestionsById(quizId); // DB的資料
        if (CollectionUtils.isEmpty(questionList)) {
            return new BasicRes(ResMessage.QUESTION_NOT_FOUND.getCode(),
                    ResMessage.QUESTION_NOT_FOUND.getMessage());
        }
        // 6. 題號 與 選項(1~多個)
        Map<Integer, List<String>> answerMap = req.getAnswers();

        for (Question item : questionList) {
            // 取得KEY後，會得到VALUE(answerList)，也就是使用者的回答選項
            List<String> answerList = answerMap.get(item.getQuestionId());
            // 檢查必填
            if (item.isNecessary() && CollectionUtils.isEmpty(answerList)) {
                return new BasicRes(ResMessage.ANSWER_IS_NECESSARY.getCode(),
                        ResMessage.ANSWER_IS_NECESSARY.getMessage());
            }
            // 單選或文字：不能有多個答案
            if (!item.getType().equals(QuestionType.MULTI.getType())
                    && answerList != null && answerList.size() > 1) {
                return new BasicRes(ResMessage.ANSWER_ERROR.getCode(),
                        ResMessage.ANSWER_ERROR.getMessage());
            }
            // 單選或多選：需要將使用者答案與資料庫答案比對
            if (!item.getType().equals(QuestionType.TEXT.getType())) {
                // 把 Question 中的 options 字串轉成 Options 類別
                List<Options> optionList = new ArrayList<>();
                // 格式轉換就強制用 try...catch...因為不保證能成功轉換
                try {
                    optionList = mapper.readValue(item.getOptionList(), new TypeReference<>() {
                    });
                } catch (Exception e) {
                    return new BasicRes(ResMessage.OPTIONS_TRANSFER_ERROR.getCode(),
                            ResMessage.OPTIONS_TRANSFER_ERROR.getMessage());
                }
                // 蒐集 List<Options> optionList 中所有的 option 並放入 List<String> optionListInDB
                List<String> optionListInDB = new ArrayList<>();
                for (Options opt : optionList) {
                    optionListInDB.add(opt.getOption());
                }
                // 比對 req 中的答案(answerList) 與 資料庫選項(optionList) 是否一致
                if (answerList != null && answerList.size() == 1 && answerList.get(0).equals("")) {
                    // 這表示用戶沒有回答問題，視為有效空答案，跳過選項比對
                    continue;  // 跳過選項比對，直接處理為空答案
                }
                for (String ans : answerList) {
                    if (!optionListInDB.contains(ans)) {
                        return new BasicRes(ResMessage.OPTIONS_MISMATCH.getCode(),
                                ResMessage.OPTIONS_MISMATCH.getMessage());
                    }
                }
            }
        }
        // 7. 儲存回答到DB
        List<Response> resList = new ArrayList<>();
        for (Map.Entry<Integer, List<String>> map : answerMap.entrySet()) {
            // 若 answerList 為 null 或空，跳過該問題，不加的話會取到 null
            List<String> answerList = map.getValue();
            if (answerList == null || answerList.isEmpty()) {
                continue;
            }
            try {
                String str = mapper.writeValueAsString(map.getValue());
                Response response = new Response(req.getQuizId(), map.getKey(), req.getUsername(),
                        req.getPhone(), req.getEmail(), req.getAge(),
                        str, req.getFillInDate());
                resList.add(response);
            } catch (JsonProcessingException e) {
                return new BasicRes(ResMessage.OPTIONS_TRANSFER_ERROR.getCode(),
                        ResMessage.OPTIONS_TRANSFER_ERROR.getMessage());
            }
        }
        // 設定回覆ID
        Integer maxResId = null;
        try {
            maxResId = responseDao.getMaxResponseIdByQuizId(quizId);
            //System.out.println("目前最大填答ID: " + maxResId);
        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println("錯誤填答ID");
        }
        int responseId = (maxResId != null ? maxResId : 0) + 1;
        for (Response res : resList) {
            res.setResponseId(responseId);
        }
        responseDao.saveAll(resList);

        return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
    }

    @Override
    public ResponseRes getAllResponse(int quizId) {
        if (quizId <= 0) {
            return new ResponseRes(ResMessage.PARAM_ID_ERROR.getCode(),
                    ResMessage.PARAM_ID_ERROR.getMessage());
        }
        List<ResponseDTO> resList = responseDao.getAllResponseByQuizId(quizId);
        return new ResponseRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(), resList);
    }

    @Override
    public ResponseRes getResponse(int quizId, int responseId) {
        if (quizId <= 0) {
            return new ResponseRes(ResMessage.PARAM_ID_ERROR.getCode(),
                    ResMessage.PARAM_ID_ERROR.getMessage());
        }
        List<ResponseDTO> res = responseDao.getResponseByQuizIdAndEmail(quizId, responseId);
        return new ResponseRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(), res);
    }

    @Override
    public StatisticsRes getStatistics(int quizId) {
        if (quizId <= 0) {
            return new StatisticsRes(ResMessage.PARAM_ID_ERROR.getCode(),
                    ResMessage.PARAM_ID_ERROR.getMessage());
        }
        // 從資料庫取出該問卷的回覆
        List<StatisticsDTO> statsDTOList = responseDao.getStatisticsByQuizId(quizId);
        // 將 DTO 內容轉為 VO，要再轉回 Res
        List<StatisticsVo> statsVoList = new ArrayList<>();
        // 確認無重複問題 ID （HashSet：不重複、無序、無索引）
        Set<Integer> questionIdSet = new HashSet<>();
        // 每個問題的選項計數 (HashMap：：不重複、無序、無索引、鍵值對)
        Map<Integer, Map<String, Integer>> questionOptionCountMap = new HashMap<>();
        // 問題選項
        List<Options> optionList = new ArrayList<>();
        // 答案
        List<String> answerList = new ArrayList<>();

        // 將選項與問題轉型，並放入Map中
        for (StatisticsDTO dto : statsDTOList) {
            if (!dto.getType().equalsIgnoreCase(QuestionType.TEXT.getType())) {
                // putIfAbsent 指定的 key 不存在時，才會新增 (如果目前沒有問題ID，就會加入此鍵值對)
                // 此功能和 put 的差別：已經存在原有的值會保留，不會覆蓋
                questionOptionCountMap.putIfAbsent(dto.getQuestionId(), new HashMap<>());
                try {
                    optionList = mapper.readValue(dto.getOptionStr(), new TypeReference<>() {
                    });
                    answerList = mapper.readValue(dto.getAnswerStr(), new TypeReference<>() {
                    });
                    // 建立放選項與次數的 Map
                    // 例如：questionOptionCountMap(問題ID, optionCountMap)
                    // Map.get(key)會得到value
                    Map<String, Integer> optionCountMap = questionOptionCountMap.get(dto.getQuestionId());
                    // 選項只需要計算一次
                    for (Options opt : optionList) {
                        optionCountMap.putIfAbsent(opt.getOption(), 0);
                    }
                    // 回答需要統計次數 (要避開資料庫中的[""]，否則會報錯)
                    if (answerList != null && !answerList.isEmpty() && !answerList.contains("")) {
                        for (String ans : answerList) {
                            int count = optionCountMap.get(ans);
                            optionCountMap.put(ans, count + 1);
                        }
                    }
                } catch (Exception e) {
                    return new StatisticsRes(ResMessage.OPTIONS_TRANSFER_ERROR.getCode(),
                            ResMessage.OPTIONS_TRANSFER_ERROR.getMessage());
                }
            }
        }
        // 將結果加入統計 VO 列表中
        // 一題題放進 optionCountMap
        for (StatisticsDTO dto : statsDTOList) {
            // 放入單選與多選的統計結果
            if (!dto.getType().equalsIgnoreCase(QuestionType.TEXT.getType())) {
                Map<String, Integer> optionCountMap = questionOptionCountMap.get(dto.getQuestionId());
                StatisticsVo vo = new StatisticsVo(dto.getQuizName(),
                        dto.getQuestionId(), dto.getTitle(), optionCountMap);
                if (questionIdSet.add(vo.getQuestionId())) {
                    statsVoList.add(vo);
                }
            }
        }
        return new StatisticsRes(ResMessage.SUCCESS.getCode(),
                ResMessage.SUCCESS.getMessage(), statsVoList);
    }

    @Override
    public boolean checkEmail(String email) {
        Long result = responseDao.checkEmail(email); // 返回的為 Long 類型
        return result != null && result > 0;
    }

    @Override
    public StatisticsRes test(int quizId) {
        if (quizId <= 0) {
            return new StatisticsRes(ResMessage.PARAM_ID_ERROR.getCode(),
                    ResMessage.PARAM_ID_ERROR.getMessage());
        }
        // 從資料庫取出該問卷的回覆
        List<StatisticsDTO> statsDTOList = responseDao.getStatisticsByQuizId(quizId);
        // 將 DTO 內容轉為 VO
        List<StatisticsVo> statsVoList = new ArrayList<>();
        // 用來判斷 voList 中是否已存在相同問題編號
        boolean isDuplicated = false;

        a:
        for (StatisticsDTO dto : statsDTOList) {

            // 放入 DTO 傳來的的選項與次數
            // 從 VO 取出有相同問題ID的 VO (目的是不用重新蒐集選項，直接計算次數)
            Map<String, Integer> optionCountMap = new HashMap<>();

            StatisticsVo vo = new StatisticsVo();

            b:
            for (StatisticsVo svoItem : statsVoList) {
                if (svoItem.getQuestionId() == dto.getQuestionId()) {
                    optionCountMap = svoItem.getOptionCountMap();
                    vo = svoItem;
                    isDuplicated = true;
                    break; // 比對到相同問題ID後，不須再比對，直接跳出此for迴圈
                }
            }
            if (!isDuplicated) {
                statsVoList.add(vo);
            }

            // 把 StatisticsDTO 中的 options 字串轉成 Options 類別
            List<Options> optionList = new ArrayList<>();

            // 把 Response 的 answers 字串轉成陣列
            List<String> answerList = new ArrayList<>();

            // 字串轉類別 readValue , 類別轉字串 writeValue
            try {
                // 題型不是 TEXT 時才要轉
                if (!dto.getType().equalsIgnoreCase(QuestionType.TEXT.getType())) {
                    optionList = mapper.readValue(dto.getOptionStr(), new TypeReference<>() {
                    });
                }
                // 以下條件才能將答案轉為字串
                // 1. 需要 answers 不為空
                // 2. 非簡答題目(單選多選)
                if (StringUtils.hasText(dto.getAnswerStr()) &&
                        !dto.getType().equalsIgnoreCase(QuestionType.TEXT.getType())) {
                    answerList = mapper.readValue(dto.getAnswerStr(), new TypeReference<>() {
                    });
                }
            } catch (Exception e) {
                return new StatisticsRes(ResMessage.OPTIONS_TRANSFER_ERROR.getCode(),
                        ResMessage.OPTIONS_TRANSFER_ERROR.getMessage());
            }

            // 題型是 TEXT 不須蒐集選項和回答，直接放入空
            if (dto.getType().equalsIgnoreCase(QuestionType.TEXT.getType())) {
                // 確認 VOList 是否已存在於相關問題編號的 StatisticsVo
                if (isDuplicated) {
                    continue;
                }
                vo.setQuizName(dto.getQuizName());
                vo.setQuestionId(dto.getQuestionId());
                vo.setTitle(dto.getTitle());
                vo.setOptionCountMap(optionCountMap);
                continue;
            }

            // optionList 跟 answerList 都來自資料庫，分別是Question選項和Response答案

            // 蒐集選項 (只要蒐集一次就好)
            // 沒有重複的 VO 需要蒐集選項
            if (!isDuplicated) {
                for (Options opt : optionList) {
                    optionCountMap.put(opt.getOption(), 0);
                }
            }
            // 蒐集答案(計算選項次數)
            for (String ans : answerList) {
                // 計算選項出現次數
                if (optionCountMap.containsKey(ans)) {
                    int count = optionCountMap.get(ans);
                    // 放入選項的次數
                    optionCountMap.put(ans, count + 1);
                }
            }

            // 將回答結果 set 進 vo 裡
            vo.setQuizName(dto.getQuizName());
            vo.setQuestionId(dto.getQuestionId());
            vo.setTitle(dto.getTitle());
            vo.setOptionCountMap(optionCountMap);
            // 最後不須將 vo add 到 statsVoList 中，是因為迴圈開始時，已經有將其加入了
        }
        return new StatisticsRes(ResMessage.SUCCESS.getCode(),
                ResMessage.SUCCESS.getMessage(), statsVoList);
    }


    // 檢查參數的方法
    public BasicRes checkParams(CreateUpdateReq req) {
        // 1. 檢查參數 - 問卷
        if (!StringUtils.hasText(req.getName())) {
            return new BasicRes(ResMessage.PARAM_NAME_ERROR.getCode(),
                    ResMessage.PARAM_NAME_ERROR.getMessage());
        }
        if (!StringUtils.hasText(req.getDescription())) {
            return new BasicRes(ResMessage.PARAM_DESCRIPTION_ERROR.getCode(),
                    ResMessage.PARAM_DESCRIPTION_ERROR.getMessage());
        }
        // 開始時間最晚是今天
        if (req.getStartDate() == null || req.getStartDate().isBefore(LocalDate.now())) {
            return new BasicRes(ResMessage.PARAM_START_DATE_ERROR.getCode(),
                    ResMessage.PARAM_START_DATE_ERROR.getMessage());
        }
        // 今日 <= 開始時間 <= 結束時間
        if (req.getEndDate() == null || req.getEndDate().isBefore(req.getStartDate())) {
            return new BasicRes(ResMessage.PARAM_END_DATE_ERROR.getCode(),
                    ResMessage.PARAM_END_DATE_ERROR.getMessage());
        }

        // 2. 檢查參數 - 問題們
        if (CollectionUtils.isEmpty(req.getQuestionList())) {
            return new BasicRes(ResMessage.PARAM_QUESTION_NOT_FOUND.getCode(),
                    ResMessage.PARAM_QUESTION_NOT_FOUND.getMessage());
        }
        // 必須逐筆檢查每個問題的參數
        for (Question item : req.getQuestionList()) {
            if (item.getQuizId() < 0) {
                return new BasicRes(ResMessage.PARAM_QUESTION_ID_ERROR.getCode(),
                        ResMessage.PARAM_QUESTION_ID_ERROR.getMessage());
            }
            if (!StringUtils.hasText(item.getTitle())) {
                return new BasicRes(ResMessage.PARAM_TITLE_ERROR.getCode(),
                        ResMessage.PARAM_TITLE_ERROR.getMessage());
            }
            // 問題類型必須是 S、M、T 三種
            if (!StringUtils.hasText(item.getType()) || !QuestionType.checkType(item.getType())) {
                return new BasicRes(ResMessage.PARAM_TYPE_ERROR.getCode(),
                        ResMessage.PARAM_TYPE_ERROR.getMessage());
            }
            // 非文字類型時(單多選)，選項沒有值的話，要報錯
            if (!item.getType().equalsIgnoreCase(QuestionType.TEXT.toString())
                    && !StringUtils.hasText(item.getOptionList())) {
                return new BasicRes(ResMessage.PARAM_OPTIONS_ERROR.getCode(),
                        ResMessage.PARAM_OPTIONS_ERROR.getMessage());
            }
        }
        return null;
    }
}
