package com.a14roxgmail.prasanna.healthcareapp.DAO;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;

import com.a14roxgmail.prasanna.healthcareapp.Models.disease;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Prasanna Deshappriya on 12/15/2016.
 */
public class diseaseDAO extends DAO{
    private String name = "disease_name";
    private String description = "description";
    private String treatment = "treatment";
    private SQLiteDatabase sqldb;
    private String command;

    public diseaseDAO(Context context, SQLiteDatabase sqldb) {
        super();
        this.tableName = "disease";
        this.primaryKey = "disease_id";
        this.sqldb = sqldb;
    }

    public List<disease> getDiseaseList() {
        command = "SELECT * FROM "+tableName+" where 1";
        Cursor c = sqldb.rawQuery(command,null);
        //list to store data
        List<disease> diseases = new ArrayList<disease>();
        if(c.moveToFirst()) {
            do {
                disease d = new disease(
                        c.getString(c.getColumnIndex("disease_name")),
                        c.getString(c.getColumnIndex("description")),
                        c.getString(c.getColumnIndex("treatment")));
                diseases.add(d);
            } while (c.moveToNext());
        }
        //Return
        return diseases;
    }

    public disease getDisease(String disease_name){
        command = "SELECT * FROM "+tableName+" WHERE disease_name = " + disease_name;
        Cursor c = sqldb.rawQuery(command,null);
        disease disease = null;
        if(c.moveToFirst()) {
            do {
                disease = new disease(
                        c.getString(c.getColumnIndex("disease_name")),
                        c.getString(c.getColumnIndex("description")),
                        c.getString(c.getColumnIndex("treatment")));
            } while (c.moveToNext());
        }
        return disease;
    }

    public void addDisease(disease disease) {
        //use prepared statements for insert
        command = "INSERT INTO "+tableName+" (disease_name,description,treatment) VALUES (?,?,?)";
        SQLiteStatement statement = sqldb.compileStatement(command);
        statement.bindString(1, disease.getName());
        statement.bindString(2, disease.getDescription());
        statement.bindString(3, disease.getTreatment());
        statement.executeInsert();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void removeDisease(String disease_name){
        command = "DELETE FROM "+tableName+" WHERE disease_name = ?";
        SQLiteStatement statement = sqldb.compileStatement(command);
        statement.bindString(1,disease_name);
        statement.executeUpdateDelete();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void updateDisease(String disease_name, String nDescription, String nTreatement){
        command = "UPDATE "+tableName+" SET init_amount = init_amount + ?";
        SQLiteStatement statement = sqldb.compileStatement(command);
        statement.bindString(1,nDescription);
        statement.bindString(1,nTreatement);
        statement.executeUpdateDelete();
    }
}
