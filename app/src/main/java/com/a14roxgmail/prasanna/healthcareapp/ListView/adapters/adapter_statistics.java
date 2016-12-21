package com.a14roxgmail.prasanna.healthcareapp.ListView.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.a14roxgmail.prasanna.healthcareapp.Models.statistics;
import com.a14roxgmail.prasanna.healthcareapp.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Prasanna Deshappriya on 12/7/2016.
 */
public class adapter_statistics extends BaseAdapter {
    private List<statistics> arrStat;
    private Context contect;

    public adapter_statistics(List<statistics> arrStat, Context contect) {
        this.arrStat = arrStat;
        this.contect = contect;
    }

    @Override
    public int getCount() {
        return arrStat.size();
    }

    @Override
    public Object getItem(int i) {
        return arrStat.get(i);
    }

    @Override
    public long getItemId(int i) {
        return arrStat.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(contect, R.layout.adapter_lst_statistics,null);

        TextView tvName = (TextView) v.findViewById(R.id.tvAdapterHomeDistrictName);
        TextView tvDisease = (TextView) v.findViewById(R.id.tvAdapterHomeDisease);
        TextView tvDiscription = (TextView) v.findViewById(R.id.tvAdapterHomeDescription);
        TextView tvSymptoms = (TextView) v.findViewById(R.id.tvAdapterHomeSymptoms);
        TextView tvCount = (TextView) v.findViewById(R.id.tvAdapterHomeCount);
        TextView tvTreatment = (TextView) v.findViewById(R.id.tvAdapterHomeTreatment);

        tvName.setText(arrStat.get(i).getDistrict_name());
        tvDisease.setText("Disease: " + arrStat.get(i).getDisease());
        tvDiscription.setText("Description: " + arrStat.get(i).getDescription());
        tvSymptoms.setText("Symptoms: " + arrStat.get(i).getSymptoms());
        tvCount.setText("Count: " + arrStat.get(i).getCount());
        tvTreatment.setText("Treatment: " + arrStat.get(i).getTreatement());

        v.setTag(arrStat.get(i).getId());
        return v;
    }
}
