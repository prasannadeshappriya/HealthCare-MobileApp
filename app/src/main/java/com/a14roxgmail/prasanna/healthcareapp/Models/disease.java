package com.a14roxgmail.prasanna.healthcareapp.Models;

/**
 * Created by Prasanna Deshappriya on 12/15/2016.
 */
public class disease {
    private Long id;
    private String name;
    private String description;
    private String treatment;
    private String flag;

    public disease(long id, String name){
        this.id = id;
        this.name = name;
    }

    public disease(String name, String description, String treatment){
        this.name = name;
        this.description = description;
        this.treatment = treatment;
    }

    public disease(long id, String name, String description, String treatment){
        this(id, name);
        this.description = description;
        this.treatment = treatment;
    }

    public disease(String name, String description, String treatment, String flag){
        this.name = name;
        this.description = description;
        this.treatment = treatment;
        this.flag = flag;
    }
    public disease(long id, String name, String description, String treatment,String flag){
        this(id, name);
        this.description = description;
        this.treatment = treatment;
        this.flag = flag;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setTreatment(String treatment){
        this.treatment = treatment;
    }

    public void setFlag(String flag){
        this.flag = flag;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public long getId(){
        return id;
    }

    public String getName(){
        return this.name;
    }

    public String getFlag(){
        return this.flag;
    }

    public String getDescription(){
        return this.description;
    }

    public String getTreatment(){
        return this.treatment;
    }
}
