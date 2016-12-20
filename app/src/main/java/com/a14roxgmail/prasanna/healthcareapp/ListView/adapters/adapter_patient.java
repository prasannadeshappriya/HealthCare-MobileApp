package com.a14roxgmail.prasanna.healthcareapp.ListView.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.a14roxgmail.prasanna.healthcareapp.Models.patient;
import com.a14roxgmail.prasanna.healthcareapp.R;

import java.util.List;

/**
 * Created by Prasanna Deshappriya on 12/1/2016.
 */
public class adapter_patient extends BaseAdapter {
    private List<patient> patient_list;
    private Context context;

    public adapter_patient(Context context, List<patient> patient_list) {
        this.context = context;
        this.patient_list = patient_list;
    }

    @Override
    public int getCount() {
        return patient_list.size();
    }

    @Override
    public Object getItem(int i) {
        return patient_list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return patient_list.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.adapter_lst_patient,null );

        TextView patient_name = (TextView) v.findViewById(R.id.tvAdapterName);
        TextView patient_NIC = (TextView) v.findViewById(R.id.tvAdapterNIC);
        TextView patient_role = (TextView) v.findViewById(R.id.tvAdapterRole);
        TextView patient_dob = (TextView) v.findViewById(R.id.tvAdapterDob);
        TextView patient_district = (TextView) v.findViewById(R.id.tvAdapterDistrict);

        patient_name.setText(patient_list.get(i).getName());
        patient_NIC.setText("NIC:  " + patient_list.get(i).getNic());
        patient_role.setText("Role:  " + "Available soon");
        patient_dob.setText("Date of Birth:  " + patient_list.get(i).getDate_of_birth());
        patient_district.setText("District:  " + patient_list.get(i).getDistrict_id());

        v.setTag(patient_list.get(i).getId());
        return v;
    }
}
