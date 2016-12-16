package com.a14roxgmail.prasanna.healthcareapp.Models;

/**
 * Created by Prasanna Deshappriya on 12/15/2016.
 */
public class disease {
    private Long id;
    private String name;
    private String description;
    private String treatment;

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

    public void setId(Long id){ this.id = id; }

    public void setTreatment(String treatment){ this.treatment = treatment; }

    public void setDescription(String description){ this.description = description; }

    public long getId(){return id;}

    public String getName(){return this.name;}

    public String getDescription(){ return this.description; }

    public String getTreatment(){ return this.treatment; }
}
