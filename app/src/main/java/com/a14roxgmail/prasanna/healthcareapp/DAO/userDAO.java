package com.a14roxgmail.prasanna.healthcareapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.a14roxgmail.prasanna.healthcareapp.Models.user;
import com.a14roxgmail.prasanna.healthcareapp.constants;

/**
 * Created by Prasanna Deshappriya on 12/16/2016.
 */
public class userDAO extends DAO {
    private String name = "login_detail";
    private String description = "description";
    private String treatment = "treatment";
    private SQLiteDatabase sqldb;
    private String command;

    public userDAO(Context context, SQLiteDatabase sqldb) {
        super();
        this.tableName = "login_detail";
        this.primaryKey = "id";
        this.sqldb = sqldb;
    }

    public  void update(user user){
        String email = user.getEmail();
        int status = user.getStatus();
        command = "UPDATE " + tableName + " SET status =\"" + status + "\" WHERE email =\"" + email + "\";";
        Log.i(constants.TAG,command);
        sqldb.execSQL(command);
    }

    public void signout(String email){
        command = "UPDATE " + tableName + " SET status =\"0\" WHERE email =\"" + email + "\";";
        Log.i(constants.TAG,command);
        sqldb.execSQL(command);
    }

    public boolean isExistsUser(String email){
        command = "SELECT * FROM " + tableName + " WHERE email=\"" + email + "\";";
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
        command = "SELECT email FROM " + tableName + " WHERE status=\"1\";";
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
        String email = user.getEmail();
        int status = user.getStatus();
        ContentValues cv = new ContentValues();
        cv.put("email",email);
        cv.put("status",status);
        sqldb.insert(tableName,null,cv);
    }
}
