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
import com.a14roxgmail.prasanna.healthcareapp.ListView.adapters.adapter_statistics;
import com.a14roxgmail.prasanna.healthcareapp.Models.mreports;
import com.a14roxgmail.prasanna.healthcareapp.Models.statistics;
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
 * Created by Prasanna Deshappriya on 12/1/2016.
 */
public class home_fragment extends Fragment {
    private List<statistics> lstStatistics;
    private adapter_statistics adapter;
    private ListView lvStat;
    private TextView lnkRefresh;
    private String nic;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false );
        init(view);
        search_from_server();
        lnkRefresh.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        search_from_server();
                    }
                }
        );
        return view;
    }

    public void setNic(String nic){
        this.nic = nic.toUpperCase().toString();
    }

    public void init(View view){
        lstStatistics = new ArrayList<>();
        lvStat = (ListView) view.findViewById(R.id.lstMHome);
        lnkRefresh = (TextView)view.findViewById(R.id.lnkRefresh);
    }

    public void search_from_server(){
        final server_request request = new server_request(getActivity());
        HashMap<String,String> arr = new HashMap<>();
        Log.i(constants.TAG, "Nic :- " + nic);
        arr.put("token", tokenAuth.getTokenNumber(getContext(),nic));
        request.sendGetRequest(arr,constants.server_home_url);
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
        lstStatistics.clear();
        try {
            JSONArray itemArray = new JSONArray(request.getResponse());
            for(int i=0; i<itemArray.length(); i++){
                JSONObject objResponse = new JSONObject(itemArray.get(i).toString());
                lstStatistics.add(new statistics(
                        (1+i)
                        ,objResponse.getString("district_id")
                        ,objResponse.getString("description")
                        ,objResponse.getString("count")
                        ,objResponse.getString("symptoms")
                        ,objResponse.getString("treatment")
                        ,objResponse.getString("disease_name")));
            }
            request = null;
            lvStat.setAdapter(null);
            adapter = new adapter_statistics(lstStatistics,getContext());
            lvStat.setAdapter(adapter);

        } catch (JSONException e) {
            Log.i(constants.TAG,"JasonError :- " + e.toString());
            e.printStackTrace();
        }
    }
}
