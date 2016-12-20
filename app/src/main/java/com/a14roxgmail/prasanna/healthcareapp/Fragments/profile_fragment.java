package com.a14roxgmail.prasanna.healthcareapp.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.a14roxgmail.prasanna.healthcareapp.DAO.patientDAO;
import com.a14roxgmail.prasanna.healthcareapp.DAO.userDAO;
import com.a14roxgmail.prasanna.healthcareapp.Database.database;
import com.a14roxgmail.prasanna.healthcareapp.Models.patient;
import com.a14roxgmail.prasanna.healthcareapp.Models.user;
import com.a14roxgmail.prasanna.healthcareapp.R;
import com.a14roxgmail.prasanna.healthcareapp.constants;

import java.util.List;

/**
 * Created by Prasanna Deshappriya on 12/7/2016.
 */
public class profile_fragment extends Fragment {
    private EditText etNic;
    private EditText etName;
    private Spinner lstDistrict;
    private EditText etDob;
    private EditText etRole;
    private Button btnBack;
    private patientDAO patient_dao;
    private userDAO user_dao;
    private database sqlite_db;

    private String nic;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        init(view);

        set_data();
        setEnable(false);
        btnBack.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btn_click(view);
                    }
                }
        );
        return view;
    }

    public void setLoginId(String nic){
        this.nic = nic;
    }

    private void set_data(){
        user user = user_dao.getUser(nic);
        if(user.getRole().toString().equals("patient")){
            patient patient = patient_dao.getPatient(nic);
            etNic.setText(nic);
            etName.setText(patient.getName());
            etRole.setText(user.getRole());
            etDob.setText(patient.getDate_of_birth());
            lstDistrict.setSelection(1);
        }
    }

    private void setEnable(boolean enable){
        etNic.setEnabled(false);
        etName.setEnabled(enable);
        lstDistrict.setEnabled(enable);
        etDob.setEnabled(enable);
        etRole.setEnabled(false);
    }

    public void btn_click(View view){
        if(!etName.isEnabled()){
            setEnable(true);
            btnBack.setText("Update");
        }else{
            if(verify()){
                setEnable(false);
                btnBack.setText("Edit");
                //update the details with the central cerver
            }else{
                Toast.makeText(getContext(),"Some fields contain invalid data",Toast.LENGTH_LONG).show();
            }
        }
    }

    public boolean verify(){
        return true;
    }

    private void init(View view) {
        etNic = (EditText) view.findViewById(R.id.profile_etNIC);
        etName = (EditText) view.findViewById(R.id.profile_etName);
        lstDistrict = (Spinner) view.findViewById(R.id.lstProfileDistrict);
        etDob = (EditText) view.findViewById(R.id.profile_etDateOfBirth);
        etRole = (EditText) view.findViewById(R.id.profile_etRole);
        btnBack = (Button) view.findViewById(R.id.profileBtnEdit);

        String[] items = new String[]{
                "Ampara", "Anuradhapura", "Badulla", "Batticaloa",
                "Colombo", "Galle", "Gampaha", "Hambantota",
                "Jaffna", "Kalutara", "Kandy", "Kegalle",
                "Kilinochchi", "Kurunegala", "Mannar", "Matale",
                "Matara", "Monaragala", "Mullaitivu", "Nuwara Eliya",
                "Polonnaruwa", "Puttalam", "Ratnapura", "Trincomalee",
                "Vavuniya"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        lstDistrict.setAdapter(adapter);

        sqlite_db = new database(getContext(), constants.database_name,null,1);
        patient_dao = new patientDAO(getContext(),sqlite_db.getDatabase());
        user_dao = new userDAO(getContext(),sqlite_db.getDatabase());
    }
}
