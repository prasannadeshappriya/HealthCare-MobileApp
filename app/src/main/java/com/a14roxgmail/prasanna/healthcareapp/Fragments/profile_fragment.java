package com.a14roxgmail.prasanna.healthcareapp.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.a14roxgmail.prasanna.healthcareapp.R;

/**
 * Created by Prasanna Deshappriya on 12/7/2016.
 */
public class profile_fragment extends Fragment {
    private EditText etName;
    private EditText etAddress;
    private EditText etEmail;
    private EditText etphone;
    private Button btnBack;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false );
        init(view);
        etName.setEnabled(false);
        etAddress.setEnabled(false);
        etEmail.setEnabled(false);
        etphone.setEnabled(false);
        btnBack.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        back_btn_click(view);
                    }
                }
        );
        return view;
    }

    public void back_btn_click(View view){
        Toast.makeText(getContext(),String.valueOf(etName.isEnabled()),Toast.LENGTH_LONG).show();
        if(!etName.isEnabled()){
            etName.setEnabled(true);
            etAddress.setEnabled(true);
            etphone.setEnabled(true);
            btnBack.setText("Update");
        }else{
            if(verify()){
                etName.setEnabled(false);
                etAddress.setEnabled(false);
                etphone.setEnabled(false);
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
        btnBack = (Button) view.findViewById(R.id.profileBtnEdit);
        etName = (EditText)view.findViewById(R.id.profile_etName);
        etAddress =(EditText)view.findViewById(R.id.profile_etAddress);
        etEmail = (EditText) view.findViewById(R.id.profile_etEmail);
        etphone = (EditText)view.findViewById(R.id.profile_etPhone);
    }
}
