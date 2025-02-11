package com.yuina.survey.vo;
import com.yuina.survey.entity.Login;

public class LoginRes extends BasicRes {

    private Login login;

    public LoginRes() {
    }

    public LoginRes(Login login) {
        this.login = login;
    }

    public LoginRes(int code, String message, Login login) {
        super(code, message);
        this.login = login;
    }

    public LoginRes(int code, String message) {
        super(code, message);
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }
}
