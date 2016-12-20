package com.a14roxgmail.prasanna.healthcareapp.Models;

/**
 * Created by Prasanna Deshappriya on 12/15/2016.
 */
public class user {
    private String nic;
    private int status; //to auto login process

    public user(String nic, int status) {
        this.nic = nic;
        this.status = status;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String email) {
        this.nic = email;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
