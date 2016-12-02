package com.a14roxgmail.prasanna.healthcareapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Prasanna Deshappriya on 11/30/2016.
 */
public class database extends SQLiteOpenHelper{
    private Context context;
    private String DB_NAME;
    private String TABLE_NAME = "login_detail";
    private String TAG = "TAG";
    private String command;
    public database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
        this.DB_NAME = name;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        command = "CREATE TABLE " + TABLE_NAME + "(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "email VARCHAR(25), " +
                "status INTEGER);";
        Log.i(TAG,command);
        sqLiteDatabase.execSQL(command);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        command = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        Log.i(TAG,command);
        sqLiteDatabase.execSQL(command);
        onCreate(sqLiteDatabase);
    }

    public  void login_detail_update(String email, String status){
        SQLiteDatabase sqldb = getWritableDatabase();
        command = "UPDATE " + TABLE_NAME + " SET status =\"" + status + "\" WHERE email =\"" + email + "\";";
        Log.i(TAG,command);
        sqldb.execSQL(command);
    }

    public void login_data_signout(String email){
        SQLiteDatabase sqldb = getWritableDatabase();
        command = "UPDATE " + TABLE_NAME + " SET status =\"0\" WHERE email =\"" + email + "\";";
        Log.i(TAG,command);
        sqldb.execSQL(command);
    }

    public boolean isExistsUser(String email){
        SQLiteDatabase sqldb = getWritableDatabase();
        command = "SELECT * FROM " + TABLE_NAME + " WHERE email=\"" + email + "\";";
        Log.i(TAG,command);
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
        SQLiteDatabase sqldb = getWritableDatabase();
        command = "SELECT email FROM " + TABLE_NAME + " WHERE status=\"1\";";
        Log.i(TAG,command);
        Cursor c = sqldb.rawQuery(command,null);
        c.moveToFirst();
        if(c.getCount()>0){
            return c.getString(0);
        }else{
            return "";
        }
    }

    public void login_detail_insert(String email){
        SQLiteDatabase sqldb = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("email",email);
        cv.put("status","0");
        sqldb.insert(TABLE_NAME,null,cv);
    }
}
