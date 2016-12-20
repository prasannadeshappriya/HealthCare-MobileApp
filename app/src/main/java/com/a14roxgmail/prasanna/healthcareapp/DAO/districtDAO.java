package com.a14roxgmail.prasanna.healthcareapp.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.a14roxgmail.prasanna.healthcareapp.Models.disease;
import com.a14roxgmail.prasanna.healthcareapp.Models.district;
import com.a14roxgmail.prasanna.healthcareapp.constants;

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

    //manually insert the district list at the beginning
    public void init() {
        command = "SELECT COUNT(district_id) FROM "+tableName+";";
        Cursor c = sqldb.rawQuery(command,null);
        c.moveToFirst();
        if(c.getString(0).equals("0")){
            Log.i(constants.TAG, "Adding district information to the database");
            addDistrict(new district(1,"Ampara"));
            addDistrict(new district(2,"Anuradhapura"));
            addDistrict(new district(3,"Badulla"));
            addDistrict(new district(4,"Batticaloa"));
            addDistrict(new district(5,"Colombo"));
            addDistrict(new district(6,"Galle"));
            addDistrict(new district(7,"Gampaha"));
            addDistrict(new district(8,"Hambantota"));
            addDistrict(new district(9,"Jaffna"));
            addDistrict(new district(10,"Kalutara"));
            addDistrict(new district(11,"Kandy"));
            addDistrict(new district(12,"Kegalle"));
            addDistrict(new district(13,"Kilinochchi"));
            addDistrict(new district(14,"Kurunegala"));
            addDistrict(new district(15,"Mannar"));
            addDistrict(new district(16,"Matale"));
            addDistrict(new district(17,"Matara"));
            addDistrict(new district(18,"Monaragala"));
            addDistrict(new district(19,"Mullaitivu"));
            addDistrict(new district(20,"Nuwara Eliya"));
            addDistrict(new district(21,"Polonnaruwa"));
            addDistrict(new district(22,"Puttalam"));
            addDistrict(new district(23,"Ratnapura"));
            addDistrict(new district(24,"Trincomalee"));
            addDistrict(new district(25,"Vavuniya"));
        }
    }
}
