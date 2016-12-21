package com.a14roxgmail.prasanna.healthcareapp.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
 * Created by Prasanna Deshappriya on 12/21/2016.
 */
public class mreport_insert_fragment extends Fragment {
    private AutoCompleteTextView tvDiseaseSearch;
    private AutoCompleteTextView tvNameSearch;
    private String resent_email;
    private List<String> lstDisease;
    private EditText etNic;
    private ArrayList<String> arrNic;
    private ArrayList<String> arrDisease;
    private String nic;
    private String disease_id;
    private Button btnCreate;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mreports_insert,container,false);
        init(view);

        tvNameSearch.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        etNic.setText(arrNic.get(i).toString());
                    }
                }
        );

        tvDiseaseSearch.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        disease_id = arrDisease.get(i).toString();
                    }
                }
        );

        tvNameSearch.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        search_from_server(constants.server_patient_search_medical_report_insert_url,"patient");
                    }
                    @Override
                    public void afterTextChanged(Editable editable) {}
                }
        );

        tvDiseaseSearch.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        search_from_server(constants.server_disease_search_url,"disease");
                    }
                    @Override
                    public void afterTextChanged(Editable editable) {}
                }
        );

        btnCreate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sendUpdateRequest();
                    }
                }
        );

        return view;
    }

    public void setNic(String nic){
        this.nic = nic.toUpperCase();
    }

    private void init(View view) {
        tvDiseaseSearch = (AutoCompleteTextView) view.findViewById(R.id.mreport_search_disease);
        tvNameSearch = (AutoCompleteTextView) view.findViewById(R.id.mreport_search_name);
        tvDiseaseSearch.setAdapter(null);
        tvNameSearch.setAdapter(null);
        lstDisease = new ArrayList<String>();
        arrNic = new ArrayList<>();
        etNic =(EditText)view.findViewById(R.id.mreport_nic);
        arrDisease = new ArrayList<>();
        btnCreate = (Button) view.findViewById(R.id.btnCreate);
    }



    public  void response_data_process(server_request request,String role){
        Log.i(constants.TAG,"Final Respond :- " + request.getResponse());
        lstDisease.clear();
        arrDisease.clear();
        arrNic.clear();
        try {
            JSONArray itemArray = new JSONArray(request.getResponse());
            for(int i=0; i<itemArray.length(); i++){
                JSONObject objResponse = new JSONObject(itemArray.get(i).toString());
                lstDisease.add(objResponse.getString("name"));
                if(role.equals("patient")){
                    arrNic.add(objResponse.getString("nic"));

                }else if(role.equals("disease")){
                    arrDisease.add(objResponse.getString("id"));
                }
            }
            request = null;
            if(role.equals("disease")) {
                tvDiseaseSearch.setAdapter(null);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, lstDisease);
                tvDiseaseSearch.setAdapter(adapter);
            }else if(role.equals("patient")){
                tvNameSearch.setAdapter(null);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, lstDisease);
                tvNameSearch.setAdapter(adapter);
            }

        } catch (JSONException e) {
            Log.i(constants.TAG,"JasonError :- " + e.toString());
            e.printStackTrace();
        }
    }

    public void search_from_server(String url, String role){
        final server_request request = new server_request(getActivity());
        final String method = role;
        HashMap<String,String> arr = new HashMap<String,String>();
        if(role.equals("patient")){
            arr.put("name", tvNameSearch.getText().toString());
        }else if(role.equals("disease")){
            arr.put("name", tvDiseaseSearch.getText().toString());
        }
        arr.put("token", tokenAuth.getTokenNumber(getContext(),nic));

        request.sendGetRequest(arr, url);
        CountDownTimer timer = new CountDownTimer(300, 100) {
            @Override
            public void onFinish() {
                response_data_process(request,method);
                Log.i(constants.TAG, "OnFinish method triggered");
            }

            @Override
            public void onTick(long millisLeft) {}
        };
        timer.start();
    }

    private void sendUpdateRequest(){
        final server_request request = new server_request(2,getActivity());
        request.set_server_url(constants.server_update_medical_report);
        request.setParams(tokenAuth.getTokenNumber(getContext(),nic),"token");
        request.setParams(etNic.getText().toString(),"patient_nic");
        request.setParams(disease_id,"disease_id");

        try {
            String req = request.sendPostRequest();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final ProgressDialog pd = new ProgressDialog(getContext(), R.style.AppTheme_Dark_Dialog);
        pd.setIndeterminate(true);
        pd.setMessage("Authenticating..");
        pd.show();

        CountDownTimer timer = new CountDownTimer(2000, 1000) {
            @Override
            public void onFinish() {response_process(request); pd.dismiss();}

            @Override
            public void onTick(long millisLeft) {
            }
        };
        timer.start();
    }

    private void response_process(server_request request) {
        String response = request.getResponse();
        Log.i(constants.TAG,"Response from update request :- " + response);
        if(response.equals("")) {
            Toast.makeText(getContext(), "Server timeout", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getContext(), "Medical report created", Toast.LENGTH_LONG).show();
            mreports_fragment frag = new mreports_fragment();
            frag.setNic(nic);
            FragmentTransaction  fragTrans = getFragmentManager().beginTransaction();
            fragTrans.replace(R.id.frmMain,frag);
            fragTrans.commit();
        }

    }
}







