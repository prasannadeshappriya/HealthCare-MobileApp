package com.a14roxgmail.prasanna.healthcareapp.ListView;

/**
 * Created by Prasanna Deshappriya on 12/7/2016.
 */
public class disease {
    private int ID;
    private String disease;
    private String description;
    private String treatment;

    public disease(String disease, int ID, String treatment, String description) {
        this.disease = disease;
        this.ID = ID;
        this.treatment = treatment;
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }
}
