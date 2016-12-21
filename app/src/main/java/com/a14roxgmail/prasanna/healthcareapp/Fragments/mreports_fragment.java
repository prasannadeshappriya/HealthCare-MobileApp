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

import com.a14roxgmail.prasanna.healthcareapp.DAO.userDAO;
import com.a14roxgmail.prasanna.healthcareapp.Database.database;
import com.a14roxgmail.prasanna.healthcareapp.ListView.adapters.adapter_mreport;
import com.a14roxgmail.prasanna.healthcareapp.Models.mreports;
import com.a14roxgmail.prasanna.healthcareapp.Models.user;
import com.a14roxgmail.prasanna.healthcareapp.R;
import com.a14roxgmail.prasanna.healthcareapp.constants;
import com.a14roxgmail.prasanna.healthcareapp.server_request;
import com.a14roxgmail.prasanna.healthcareapp.tokenAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Prasanna Deshappriya on 12/16/2016.
 */
public class mreports_fragment extends Fragment {
    private TextView lnkAddMReport;
    private List<mreports> lstMReports;
    private adapter_mreport adapter;
    private ListView lvMReports;
    private EditText etSearch;
    private String nic;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mreports,container,false);
        init(view);

        search_from_server();
        if(getRole().equals("patient")){
            lnkAddMReport.setVisibility(View.INVISIBLE);
        }else {
            lnkAddMReport.setVisibility(View.VISIBLE);
        }
        lnkAddMReport.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mreport_insert_fragment report_insert = new mreport_insert_fragment();
                        report_insert.setNic(nic);
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frmMain,report_insert);
                        fragmentTransaction.commit();
                    }
                }
        );

        etSearch.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        search_from_server();
                    }
                    @Override
                    public void afterTextChanged(Editable editable) {}
                }
        );
        return view;
    }

    public void setNic(String nic){
        this.nic = nic.toUpperCase().toString();
    }

    public void init(View view){
        lstMReports = new ArrayList<>();
        lvMReports = (ListView) view.findViewById(R.id.lstMReport);
        lnkAddMReport = (TextView)view.findViewById(R.id.lnkAddMReport);
        etSearch = (EditText) view.findViewById(R.id.etSearchMReport);
    }

    public void search_from_server(){
        final server_request request = new server_request(getActivity());
        HashMap<String,String> arr = new HashMap<>();
        Log.i(constants.TAG, "Nic :- " + nic);
        arr.put("token", tokenAuth.getTokenNumber(getContext(),nic));
        arr.put("name", etSearch.getText().toString());
        if (getRole().equals("patient")){
            request.sendGetRequest(arr,constants.server_patient_search_medical_report_url);
        }else {
            request.sendGetRequest(arr,constants.server_medical_officer_search_medical_report_url);
        }
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
        lstMReports.clear();
        try {
            JSONArray itemArray = new JSONArray(request.getResponse());
            for(int i=0; i<itemArray.length(); i++){
                JSONObject objResponse = new JSONObject(itemArray.get(i).toString());
                lstMReports.add(new mreports(
                        (1+i)
                        ,objResponse.getString("patient_name")
                        ,objResponse.getString("disease_name")
                        ,objResponse.getString("prescription")
                        ,objResponse.getString("comments")));
            }
            request = null;
            lvMReports.setAdapter(null);
            adapter = new adapter_mreport(getContext(),lstMReports);
            lvMReports.setAdapter(adapter);

        } catch (JSONException e) {
            Log.i(constants.TAG,"JasonError :- " + e.toString());
            e.printStackTrace();
        }
    }

    public String getRole(){
        database sqldb = new database(getContext(),constants.database_name,null,1);
        userDAO user_dao = new userDAO(getContext(),sqldb.getDatabase());
        user user = user_dao.getUser(nic.toUpperCase().toString());
        return user.getRole();
    }
}
