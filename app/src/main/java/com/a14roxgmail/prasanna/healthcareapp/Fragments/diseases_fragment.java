package com.a14roxgmail.prasanna.healthcareapp.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.a14roxgmail.prasanna.healthcareapp.ListView.adapters.adapter_disease;
import com.a14roxgmail.prasanna.healthcareapp.Models.disease;
import com.a14roxgmail.prasanna.healthcareapp.R;
import com.a14roxgmail.prasanna.healthcareapp.constants;

import java.util.ArrayList;
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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diseases,container,false);
        init(view);
        add_list_details(view);
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
        return view;
    }

    private void show_modify_data_fragment() {
        mdf = new modify_data_fragment();
        mdf.setFields("Disease","Description","Treatment","Insert","disease");
        FragmentTransaction trans = getFragmentManager().beginTransaction();
        trans.replace(R.id.frmDiseaseModifyDialog,mdf);
        trans.commit();
        lvDisease.setVisibility(View.INVISIBLE);
    }

    private void add_list_details(View view) {
        lstDisease.add(new disease(1
                ,"Dengue"
                ,"Patient need to admit to the nearest hospital"
                ,"Not available"));
        lstDisease.add(new disease(2
                ,"HIV"
                ,"Patient need to admit to the nearest hospital"
                ,"Not available"));
        lstDisease.add(new disease(3
                ,"Chiken"
                ,"Patient need to admit to the nearest hospital"
                ,"Not available"));
        lstDisease.add(new disease(4
                ,"Cancer"
                ,"Patient will die soon"
                ,"Blah Blah"));
        lstDisease.add(new disease(5
                ,"Dengue"
                ,"Patient need to admit to the nearest hospital"
                ,"Not available"));
        lstDisease.add(new disease(6
                ,"Dengue"
                ,"Patient need to admit to the nearest hospital"
                ,"Not available"));
        lstDisease.add(new disease(7
                ,"Dengue"
                ,"Patient need to admit to the nearest hospital"
                ,"Not available"));
        lstDisease.add(new disease(8
                ,"Dengue"
                ,"Patient need to admit to the nearest hospital"
                ,"Not available"));
        lstDisease.add(new disease(9
                ,"Dengue"
                ,"Patient need to admit to the nearest hospital"
                ,"Not available"));
        lstDisease.add(new disease(10
                ,"Dengue"
                ,"Patient need to admit to the nearest hospital"
                ,"Not available"));
        adapter = new adapter_disease(getContext(),lstDisease);
        lvDisease.setAdapter(adapter);
    }

    private void init(View view) {
        lvDisease = (ListView)view.findViewById(R.id.lstDisease);
        lstDisease = new ArrayList<disease>();
        lnkAddDsease = (TextView)view.findViewById(R.id.lnkAddDisease);
        etSearch = (EditText)view.findViewById(R.id.etSearchDisease);
    }
}
