package com.a14roxgmail.prasanna.healthcareapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.a14roxgmail.prasanna.healthcareapp.Models.token;
import com.a14roxgmail.prasanna.healthcareapp.constants;

/**
 * Created by Prasanna Deshappriya on 12/21/2016.
 */
public class tokenDAO extends DAO {
    private SQLiteDatabase sqldb;
    private String command;
    private Context context;

    public tokenDAO(Context context, SQLiteDatabase sqldb) {
        super();
        this.tableName = "token";
        this.primaryKey = "token_id";
        this.context = context;
        this.sqldb = sqldb;
    }

    public void insert(token token){
        String nic = token.getNic();
        String token_chr= token.getToken();
        ContentValues cv = new ContentValues();
        cv.put("token",token_chr);
        cv.put("nic",nic.toUpperCase());
        sqldb.insert(tableName,null,cv);
    }

    public token getToken(String nic){
        command = "SELECT * FROM "+tableName+" WHERE nic = \"" + nic.toUpperCase() + "\";";
        Cursor c = sqldb.rawQuery(command,null);
        token token = null;
        if(c.moveToFirst()) {
            do {
                token = new token(
                        c.getString(c.getColumnIndex("nic")),
                        c.getString(c.getColumnIndex("token")));
            } while (c.moveToNext());
        }
        return token;
    }

    //update the user status
    public  void update(token token){
        String nic = token.getNic();
        String newToken = token.getToken();
        command = "UPDATE " + tableName + " SET token =\"" + newToken + "\" WHERE nic =\"" + nic + "\";";
        Log.i(constants.TAG,command);
        sqldb.execSQL(command);
    }

    public boolean isExistsToken(String nic){
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
}
