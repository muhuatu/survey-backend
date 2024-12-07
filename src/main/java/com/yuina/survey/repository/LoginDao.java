package com.yuina.survey.repository;

import com.yuina.survey.entity.Login;
import com.yuina.survey.vo.BasicRes;
import com.yuina.survey.vo.LoginReq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LoginDao extends JpaRepository<Login, Integer> {

    @Query(value = "select * from admin where email = ?1", nativeQuery = true)
    public Login getInfoById(String email);
}
