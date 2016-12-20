package com.a14roxgmail.prasanna.healthcareapp.DAO;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;
import android.util.Log;

import com.a14roxgmail.prasanna.healthcareapp.Models.medical_officer;
import com.a14roxgmail.prasanna.healthcareapp.constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Prasanna Deshappriya on 12/21/2016.
 */

public class medical_officerDAO extends DAO{
    private String name = "medical_officer_name";
    private String date_of_birth = "dob";
    private String nic = "nic";
    private String district_id = "district_id";
    private String last_edit_date = "last_edit_date";
    private String sync_status = "sync_status";
    private SQLiteDatabase sqldb;
    private String command;

    public medical_officerDAO(Context context, SQLiteDatabase sqldb) {
        super();
        this.tableName = "medical_officer";
        this.primaryKey = "medical_officer";
        this.sqldb = sqldb;
    }

    public List<medical_officer> getMedicalOfficerList() {
        command = "SELECT * FROM "+tableName+" where 1";
        Cursor c = sqldb.rawQuery(command,null);
        //list to store data
        List<medical_officer> medical_officers = new ArrayList<>();
        if(c.moveToFirst()) {
            do {
                medical_officer d = new medical_officer(
                        c.getString(c.getColumnIndex(name)),
                        c.getString(c.getColumnIndex(nic)),
                        c.getString(c.getColumnIndex(date_of_birth)),
                        c.getString(c.getColumnIndex(district_id)),
                        c.getString(c.getColumnIndex(last_edit_date)),
                        c.getString(c.getColumnIndex(sync_status)));
                medical_officers.add(d);
            } while (c.moveToNext());
        }
        //Return
        return medical_officers;
    }

    public List<medical_officer> filter(String field){
        command = "SELECT * FROM "+tableName+" WHERE " +
                "medical_officer_name LIKE \"%" + field + "%\";";
        Log.i(constants.TAG,command);
        Cursor c = sqldb.rawQuery(command,null);
        List<medical_officer> filter = new ArrayList<>();
        if(c.moveToFirst()){
            do{
                medical_officer d = new medical_officer(
                        c.getString(c.getColumnIndex(name)),
                        c.getString(c.getColumnIndex(nic)),
                        c.getString(c.getColumnIndex(date_of_birth)),
                        c.getString(c.getColumnIndex(district_id)),
                        c.getString(c.getColumnIndex(last_edit_date)),
                        c.getString(c.getColumnIndex(sync_status)));
                filter.add(d);
            }while (c.moveToNext());
        }
        return filter;
    }

    public medical_officer getMedicalOfficer(String nic_number){
        command = "SELECT * FROM "+tableName+" WHERE nic = \"" + nic_number + "\";";
        Cursor c = sqldb.rawQuery(command,null);
        medical_officer medical_officer = null;
        if(c.moveToFirst()) {
            do {
                medical_officer = new medical_officer(
                        c.getString(c.getColumnIndex(name)),
                        c.getString(c.getColumnIndex(nic)),
                        c.getString(c.getColumnIndex(date_of_birth)),
                        c.getString(c.getColumnIndex(district_id)),
                        c.getString(c.getColumnIndex(last_edit_date)),
                        c.getString(c.getColumnIndex(sync_status)));
            } while (c.moveToNext());
        }
        return medical_officer;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void updatePMedicalOfficer(String nic, String medical_officer_name, String district_id, String date_of_birth){
        command = "UPDATE "+tableName+" SET "+
                "medical_officer_name = ?, " +
                "district_id = ?, " +
                "date_of_birth = ?, " +
                "last_edit_date = ?, " +
                "sync_status = ? WHERE" +
                " nic = ?";
        Log.i(constants.TAG,command);
        SQLiteStatement statement = sqldb.compileStatement(command);
        statement.bindString(1,medical_officer_name);
        statement.bindString(2,district_id);
        statement.bindString(3,date_of_birth);
        statement.bindString(4,new SimpleDateFormat("\"yyyy-MM-dd\"").format(new Date()));
        statement.bindString(5,"0");
        statement.bindString(3,nic);
        statement.executeUpdateDelete();
    }
    public String get_district_name_by_id(String id){
        command = "SELECT district_name FROM district WHERE district_id=\"" + id + "\";";
        Log.i(constants.TAG,command);
        Cursor c = sqldb.rawQuery(command,null);
        if(c.getCount()>0){
            c.close();
            return c.getString(c.getColumnIndex("district_id"));
        }else{
            c.close();
            return "Unknown District";
        }
    }

    public void addMedicalOfficer(medical_officer medical_officer) {
        //use prepared statements for insert
        command = "INSERT INTO "+tableName+" (medical_officer_name,dob,nic,district_id,last_edit_date,sync_status) VALUES (?,?,?,?,?,?)";
        SQLiteStatement statement = sqldb.compileStatement(command);
        statement.bindString(1, medical_officer.getName());
        statement.bindString(2, medical_officer.getDate_of_birth());
        statement.bindString(3, medical_officer.getNic());
        statement.bindString(4, medical_officer.getDistrict_id());
        statement.bindString(5, medical_officer.getLast_edit_date());
        statement.bindString(6, medical_officer.getFlag());
        statement.executeInsert();
    }
}
