package com.a14roxgmail.prasanna.healthcareapp.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.a14roxgmail.prasanna.healthcareapp.constants;

/**
 * Created by Prasanna Deshappriya on 11/30/2016.
 */
public class database extends SQLiteOpenHelper{
    private Context context;
    private String DB_NAME;
    private String command;
    public database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
        this.DB_NAME = name;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        command = "CREATE TABLE IF NOT EXISTS login_detail" + "(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nic VARCHAR(25), " +
                "status INTEGER);";
        Log.i(constants.TAG,command);
        sqLiteDatabase.execSQL(command);

        command = "CREATE TABLE IF NOT EXISTS disease" + "(" +
                "disease_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "disease_name VARCHAR(50), " +
                "description VARCHAR(255), " +
                "treatment VARCHAR(255), " +
                "last_edit_date DATE, " +
                "sync_status INTEGER);";
        Log.i(constants.TAG,command);
        sqLiteDatabase.execSQL(command);

        command = "CREATE TABLE IF NOT EXISTS district" + "(" +
                "district_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "district_name VARCHAR(100));";
        Log.i(constants.TAG,command);
        sqLiteDatabase.execSQL(command);

        command = "CREATE TABLE IF NOT EXISTS patient" + "(" +
                "patient_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "patient_name VARCHAR(100), " +
                "dob DATE, " +
                "nic VARCHAR(25), " +
                "district_id INTEGER, " +
                "last_edit_date DATE, " +
                "sync_status INTEGER, " +
                "FOREIGN KEY(district_id) REFERENCES district(district_id));";
        Log.i(constants.TAG,command);
        sqLiteDatabase.execSQL(command);

        command = "CREATE TABLE IF NOT EXISTS health_officer" + "(" +
                "health_officer_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "health_officer_name VARCHAR(100), " +
                "dob DATE, " +
                "nic VARCHAR(25), " +
                "district_id INTEGER, " +
                "last_edit_date DATE, " +
                "sync_status INTEGER, " +
                "FOREIGN KEY(district_id) REFERENCES district(district_id));";
        Log.i(constants.TAG,command);
        sqLiteDatabase.execSQL(command);

        command = "CREATE TABLE IF NOT EXISTS medical_officer" + "(" +
                "medical_officer_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "medical_officer_name VARCHAR(100), " +
                "dob DATE, " +
                "nic VARCHAR(25), " +
                "district_id INTEGER, " +
                "last_edit_date DATE, " +
                "sync_status INTEGER, " +
                "FOREIGN KEY(district_id) REFERENCES district(district_id));";
        Log.i(constants.TAG,command);
        sqLiteDatabase.execSQL(command);

        command = "CREATE TABLE IF NOT EXISTS tips" + "(" +
                "tips_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tips_description VARCHAR(255));";
        Log.i(constants.TAG,command);
        sqLiteDatabase.execSQL(command);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        command = "DROP TABLE IF EXISTS login_detail;";
        Log.i(constants.TAG,command);
        sqLiteDatabase.execSQL(command);
        onCreate(sqLiteDatabase);
    }

    public SQLiteDatabase getDatabase(){
        return getWritableDatabase();
    }
}
