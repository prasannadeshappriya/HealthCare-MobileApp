package com.a14roxgmail.prasanna.healthcareapp.Models;

/**
 * Created by Prasanna Deshappriya on 12/21/2016.
 */
public class token {
    private int token_id;
    private String token;
    private String nic;

    public token(String nic, String token) {
        this.nic = nic;
        this.token = token;
    }

    public token(int token_id, String nic, String token) {
        this.token_id = token_id;
        this.nic = nic;
        this.token = token;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public int getToken_id() {
        return token_id;
    }

    public void setToken_id(int token_id) {
        this.token_id = token_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
