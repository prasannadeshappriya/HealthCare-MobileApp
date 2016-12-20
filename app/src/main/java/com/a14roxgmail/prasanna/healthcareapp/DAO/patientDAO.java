package com.a14roxgmail.prasanna.healthcareapp.DAO;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;
import android.util.Log;
import com.a14roxgmail.prasanna.healthcareapp.Models.patient;
import com.a14roxgmail.prasanna.healthcareapp.constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Prasanna Deshappriya on 12/20/2016.
 */
public class patientDAO extends DAO {
    private String name = "patient_name";
    private String date_of_birth = "dob";
    private String nic = "nic";
    private String district_id = "district_id";
    private String last_edit_date = "last_edit_date";
    private String sync_status = "sync_status";
    private SQLiteDatabase sqldb;
    private String command;

    public patientDAO(Context context, SQLiteDatabase sqldb) {
        super();
        this.tableName = "patient";
        this.primaryKey = "patient_id";
        this.sqldb = sqldb;
        sync_status = "0";
    }

    public List<patient> getPatientList() {
        command = "SELECT * FROM "+tableName+" where 1";
        Cursor c = sqldb.rawQuery(command,null);
        //list to store data
        List<patient> patients = new ArrayList<>();
        if(c.moveToFirst()) {
            do {
                patient d = new patient(
                        c.getString(c.getColumnIndex(name)),
                        c.getString(c.getColumnIndex(nic)),
                        c.getString(c.getColumnIndex(date_of_birth)),
                        c.getString(c.getColumnIndex(district_id)),
                        c.getString(c.getColumnIndex(last_edit_date)),
                        c.getString(c.getColumnIndex(sync_status)));
                patients.add(d);
            } while (c.moveToNext());
        }
        //Return
        return patients;
    }

    public List<patient> filter(String field){
        command = "SELECT * FROM "+tableName+" NATURAL JOIN district WHERE " +
                "patient_name LIKE \"%" + field + "%\" OR " +
                "district_name LIKE \"%" + field + "%\";";
        Log.i(constants.TAG,command);
        Cursor c = sqldb.rawQuery(command,null);
        List<patient> filter = new ArrayList<>();
        if(c.moveToFirst()){
            do{
                patient d = new patient(
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

    public patient getPatient(String patient_name){
        command = "SELECT * FROM "+tableName+" WHERE patient_name = \"" + patient_name + "\";";
        Cursor c = sqldb.rawQuery(command,null);
        patient patient = null;
        if(c.moveToFirst()) {
            do {
                patient = new patient(
                        c.getString(c.getColumnIndex(name)),
                        c.getString(c.getColumnIndex(nic)),
                        c.getString(c.getColumnIndex(date_of_birth)),
                        c.getString(c.getColumnIndex(district_id)),
                        c.getString(c.getColumnIndex(last_edit_date)),
                        c.getString(c.getColumnIndex(sync_status)));
            } while (c.moveToNext());
        }
        return patient;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void updatePatient(String nic, String patient_name, String district_id, String date_of_birth){
        command = "UPDATE "+tableName+" SET "+
                "patient_name = ?, " +
                "district_id = ?, " +
                "date_of_birth = ?, " +
                "last_edit_date = ?, " +
                "sync_status = ? WHERE" +
                " nic = ?";
        Log.i(constants.TAG,command);
        SQLiteStatement statement = sqldb.compileStatement(command);
        statement.bindString(1,patient_name);
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

    public void addPatient(patient patient) {
        //use prepared statements for insert
        command = "INSERT INTO "+tableName+" (patient_name,dob,nic,district_id,last_edit_date,sync_status) VALUES (?,?,?,?,?,?)";
        SQLiteStatement statement = sqldb.compileStatement(command);
        statement.bindString(1, patient.getName());
        statement.bindString(2, patient.getDate_of_birth());
        statement.bindString(3, patient.getNic());
        statement.bindString(4, patient.getDistrict_id());
        statement.bindString(5, patient.getLast_edit_date());
        statement.bindString(6, patient.getFlag());
        statement.executeInsert();
    }
}
