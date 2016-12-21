package com.a14roxgmail.prasanna.healthcareapp.Models;

/**
 * Created by Prasanna Deshappriya on 12/22/2016.
 */

public class statistics {
    private String[] items = new String[]{
        "Ampara", "Anuradhapura", "Badulla", "Batticaloa",
                "Colombo", "Galle", "Gampaha", "Hambantota",
                "Jaffna", "Kalutara", "Kandy", "Kegalle",
                "Kilinochchi", "Kurunegala", "Mannar", "Matale",
                "Matara", "Monaragala", "Mullaitivu", "Nuwara Eliya",
                "Polonnaruwa", "Puttalam", "Ratnapura", "Trincomalee",
                "Vavuniya"
    };
    private int id;
    private String symptoms;
    private String district_id;
    private String description;
    private String district_name;
    private String treatement;
    private String count;
    private String disease_name;

    public statistics(int id, String district_id, String description, String count, String symptoms, String treatement, String disease_name) {
        this.id = id;
        this.district_id = district_id;
        this.description = description;
        this.count = count;
        this.symptoms = symptoms;
        this.treatement = treatement;
        this.disease_name = disease_name;
    }

    public String getDisease(){
        return disease_name;
    }

    public void setDisease(String disease){
        this.disease_name = disease;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(String district_id) {
        this.district_id = district_id;
    }

    public String getDistrict_name() {
        return items[Integer.parseInt(district_id)-1];
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getTreatement() {
        return treatement;
    }

    public void setTreatement(String treatement) {
        this.treatement = treatement;
    }
}
