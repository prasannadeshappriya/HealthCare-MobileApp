package com.a14roxgmail.prasanna.healthcareapp.DAO;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;
import android.util.Log;

import com.a14roxgmail.prasanna.healthcareapp.Models.disease;
import com.a14roxgmail.prasanna.healthcareapp.constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prasanna Deshappriya on 12/15/2016.
 */
public class diseaseDAO extends DAO{
    private String name = "disease_name";
    private String description = "description";
    private String treatment = "treatment";
    private String sync_status = "sync_status";
    private SQLiteDatabase sqldb;
    private String command;

    public diseaseDAO(Context context, SQLiteDatabase sqldb) {
        super();
        this.tableName = "disease";
        this.primaryKey = "disease_id";
        this.sqldb = sqldb;
        sync_status = "0";
    }

    public List<disease> getDiseaseList() {
        command = "SELECT * FROM "+tableName+" where 1";
        Cursor c = sqldb.rawQuery(command,null);
        //list to store data
        List<disease> diseases = new ArrayList<>();
        if(c.moveToFirst()) {
            do {
                disease d = new disease(
                        c.getString(c.getColumnIndex(name)),
                        c.getString(c.getColumnIndex(description)),
                        c.getString(c.getColumnIndex(treatment)),
                        c.getString(c.getColumnIndex(sync_status)));
                diseases.add(d);
            } while (c.moveToNext());
        }
        //Return
        return diseases;
    }

    public List<disease> filter(String field){
        command = "SELECT * FROM "+tableName+" WHERE " +
                "disease_name LIKE \"%" + field + "%\" OR " +
                "description LIKE \"%" + field + "%\" OR " +
                "treatment LIKE \"%" + field + "%\";";
        Log.i(constants.TAG,command);
        Cursor c = sqldb.rawQuery(command,null);
        List<disease> filter = new ArrayList<>();
        if(c.moveToFirst()){
            do{
                disease d = new disease(
                        c.getString(c.getColumnIndex(name)),
                        c.getString(c.getColumnIndex(description)),
                        c.getString(c.getColumnIndex(treatment)),
                        c.getString(c.getColumnIndex(sync_status)));
                filter.add(d);
            }while (c.moveToNext());
        }
        return filter;
    }

    public disease getDisease(String disease_name){
        command = "SELECT * FROM "+tableName+" WHERE disease_name = \"" + disease_name + "\";";
        Cursor c = sqldb.rawQuery(command,null);
        disease disease = null;
        if(c.moveToFirst()) {
            do {
                disease = new disease(
                        c.getString(c.getColumnIndex(name)),
                        c.getString(c.getColumnIndex(description)),
                        c.getString(c.getColumnIndex(treatment)),
                        c.getString(c.getColumnIndex(sync_status)));
            } while (c.moveToNext());
        }
        return disease;
    }

    public void addDisease(disease disease) {
        //use prepared statements for insert
        command = "INSERT INTO "+tableName+" (disease_name,description,treatment,sync_status) VALUES (?,?,?,?)";
        SQLiteStatement statement = sqldb.compileStatement(command);
        statement.bindString(1, disease.getName());
        statement.bindString(2, disease.getDescription());
        statement.bindString(3, disease.getTreatment());
        statement.bindString(4, disease.getFlag());
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
        command = "UPDATE "+tableName+" SET "+
                "description = ?, " +
                "treatment = ? WHERE" +
                " disease_name = ?";
        Log.i(constants.TAG,command);
        SQLiteStatement statement = sqldb.compileStatement(command);
        statement.bindString(1,nDescription);
        statement.bindString(2,nTreatement);
        statement.bindString(3,disease_name);
        statement.executeUpdateDelete();
    }

}
