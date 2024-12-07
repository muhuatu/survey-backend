package com.yuina.survey.repository;

import com.yuina.survey.entity.Quiz;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface QuizDao extends JpaRepository<Quiz, Integer> {

    /**
     * 新增整份問卷
     *
     * @param name
     * @param description
     * @param startDate
     * @param endDate
     * @param published
     */
    @Modifying
    @Transactional
    @Query(value = "insert into quiz (quiz_name, description, start_date, end_date, published)" +
            " values (:name, :description, :startDate, :endDate, :published)", nativeQuery = true)
    public void createQuiz(@Param("name") String name,
                           @Param("description") String description,
                           @Param("startDate") LocalDate startDate,
                           @Param("endDate") LocalDate endDate,
                           @Param("published") boolean published
    );

    /**
     * 取得最新ID
     *
     * @return
     */
    @Query(value = "select last_insert_id()", nativeQuery = true)
    public int getLastInsertId();

    /**
     * 更新問卷
     *
     * @param id
     * @param name
     * @param description
     * @param startDate
     * @param endDate
     * @param published
     */
    @Modifying
    @Transactional
    @Query(value = "update quiz set quiz_name = :name, description = :description, start_date = :startDate" +
            ", end_date = :endDate, published = :published where id = :id", nativeQuery = true)
    public void updateQuiz(@Param("id") int id,
                           @Param("name") String name,
                           @Param("description") String description,
                           @Param("startDate") LocalDate startDate,
                           @Param("endDate") LocalDate endDate,
                           @Param("published") boolean published
    );

    /**
     * 透過ID刪除問卷
     *
     * @param idList
     */
    @Modifying
    @Transactional
    @Query(value = "delete from quiz where id in (?1)", nativeQuery = true)
    public void deleteByIdIn(List<Integer> idList);

    /**
     * 查詢問卷
     *
     * @param name
     * @param startDate
     * @param endDate
     */
    @Query(value = "select * from quiz as qz" +
            " where qz.quiz_name like concat('%', :name, '%')" +
            " and qz.start_date >= :startDate" +
            " and qz.end_date <= :endDate", nativeQuery = true)
    public List<Quiz> search(@Param("name") String name,
                             @Param("startDate") LocalDate startDate,
                             @Param("endDate") LocalDate endDate);

    /**
     * 透過問卷ID查找發布狀態 (JPA語法)
     * @param quizId
     * @return
     */
    //public Quiz findByIdActivePublishedTrue(int quizId);

    /**
     * 透過問卷ID查找發布狀態
     *
     * @param quizId
     * @return
     */
    // 語法中的 published is true 也可寫成 published = true ; null 也適用
    @Query(value = "select * from quiz where id = ?1 and published = true", nativeQuery = true)
    public Quiz findByIdActivePublishedTrue(int quizId);

    /**
     * 透過問卷ID查找發布狀態與填寫日期
     *
     * @param quizId
     * @param fillInDate
     * @return
     */
    @Query(value = "select * from quiz where id = ?1 and published = true " +
            "and start_date <= ?2 and end_date >= ?2", nativeQuery = true)
    public Quiz findByIdActivePublishedTrueBetween(int quizId, LocalDate fillInDate);


}
