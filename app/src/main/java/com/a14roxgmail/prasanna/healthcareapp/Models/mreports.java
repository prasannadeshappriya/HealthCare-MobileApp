package com.a14roxgmail.prasanna.healthcareapp.Models;

/**
 * Created by Prasanna Deshappriya on 12/16/2016.
 */
public class mreports {
    private int id;
    private String patient_name;
    private String disease_name;
    private String prescription;
    private String comments;

    public mreports(String patient_name, String disease_name, String prescription, String comments) {
        this.patient_name = patient_name;
        this.disease_name = disease_name;
        this.prescription = prescription;
        this.comments = comments;
    }

    public mreports(int id, String patient_name, String disease_name, String prescription, String comments) {
        this.id = id;
        this.patient_name = patient_name;
        this.disease_name = disease_name;
        this.prescription = prescription;
        this.comments = comments;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDisease_name() {
        return disease_name;
    }

    public void setDisease_name(String disease_name) {
        this.disease_name = disease_name;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
