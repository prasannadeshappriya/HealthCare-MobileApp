package com.a14roxgmail.prasanna.healthcareapp.Models;

/**
 * Created by Prasanna Deshappriya on 12/15/2016.
 */
public class user {
    private String email;
    private int status; //to autologin process

    public user(String email, int status) {
        this.email = email;
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
