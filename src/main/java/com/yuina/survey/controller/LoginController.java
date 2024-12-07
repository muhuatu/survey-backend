package com.yuina.survey.controller;

import com.yuina.survey.service.ifs.LoginService;
import com.yuina.survey.vo.BasicRes;
import com.yuina.survey.vo.LoginReq;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin/")
@CrossOrigin
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping(value = "login")
    public BasicRes login(@Valid @RequestBody LoginReq req){
        return loginService.login(req);
    }

    @PostMapping(value = "add-info")
    public BasicRes add(@Valid @RequestBody LoginReq req){
        return loginService.add(req);
    }

}
