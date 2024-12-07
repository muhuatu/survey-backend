package com.yuina.survey.repository;

import com.yuina.survey.entity.Response;
import com.yuina.survey.entity.ResponseId;
import com.yuina.survey.vo.ResponseDTO;
import com.yuina.survey.vo.StatisticsDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResponseDao extends JpaRepository<Response, ResponseId> {

    /**
     * 藉由問卷ID和信箱判斷此填答是否存在
     *
     * @param quizId
     * @param email
     */
    public boolean existsByQuizIdAndEmail(int quizId, String email);

    /**
     * 透過ID刪除問卷
     *
     * @param quizIdList
     */
    @Modifying
    @Transactional
    @Query(value = "delete from response where quiz_id in (?1)", nativeQuery = true)
    public void deleteByQuizIdIn(List<Integer> quizIdList);

    /**
     * 藉由問卷ID找出所有人的填答
     *
     * @param quizId
     * @return
     */
    @Query(value = "select new com.yuina.survey.vo.ResponseDTO(" +
            " res.responseId, qz.id, res.fillInDate, qz.name, qz.description," +
            " res.username, res.phone, res.email, res.age," +
            " qu.questionId, qu.title, res.answers)" +
            " from Quiz as qz " +
            " join Question as qu on qz.id = qu.quizId " +
            " join Response as res on qu.quizId = res.quizId" +
            " and qu.questionId = res.questionId" +
            " where qz.id = ?1", nativeQuery = false)
    // nativeQuery = true 是因為有 @Column 來和資料庫比對(JPQL語法)
    public List<ResponseDTO> getAllResponseByQuizId(int quizId);

    /**
     * 藉由問卷ID和信箱找出該用戶的填答
     *
     * @param quizId
     * @param responseId
     * @return
     */
    @Query(value = "select new com.yuina.survey.vo.ResponseDTO(" +
            " res.responseId, qz.id, res.fillInDate, qz.name, qz.description," +
            " res.username, res.phone, res.email, res.age," +
            " qu.questionId, qu.title, res.answers)" +
            " from Quiz as qz " +
            " join Question as qu on qz.id = qu.quizId " +
            " join Response as res on qu.quizId = res.quizId" +
            " and qu.questionId = res.questionId" +
            " where qz.id = ?1 and res.responseId = ?2", nativeQuery = false)
    public List<ResponseDTO> getResponseByQuizIdAndEmail(int quizId, int responseId);

    /**
     * 藉由問卷ID找出該用戶的回覆ID
     *
     * @param quizId
     * @return
     */
    @Query(value = "select COALESCE(MAX(response_id), 0) from response where quiz_id = ?1", nativeQuery = true)
    public int getMaxResponseIdByQuizId(int quizId);

    /**
     * 藉由問卷ID找出所有用戶的統計資料
     *
     * @param quizId
     * @return
     */
    @Query(value = "select new com.yuina.survey.vo.StatisticsDTO(" +
            "qz.name, qu.questionId, qu.title, qu.type, qu.optionList, res.answers)" +
            " from Quiz as qz join Question as qu on qz.id = qu.quizId " +
            " join Response as res on qu.quizId = res.quizId " +
            " and qu.questionId = res.questionId where qz.id = ?1", nativeQuery = false)
    public List<StatisticsDTO> getStatisticsByQuizId(int quizId);


    /**
     * 確認填寫人的信箱是否有重複
     *
     * @param email
     * @return
     */
    @Query(value = "select exists (select 1 from response where email = ?1)", nativeQuery = true)
    public Long checkEmail(String email);

}
