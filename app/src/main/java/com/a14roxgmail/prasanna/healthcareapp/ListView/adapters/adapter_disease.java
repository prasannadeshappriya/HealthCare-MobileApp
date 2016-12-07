package com.a14roxgmail.prasanna.healthcareapp.ListView.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.a14roxgmail.prasanna.healthcareapp.ListView.disease;
import com.a14roxgmail.prasanna.healthcareapp.R;

import java.util.List;

/**
 * Created by Prasanna Deshappriya on 12/7/2016.
 */
public class adapter_disease extends BaseAdapter {
    private List<disease> arrDisease;
    private Context context;

    public adapter_disease(Context context, List<disease> arrDisease) {
        this.context = context;
        this.arrDisease = arrDisease;
    }

    @Override
    public int getCount() {
        return arrDisease.size() ;
    }

    @Override
    public Object getItem(int i) {
        return arrDisease.get(i);
    }

    @Override
    public long getItemId(int i) {
        return arrDisease.get(i).getID();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.adapter_lst_disease,null );
        TextView tvName = (TextView)v.findViewById(R.id.tvAdapterName);
        TextView tvDiscription = (TextView)v.findViewById(R.id.tvAdapterDescription);
        TextView tvTreatement = (TextView)v.findViewById(R.id.tvAdapterTreatment);
        tvName.setText(arrDisease.get(i).getDisease());
        tvDiscription.setText("Description: - " + arrDisease.get(i).getDescription());
        tvTreatement.setText("Treatment: - " + arrDisease.get(i).getTreatment());
        v.setTag(arrDisease.get(i).getID());
        return v;
    }
}
