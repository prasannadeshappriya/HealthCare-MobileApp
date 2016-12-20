package com.a14roxgmail.prasanna.healthcareapp.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.a14roxgmail.prasanna.healthcareapp.Models.disease;
import com.a14roxgmail.prasanna.healthcareapp.Models.district;

import java.util.ArrayList;

/**
 * Created by Prasanna Deshappriya on 12/20/2016.
 */
public class districtDAO extends DAO {
    private String districtName = "district_name";
    private String command;
    private SQLiteDatabase sqldb;
    private Context context;

    public districtDAO(Context context, SQLiteDatabase sqldb){
        this.tableName = "district";
        this.primaryKey = "district_id";
        this.sqldb = sqldb;
    }

    public String getDistrict(Long id){
        command = "SELECT * FROM "+tableName+" WHERE disease_id = " + id;
        Cursor c = sqldb.rawQuery(command,null);
        disease disease = null;
        c.moveToFirst();
        if(c.getCount()>0){
            return c.getString(c.getColumnIndex(districtName));
        }else{
            return "error";
        }
    }

    public void addDistrict(district district) {
        //use prepared statements for insert
        command = "INSERT INTO "+tableName+" (district_name) VALUES (?)";
        SQLiteStatement statement = sqldb.compileStatement(command);
        statement.bindString(1, district.getDistrict_name());
        statement.executeInsert();
    }
}
