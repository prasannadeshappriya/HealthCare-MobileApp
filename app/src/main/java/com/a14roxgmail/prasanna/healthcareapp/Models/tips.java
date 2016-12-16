package com.a14roxgmail.prasanna.healthcareapp.Models;

/**
 * Created by Prasanna Deshappriya on 12/7/2016.
 */
public class tips {
    private int ID;
    private String Detail;

    public tips(String detail, int ID) {
        Detail = detail;
        this.ID = ID;
    }

    public String getDetail() {
        return Detail;
    }

    public void setDetail(String detail) {
        Detail = detail;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
