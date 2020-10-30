package com.virtuallab.authentication;

import java.io.Serializable;

public class UserLogin implements Serializable {

    private String email;
    private String password;

    public UserLogin() {
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
