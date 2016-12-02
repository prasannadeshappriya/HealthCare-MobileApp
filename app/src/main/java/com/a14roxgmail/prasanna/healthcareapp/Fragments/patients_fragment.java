package com.a14roxgmail.prasanna.healthcareapp.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.a14roxgmail.prasanna.healthcareapp.ListView.*;
import com.a14roxgmail.prasanna.healthcareapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prasanna Deshappriya on 12/1/2016.
 */
public class patients_fragment extends Fragment {
    patient_adapter adapter;
    List<patient> patientList;
    ListView lstPatient;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patients,container,false);
        init(view);
        add_itms_to_lst(view);
        return view;
    }

    private void add_itms_to_lst(View view) {
        patientList.add(new patient("Ja-Ela",1,"Prasanna Deshappriya", "942222467V"));
        patientList.add(new patient("Kandana",1,"Hiruni Kalanika", "947760203V"));
        patientList.add(new patient("Kandy",1,"Malaka Sandaruwan", "942411625V"));
        patientList.add(new patient("Jeffna",1,"Promodh Sudharaka", "942265485V"));
        patientList.add(new patient("Mount-Lavinia",1,"Asela Bandara", "9428752467V"));
        patientList.add(new patient("Galle",1,"Sandaru Perera", "654822467V"));
        patientList.add(new patient("Polonnaruwa",1,"Shammika Deshappriya", "942222467V"));
        patientList.add(new patient("Kaluthara",1,"Kevin Charles", "845562467V"));
        patientList.add(new patient("Colombo",1,"Ruwan Hettiarachchi", "745855467V"));
        adapter = new patient_adapter(getContext(),patientList);
        lstPatient.setAdapter(adapter);
    }

    public void init(View view){
        patientList = new ArrayList<>();
        lstPatient = (ListView) view.findViewById(R.id.lstPatient);
    }
}

