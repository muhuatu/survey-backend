package com.yuina.survey.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "admin")
public class Login {

    @Id
    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    public Login() {
    }

    public Login(String username, String password) {
        this.email = username;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
