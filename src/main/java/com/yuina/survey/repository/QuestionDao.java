package com.yuina.survey.repository;

import com.yuina.survey.entity.Question;
import com.yuina.survey.entity.QuestionId;
import com.yuina.survey.entity.Quiz;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question, QuestionId> {

    /**
     * 新增問題
     *
     * @param quizId
     * @param title
     * @param type
     * @param necessary
     * @param optionList
     */
    @Modifying
    @Transactional
    @Query(value = "insert into question (quiz_id, question_id, title, type, necessary, choice)" +
            " values (:quizId, :questionId, :title, :type, :necessary, :optionList)", nativeQuery = true)
    public void createQuestion(@Param("quizId") int quizId,
                               @Param("questionId") int questionId,
                               @Param("title") String title,
                               @Param("type") String type,
                               @Param("necessary") boolean necessary,
                               @Param("optionList") String optionList
    );

    /**
     * 透過一個問卷ID刪除問題
     *
     * @param quizId
     */
    @Modifying
    @Transactional
    @Query(value = "delete from question where quiz_id = ?1", nativeQuery = true)
    public void deleteByQuizId(int quizId);

    /**
     * 透過多個問卷ID刪除問題
     *
     * @param quizIdList
     */
    @Modifying
    @Transactional
    @Query(value = "delete from question where quiz_id in (?1)", nativeQuery = true)
    public void deleteByQuizIdIn(List<Integer> quizIdList);

    /**
     * 透過問卷ID查找所有問題
     *
     * @param quizId
     * @return
     */
    @Query(value = "select * from question where quiz_id = ?1", nativeQuery = true)
    public List<Question> getQuestionsById(int quizId);


}
