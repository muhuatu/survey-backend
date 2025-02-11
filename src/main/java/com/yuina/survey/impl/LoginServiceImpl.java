package com.yuina.survey.impl;

import com.yuina.survey.constants.ResMessage;
import com.yuina.survey.entity.Login;
import com.yuina.survey.repository.LoginDao;
import com.yuina.survey.service.ifs.LoginService;
import com.yuina.survey.vo.BasicRes;
import com.yuina.survey.vo.LoginReq;
import com.yuina.survey.vo.LoginRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginDao loginDao;

    @Override
    public LoginRes login(LoginReq req) {

        String email = req.getEmail();
        String password = req.getPassword();

        // 1. 檢查帳戶是否存在
        Login login = loginDao.getInfoById(email);
        if (login == null) {
            return new LoginRes(ResMessage.ACCOUNT_NOT_EXIST.getCode(),
                    ResMessage.ACCOUNT_NOT_EXIST.getMessage());
        }
        // 2. 加密與驗證密碼
        if (!isPasswordValid(email, password)) {
            return new LoginRes(ResMessage.INVALID_PASSWORD.getCode(),
                    ResMessage.INVALID_PASSWORD.getMessage());
        }
        // 3. 驗證成功
        return new LoginRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(), login);
    }

    @Override
    public LoginRes add(LoginReq req) {

        String email = req.getEmail();
        String password = req.getPassword();

        // 1. 帳戶若存在則不可新增
        Login login = loginDao.getInfoById(email);

        if (login != null) {
            return new LoginRes(ResMessage.ACCOUNT_IS_EXIST.getCode(),
                    ResMessage.ACCOUNT_IS_EXIST.getMessage());
        }

        login = new Login(email, password);

        // 2. 加密
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        login.setPassword(encoder.encode(password));
        System.out.println(login.getEmail() + login.getPassword());

        // 3. 新增至資料庫
        try {
            loginDao.save(login);
        } catch (Exception e) {
            return new LoginRes(ResMessage.ADD_INFO_FAILED.getCode(),
                    ResMessage.ADD_INFO_FAILED.getMessage());
        }
        return new LoginRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
    }


    // 加密 ＆ 驗證密碼
    public boolean isPasswordValid(String email, String password) {
        Login login = loginDao.getInfoById(email);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(password, login.getPassword());
    }

}
