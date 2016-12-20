package com.a14roxgmail.prasanna.healthcareapp.Fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.a14roxgmail.prasanna.healthcareapp.DAO.patientDAO;
import com.a14roxgmail.prasanna.healthcareapp.Database.database;
import com.a14roxgmail.prasanna.healthcareapp.ListView.adapters.adapter_patient;
import com.a14roxgmail.prasanna.healthcareapp.Models.patient;
import com.a14roxgmail.prasanna.healthcareapp.R;
import com.a14roxgmail.prasanna.healthcareapp.Services.sync_service;
import com.a14roxgmail.prasanna.healthcareapp.constants;
import com.a14roxgmail.prasanna.healthcareapp.server_request;
import com.a14roxgmail.prasanna.healthcareapp.token;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Prasanna Deshappriya on 12/1/2016.
 */
public class patients_fragment extends Fragment {
    private adapter_patient adapter;
    private List<patient> patientList;
    private ListView lstPatient;
    private EditText etSearch;
    private modify_data_fragment mdf;
    private TextView lnkAddPatient;
    private patientDAO patient_dao;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patients,container,false);
        init(view);
        add_list_details("init");
        etSearch.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        add_list_details("filter");
                    }
                    @Override
                    public void afterTextChanged(Editable editable) {}
                }
        );
        return view;
    }

    public void init(View view){
        patientList = new ArrayList<>();
        lstPatient = (ListView) view.findViewById(R.id.lstPatient);
        lnkAddPatient = (TextView)view.findViewById(R.id.lnkAddPatient);
        etSearch = (EditText) view.findViewById(R.id.etSearchPatient);
        database sqldb = new database(getContext(),constants.database_name,null,1);
        patient_dao = new patientDAO(getContext(),sqldb.getDatabase());
    }

    public void search_from_server(){
        final server_request request = new server_request(getActivity());
        HashMap<String,String> arr = new HashMap<String,String>();
        arr.put("nic",etSearch.getText().toString());
        arr.put("token", token.fake_token);
        request.sendGetRequest(arr,constants.server_patient_search_url);
        CountDownTimer timer = new CountDownTimer(300, 100) {
            @Override
            public void onFinish() {
                response_data_process(request);
                Log.i(constants.TAG, "OnFinish method triggered");
            }

            @Override
            public void onTick(long millisLeft) {}
        };
        timer.start();
    }

    public  void response_data_process(server_request request){
        Log.i(constants.TAG,"Final Respond :- " + request.getResponse());
        try {
            JSONArray itemArray = new JSONArray(request.getResponse());
            int count = patientList.size();
            for(int i=0; i<itemArray.length(); i++){
                JSONObject objResponse = new JSONObject(itemArray.get(i).toString());
                patientList.add(new patient(
                        (count+1+i)
                        ,objResponse.getString("name")
                        ,objResponse.getString("nic")
                        ,objResponse.getString("dob")
                        ,objResponse.getString("district_id")));
            }
            request = null;
            adapter = new adapter_patient(getContext(),patientList);
            lstPatient.setAdapter(adapter);

        } catch (JSONException e) {
            Log.i(constants.TAG,"JasonError :- " + e.toString());
            e.printStackTrace();
        }
    }

    private void add_list_details(String method) {
        List<patient> arr;
        patientList.clear();
        if (method.equals("init")) {
            arr = patient_dao.getPatientList();
        }else{
            arr = patient_dao.filter(etSearch.getText().toString());
        }
        for(int count=0; count<arr.size();count++) {
            patientList.add(new patient(
                    (count + 1),
                    arr.get(count).getName(),
                    arr.get(count).getNic(),
                    arr.get(count).getDate_of_birth(),
                    arr.get(count).getDistrict_id())
            );
        }
        if (method.equals("init")) {
            if (sync_service.isNetworkAvailable(getContext(), constants.Internet_Array)) {
                search_from_server();
            } else {
                adapter = new adapter_patient(getContext(), patientList);
                lstPatient.setAdapter(adapter);
            }
        }else{
            if (sync_service.isNetworkAvailable(getContext(), constants.Internet_Array)) {
                search_from_server();
            } else {
                adapter = new adapter_patient(getContext(), patientList);
                lstPatient.setAdapter(adapter);
            }
        }
    }
}

