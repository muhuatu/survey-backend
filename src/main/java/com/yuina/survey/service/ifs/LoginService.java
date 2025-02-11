package com.yuina.survey.service.ifs;

import com.yuina.survey.vo.BasicRes;
import com.yuina.survey.vo.LoginReq;
import com.yuina.survey.vo.LoginRes;

public interface LoginService {

    public LoginRes login(LoginReq req);

    public LoginRes add(LoginReq req);

}
