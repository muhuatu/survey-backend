package com.yuina.survey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

// 因為有使用 spring-boot-starter-security 此依賴，要排除預設的基本安全性設定(帳密登入驗證)
// 排除帳密登入驗證就是加上 exclude = SecurityAutoConfiguration.class
// 等號後面若有多個 class 時，就要用{}大括號，一個 class 時大括號可有可無
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class SurveyApplication {

	public static void main(String[] args) {
		SpringApplication.run(SurveyApplication.class, args);
	}

}
