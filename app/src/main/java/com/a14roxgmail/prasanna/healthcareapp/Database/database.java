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
        command = "CREATE TABLE login_detail" + "(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "email VARCHAR(25), " +
                "status INTEGER);";
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
