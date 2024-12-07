package com.yuina.survey.service.ifs;

import com.yuina.survey.vo.BasicRes;
import com.yuina.survey.vo.LoginReq;

public interface LoginService {

    public BasicRes login(LoginReq req);

    public BasicRes add(LoginReq req);

}
