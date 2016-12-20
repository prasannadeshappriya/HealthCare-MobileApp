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

import com.a14roxgmail.prasanna.healthcareapp.ListView.adapters.adapter_patient;
import com.a14roxgmail.prasanna.healthcareapp.Models.patient;
import com.a14roxgmail.prasanna.healthcareapp.R;
import com.a14roxgmail.prasanna.healthcareapp.constants;

import java.util.ArrayList;
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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patients,container,false);
        init(view);
        add_itms_to_lst(view);

        lnkAddPatient.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(lnkAddPatient.getText().equals("Add patient")) {
                            lnkAddPatient.setText("Cancel");
                            etSearch.setEnabled(false);
                            show_modify_data_fragment();
                        }else{
                            lnkAddPatient.setText("Add patient");
                            FragmentTransaction trans = getFragmentManager().beginTransaction();
                            trans.remove(mdf);
                            trans.commit();
                            lstPatient.setVisibility(View.VISIBLE);
                            etSearch.setEnabled(true);
                        }
                    }
                }
        );
        lstPatient.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        on_long_press_listview(adapterView,view,i,l);
                        return true;
                    }
                }
        );
        return view;
    }

    private void on_long_press_listview(AdapterView<?> adapterView, View view, int i, long l) {
        patient p = (patient) adapterView.getItemAtPosition(i);
        Log.i(constants.TAG,"this should be the index :- " + i + "    " + l);
        Log.i(constants.TAG, p.getName());
        if(lnkAddPatient.getText().equals("Add patient")) {
            lnkAddPatient.setText("Cancel");
            etSearch.setEnabled(false);
            //open modify fragment (replacing the list context)
            mdf = new modify_data_fragment();
            mdf.setFields("Name","Nic","City","Update","patient");
            mdf.setFieldValues(p.getName(),p.getNic(),p.getDistrict_id());
            FragmentTransaction trans = getFragmentManager().beginTransaction();
            trans.replace(R.id.frmPatientModifyDialog,mdf);
            trans.commit();
            lstPatient.setVisibility(View.INVISIBLE);
        }else{
            lnkAddPatient.setText("Add patient");
            FragmentTransaction trans = getFragmentManager().beginTransaction();
            trans.remove(mdf);
            trans.commit();
            lstPatient.setVisibility(View.VISIBLE);
            etSearch.setEnabled(true);
        }
    }

    private void show_modify_data_fragment() {
        mdf = new modify_data_fragment();
        mdf.setFields("Name","Nic","City","Insert","patient");
        FragmentTransaction trans = getFragmentManager().beginTransaction();
        trans.replace(R.id.frmPatientModifyDialog,mdf);
        trans.commit();
        lstPatient.setVisibility(View.INVISIBLE);
    }

    private void add_itms_to_lst(View view) {
        patientList.add(new patient("Ja-Ela","1","Prasanna Deshappriya", "942222467V"));
        patientList.add(new patient("Kandana","1","Hiruni Kalanika", "947760203V"));
        patientList.add(new patient("Kandy","1","Malaka Sandaruwan", "942411625V"));
        patientList.add(new patient("Jeffna","1","Promodh Sudharaka", "942265485V"));
        patientList.add(new patient("Mount-Lavinia","1","Asela Bandara", "9428752467V"));
        patientList.add(new patient("Galle","1","Sandaru Perera", "654822467V"));
        patientList.add(new patient("Polonnaruwa","1","Shammika Deshappriya", "942222467V"));
        patientList.add(new patient("Kaluthara","1","Kevin Charles", "845562467V"));
        patientList.add(new patient("Colombo","1","Ruwan Hettiarachchi", "745855467V"));
        adapter = new adapter_patient(getContext(),patientList);
        lstPatient.setAdapter(adapter);
    }

    public void init(View view){
        patientList = new ArrayList<>();
        lstPatient = (ListView) view.findViewById(R.id.lstPatient);
        lnkAddPatient = (TextView)view.findViewById(R.id.lnkAddPatient);
        etSearch = (EditText) view.findViewById(R.id.etSearchPatient);
    }
}

