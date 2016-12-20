package com.a14roxgmail.prasanna.healthcareapp.Fragments;

import android.app.ProgressDialog;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.a14roxgmail.prasanna.healthcareapp.DAO.diseaseDAO;
import com.a14roxgmail.prasanna.healthcareapp.Database.database;
import com.a14roxgmail.prasanna.healthcareapp.ListView.adapters.adapter_disease;
import com.a14roxgmail.prasanna.healthcareapp.Models.disease;
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
public class diseases_fragment extends Fragment  {
    private List<disease> lstDisease;
    private adapter_disease adapter;
    private TextView lnkAddDsease;
    private modify_data_fragment mdf;
    private ListView lvDisease;
    private EditText etSearch;
    private diseaseDAO disease_dao;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diseases,container,false);
        init(view);
        add_list_details("init");
        lnkAddDsease.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(lnkAddDsease.getText().equals("Add diseases")) {
                            lnkAddDsease.setText("Cancel");
                            etSearch.setEnabled(false);
                            show_modify_data_fragment();
                        }else{
                            lnkAddDsease.setText("Add diseases");
                            FragmentTransaction trans = getFragmentManager().beginTransaction();
                            trans.remove(mdf);
                            trans.commit();
                            lvDisease.setVisibility(View.VISIBLE);
                            etSearch.setEnabled(true);
                        }
                    }
                }
        );
        final diseases_fragment frag = this;
        lvDisease.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        disease d = (disease)adapterView.getItemAtPosition(i);
                        Log.i(constants.TAG,"this should be the index :- " + i + "    " + l);
                        Log.i(constants.TAG, d.getName());
                        if(lnkAddDsease.getText().equals("Add diseases")) {
                            lnkAddDsease.setText("Cancel");
                            etSearch.setEnabled(false);
                            //open modify fragment (replacing the list context)
                            mdf = new modify_data_fragment();
                            mdf.setFields("Disease","Description","Treatment","Update","disease");
                            mdf.initClassObject("disease_fragment",frag);
                            mdf.setFieldValues(d.getName(),d.getDescription(),d.getTreatment());
                            FragmentTransaction trans = getFragmentManager().beginTransaction();
                            trans.replace(R.id.frmDiseaseModifyDialog,mdf);
                            trans.commit();
                            lvDisease.setVisibility(View.INVISIBLE);
                        }else{
                            lnkAddDsease.setText("Add diseases");
                            FragmentTransaction trans = getFragmentManager().beginTransaction();
                            trans.remove(mdf);
                            trans.commit();
                            lvDisease.setVisibility(View.VISIBLE);
                            etSearch.setEnabled(true);
                        }
                        return true;
                    }
                }
        );
        etSearch.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        add_list_details("filter");
                        search_from_server();
                    }
                    @Override
                    public void afterTextChanged(Editable editable) {}
                }
        );
        return view;

    }

    private void show_modify_data_fragment() {
        mdf = new modify_data_fragment();
        mdf.setFields("Disease","Description","Treatment","Insert","disease");
        diseases_fragment d = this;
        mdf.initClassObject("disease_fragment",d);
        FragmentTransaction trans = getFragmentManager().beginTransaction();
        trans.attach(d);
        trans.replace(R.id.frmDiseaseModifyDialog,mdf);
        trans.commit();
        lvDisease.setVisibility(View.INVISIBLE);
    }

    public void reset_list(){
        lvDisease.setVisibility(View.VISIBLE);
        lnkAddDsease.setText("Add diseases");
        etSearch.setEnabled(true);
        add_list_details("init");
    }

    private void add_list_details(String method) {
        List<disease> arr;
        if (method.equals("init")) {
            arr = disease_dao.getDiseaseList();

        }else{
            arr = disease_dao.filter(etSearch.getText().toString());
        }
        lstDisease.clear();
        for(int count=0; count<arr.size();count++){
            lstDisease.add(new disease(
                    (count+1)
                    ,arr.get(count).getName()
                    ,arr.get(count).getDescription()
                    ,arr.get(count).getTreatment()));
        }
        if (method.equals("init")) {
            if (sync_service.isNetworkAvailable(getContext(),constants.Internet_Array)) {
                search_from_server();
            }else{
                adapter = new adapter_disease(getContext(),lstDisease);
                lvDisease.setAdapter(adapter);
            }

        }else{

        }

    }

    private void init(View view) {
        lvDisease = (ListView)view.findViewById(R.id.lstDisease);
        lstDisease = new ArrayList<disease>();
        lnkAddDsease = (TextView)view.findViewById(R.id.lnkAddDisease);
        etSearch = (EditText)view.findViewById(R.id.etSearchDisease);
        database sqldb = new database(getContext(),constants.database_name,null,1);
        disease_dao = new diseaseDAO(getContext(),sqldb.getDatabase());
    }


    public void search_from_server(){
        final server_request request = new server_request(getActivity());
        HashMap<String,String> arr = new HashMap<String,String>();
        arr.put("name",etSearch.getText().toString());
        arr.put("token", token.fake_token);
        request.sendGetRequest(arr);
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
            int count = lstDisease.size();
            for(int i=0; i<itemArray.length(); i++){
                JSONObject objResponse = new JSONObject(itemArray.get(i).toString());
                lstDisease.add(new disease(
                        (count+1+i)
                        ,objResponse.getString("name")
                        ,objResponse.getString("description")
                        ,objResponse.getString("treatment")));
            }
            adapter = new adapter_disease(getContext(),lstDisease);
            lvDisease.setAdapter(adapter);

        } catch (JSONException e) {
            Log.i(constants.TAG,"JasonError :- " + e.toString());
            e.printStackTrace();
        }
    }
}
