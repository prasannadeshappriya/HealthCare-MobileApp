package com.a14roxgmail.prasanna.healthcareapp.Fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

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
    private AutoCompleteTextView actv;
    private List<String> lstDisease;
    private String nic;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mreports_insert,container,false);
        init(view);

        actv.addTextChangedListener(
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
        this.nic = nic.toUpperCase();
    }

    private void init(View view) {
        actv = (AutoCompleteTextView) view.findViewById(R.id.mreport_search);
        actv.setAdapter(null);
        lstDisease = new ArrayList<String>();
    }

    public  void response_data_process(server_request request){
        Log.i(constants.TAG,"Final Respond :- " + request.getResponse());
        lstDisease.clear();
        try {
            JSONArray itemArray = new JSONArray(request.getResponse());
            for(int i=0; i<itemArray.length(); i++){
                JSONObject objResponse = new JSONObject(itemArray.get(i).toString());
                lstDisease.add(objResponse.getString("name"));
            }
            request = null;
            actv.setAdapter(null);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,lstDisease);
            actv.setAdapter(adapter);

        } catch (JSONException e) {
            Log.i(constants.TAG,"JasonError :- " + e.toString());
            e.printStackTrace();
        }
    }

    public void search_from_server(){
        final server_request request = new server_request(getActivity());
        HashMap<String,String> arr = new HashMap<String,String>();
        arr.put("name", actv.getText().toString());
        arr.put("token", tokenAuth.getTokenNumber(getContext(),nic));

        request.sendGetRequest(arr, constants.server_disease_search_url);
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
}







