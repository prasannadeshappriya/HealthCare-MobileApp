package com.a14roxgmail.prasanna.healthcareapp.DAO;

/**
 * Created by Prasanna Deshappriya on 12/15/2016.
 */
public abstract class DAO {
    protected String tableName;
    protected String primaryKey;

    public DAO(){
        this.tableName = "";
        this.primaryKey = "";
    }
    public void getName(){

    }
}
