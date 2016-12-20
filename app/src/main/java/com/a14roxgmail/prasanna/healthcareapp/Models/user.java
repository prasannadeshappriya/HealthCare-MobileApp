package com.a14roxgmail.prasanna.healthcareapp.Models;

/**
 * Created by Prasanna Deshappriya on 12/15/2016.
 */
public class user {
    private String nic;
    private int status; //to auto login process
    private String role;
    private String specialization;

    public user(String nic, int status, String role, String specialization) {
        this.nic = nic;
        this.status = status;
        this.role = role;
        this.specialization = specialization;
    }

    public user(String nic, int status, String role) {
        this.nic = nic;
        this.status = status;
        this.role = role;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
