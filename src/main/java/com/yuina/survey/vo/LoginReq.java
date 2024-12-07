package com.yuina.survey.vo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Valid
public class LoginReq {

    @NotBlank(message = "信箱不可為空")
    @Email(message = "必須是有效的電子信箱。")
    private String email;

    @NotBlank(message = "密碼不可為空")
    @Size(min = 6, message = "密碼長度必須至少 6 個字以上。")
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
