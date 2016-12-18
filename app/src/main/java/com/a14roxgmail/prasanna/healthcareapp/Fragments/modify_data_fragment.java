package com.a14roxgmail.prasanna.healthcareapp.Fragments;


import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.a14roxgmail.prasanna.healthcareapp.DAO.diseaseDAO;
import com.a14roxgmail.prasanna.healthcareapp.Database.database;
import com.a14roxgmail.prasanna.healthcareapp.Models.disease;
import com.a14roxgmail.prasanna.healthcareapp.R;
import com.a14roxgmail.prasanna.healthcareapp.constants;

/**
 * Created by Prasanna Deshappriya on 12/16/2016.
 */
public class modify_data_fragment extends Fragment {
    private String field1;
    private String field2;
    private String field3;
    private String field4;
    private String modal_name;
    private String type;

    private EditText etField1;
    private EditText etField2;
    private EditText etField3;
    private Button btnField4;

    private String field1_value;
    private String field2_value;
    private String field3_value;

    database sqlite_db;
    boolean setText = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_modify_data,container,false);
        init(v);
        btnField4.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(varify()){
                            start_process();
                        }
                    }
                }
        );
        return v;
    }

    public boolean varify(){
        return true;
    }

    private void start_process() {
        sqlite_db = new database(getContext(),constants.database_name,null,1);
        if(type.equals("Insert")){
            diseaseDAO disease_dao = new diseaseDAO(getContext(),sqlite_db.getDatabase());
            disease_dao.addDisease(new disease(
                    etField1.getText().toString(),
                    etField2.toString().toString(),
                    etField3.getText().toString(),
                    constants.OFFLINE_FLAG));
        }
    }

    private void init(View view) {
        etField1 = (EditText)view.findViewById(R.id.editField1);
        etField2 = (EditText)view.findViewById(R.id.editField2);
        etField3 = (EditText)view.findViewById(R.id.editField3);
        btnField4 = (Button)view.findViewById(R.id.btnField4);

        etField1.setHint(this.field1);
        etField2.setHint(this.field2);
        etField3.setHint(this.field3);
        btnField4.setText(this.field4);

        if(setText){
            setText = false;
            Log.i(constants.TAG,"Triggered, Values :- " + field1_value + ", " + field2_value + ", " + field3_value);
            etField1.setText(field1_value);
            etField2.setText(field2_value);
            etField3.setText(field3_value);
        }
    }

    public void setFieldValues(String field1, String field2,String field3){
        this.field1_value = field1;
        this.field2_value = field2;
        this.field3_value = field3;
        setText = true;

    }

    public void setFields(String field1, String field2,String field3,
                          String field4, String model_name){
        this.field1 = field1; this.field2 = field2;
        this.field3 = field3; this.field4 = field4;
        this.type = field4; this.modal_name = model_name;
    }
}
