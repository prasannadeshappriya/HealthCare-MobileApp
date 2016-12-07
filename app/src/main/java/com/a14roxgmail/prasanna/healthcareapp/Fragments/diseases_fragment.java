package com.a14roxgmail.prasanna.healthcareapp.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.a14roxgmail.prasanna.healthcareapp.ListView.adapters.adapter_disease;
import com.a14roxgmail.prasanna.healthcareapp.ListView.disease;
import com.a14roxgmail.prasanna.healthcareapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prasanna Deshappriya on 12/1/2016.
 */
public class diseases_fragment extends Fragment {
    private List<disease> lstDisease;
    private adapter_disease adapter;
    private ListView lvDisease;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diseases,container,false);
        init(view);
        add_list_details(view);
        return view;
    }

    private void add_list_details(View view) {
        lstDisease.add(new disease("Dengue"
                ,1
                ,"Patient need to admit to the nearest hospital"
                ,"Not available"));
        lstDisease.add(new disease("HIV"
                ,2
                ,"Patient need to admit to the nearest hospital"
                ,"Not available"));
        lstDisease.add(new disease("Chiken"
                ,1
                ,"Patient need to admit to the nearest hospital"
                ,"Not available"));
        lstDisease.add(new disease("Cancer"
                ,1
                ,"Patient will die soon"
                ,"Blah Blah"));
        lstDisease.add(new disease("Dengue"
                ,1
                ,"Patient need to admit to the nearest hospital"
                ,"Not available"));
        lstDisease.add(new disease("Dengue"
                ,1
                ,"Patient need to admit to the nearest hospital"
                ,"Not available"));
        lstDisease.add(new disease("Dengue"
                ,1
                ,"Patient need to admit to the nearest hospital"
                ,"Not available"));
        lstDisease.add(new disease("Dengue"
                ,1
                ,"Patient need to admit to the nearest hospital"
                ,"Not available"));
        lstDisease.add(new disease("Dengue"
                ,1
                ,"Patient need to admit to the nearest hospital"
                ,"Not available"));
        lstDisease.add(new disease("Dengue"
                ,1
                ,"Patient need to admit to the nearest hospital"
                ,"Not available"));
        adapter = new adapter_disease(getContext(),lstDisease);
        lvDisease.setAdapter(adapter);
    }

    private void init(View view) {
        lvDisease = (ListView)view.findViewById(R.id.lstDisease);
        lstDisease = new ArrayList<disease>();
    }
}
