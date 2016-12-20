package com.a14roxgmail.prasanna.healthcareapp.DAO;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;
import android.util.Log;
import com.a14roxgmail.prasanna.healthcareapp.Models.health_officer;
import com.a14roxgmail.prasanna.healthcareapp.constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Prasanna Deshappriya on 12/21/2016.
 */
public class health_officerDAO extends DAO{
    private String name = "health_officer_name";
    private String date_of_birth = "dob";
    private String nic = "nic";
    private String district_id = "district_id";
    private String last_edit_date = "last_edit_date";
    private String sync_status = "sync_status";
    private SQLiteDatabase sqldb;
    private String command;

    public health_officerDAO(Context context, SQLiteDatabase sqldb) {
        super();
        this.tableName = "health_officer";
        this.primaryKey = "health_officer_id";
        this.sqldb = sqldb;
    }

    public List<health_officer> getHealthOfficerList() {
        command = "SELECT * FROM "+tableName+" where 1";
        Cursor c = sqldb.rawQuery(command,null);
        //list to store data
        List<health_officer> health_officers = new ArrayList<>();
        if(c.moveToFirst()) {
            do {
                health_officer d = new health_officer(
                        c.getString(c.getColumnIndex(name)),
                        c.getString(c.getColumnIndex(nic)),
                        c.getString(c.getColumnIndex(date_of_birth)),
                        c.getString(c.getColumnIndex(district_id)),
                        c.getString(c.getColumnIndex(last_edit_date)),
                        c.getString(c.getColumnIndex(sync_status)));
                health_officers.add(d);
            } while (c.moveToNext());
        }
        //Return
        return health_officers;
    }

    public List<health_officer> filter(String field){
        command = "SELECT * FROM "+tableName+" WHERE " +
                "health_officer_name LIKE \"%" + field + "%\";";
        Log.i(constants.TAG,command);
        Cursor c = sqldb.rawQuery(command,null);
        List<health_officer> filter = new ArrayList<>();
        if(c.moveToFirst()){
            do{
                health_officer d = new health_officer(
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

    public health_officer getHealthOfficer(String nic_number){
        command = "SELECT * FROM "+tableName+" WHERE nic = \"" + nic_number + "\";";
        Cursor c = sqldb.rawQuery(command,null);
        health_officer health_officer = null;
        if(c.moveToFirst()) {
            do {
                health_officer = new health_officer(
                        c.getString(c.getColumnIndex(name)),
                        c.getString(c.getColumnIndex(nic)),
                        c.getString(c.getColumnIndex(date_of_birth)),
                        c.getString(c.getColumnIndex(district_id)),
                        c.getString(c.getColumnIndex(last_edit_date)),
                        c.getString(c.getColumnIndex(sync_status)));
            } while (c.moveToNext());
        }
        return health_officer;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void updateHealthOfficer(String nic, String health_officer_name, String district_id, String date_of_birth){
        command = "UPDATE "+tableName+" SET "+
                "health_officer_name = ?, " +
                "district_id = ?, " +
                "date_of_birth = ?, " +
                "last_edit_date = ?, " +
                "sync_status = ? WHERE" +
                " nic = ?";
        Log.i(constants.TAG,command);
        SQLiteStatement statement = sqldb.compileStatement(command);
        statement.bindString(1,health_officer_name);
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

    public void addHealthOfficer(health_officer health_officer) {
        //use prepared statements for insert
        command = "INSERT INTO "+tableName+" (health_officer_name,dob,nic,district_id,last_edit_date,sync_status) VALUES (?,?,?,?,?,?)";
        SQLiteStatement statement = sqldb.compileStatement(command);
        statement.bindString(1, health_officer.getName());
        statement.bindString(2, health_officer.getDate_of_birth());
        statement.bindString(3, health_officer.getNic());
        statement.bindString(4, health_officer.getDistrict_id());
        statement.bindString(5, health_officer.getLast_edit_date());
        statement.bindString(6, health_officer.getFlag());
        statement.executeInsert();
    }
}
