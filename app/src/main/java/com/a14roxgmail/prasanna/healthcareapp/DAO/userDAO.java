package com.a14roxgmail.prasanna.healthcareapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.a14roxgmail.prasanna.healthcareapp.Models.patient;
import com.a14roxgmail.prasanna.healthcareapp.Models.user;
import com.a14roxgmail.prasanna.healthcareapp.constants;

import java.util.Date;

/**
 * Created by Prasanna Deshappriya on 12/16/2016.
 */
public class userDAO extends DAO {
    private SQLiteDatabase sqldb;
    private String command;
    private Context context;

    public userDAO(Context context, SQLiteDatabase sqldb) {
        super();
        this.tableName = "user";
        this.primaryKey = "id";
        this.context = context;
        this.sqldb = sqldb;
    }

    //update the user status
    public  void update(user user){
        String nic = user.getNic();
        int status = user.getStatus();
        command = "UPDATE " + tableName + " SET status =\"" + status + "\" WHERE nic =\"" + nic + "\";";
        Log.i(constants.TAG,command);
        sqldb.execSQL(command);
    }

    public void signout(String nic){
        command = "UPDATE " + tableName + " SET status =\"0\" WHERE nic =\"" + nic + "\";";
        Log.i(constants.TAG,command);
        sqldb.execSQL(command);
    }

    public boolean isExistsUser(String nic){
        command = "SELECT * FROM " + tableName + " WHERE nic=\"" + nic + "\";";
        Log.i(constants.TAG,command);
        Cursor c = sqldb.rawQuery(command,null);
        if(c.getCount()>0){
            c.close();
            return true;
        }else{
            c.close();
            return false;
        }

    }

    public String checkAutoLogin(){
        command = "SELECT nic FROM " + tableName + " WHERE status=\"1\";";
        Log.i(constants.TAG,command);
        Cursor c = sqldb.rawQuery(command,null);
        c.moveToFirst();
        if(c.getCount()>0){
            return c.getString(0);
        }else{
            return "";
        }
    }

    public void insert(user user){
        String nic = user.getNic();
        int status = user.getStatus();
        String role = user.getRole();
        String spec = user.getSpecialization();

        ContentValues cv = new ContentValues();
        cv.put("nic",nic);
        cv.put("status",status);
        cv.put("role",role);
        cv.put("specialization",spec);
        sqldb.insert(tableName,null,cv);
    }

    public void create_patient(String patient_name, String dob,String nic, String distrist_id, String flag){
        patientDAO patient_dao = new patientDAO(context,sqldb);
        String last_edit_date = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new Date());
        patient_dao.addPatient(new patient(
                patient_name,
                nic,
                dob,
                distrist_id,
                last_edit_date,
                flag
        ));

    }

    public user getUser(String nic){
        command = "SELECT * FROM "+tableName+" WHERE nic = \"" + nic + "\";";
        Cursor c = sqldb.rawQuery(command,null);
        user user = null;
        if(c.moveToFirst()) {
            do {
                user = new user(
                        c.getString(c.getColumnIndex("nic")),
                        Integer.parseInt(c.getString(c.getColumnIndex("status"))),
                        c.getString(c.getColumnIndex("role")),
                        c.getString(c.getColumnIndex("specialization")));
            } while (c.moveToNext());
        }
        return user;
    }
}
