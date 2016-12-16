package com.a14roxgmail.prasanna.healthcareapp.ListView.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.a14roxgmail.prasanna.healthcareapp.Models.mreports;

import java.util.List;

/**
 * Created by Prasanna Deshappriya on 12/16/2016.
 */
public class adapter_mreport extends BaseAdapter {
    private List<mreports> arrMReport;
    private Context context;

    public adapter_mreport(Context context, List<mreports> mreport) {
        this.context = context;
        this.arrMReport = mreport;
    }

    @Override
    public int getCount() {
        return arrMReport.size();
    }

    @Override
    public Object getItem(int i) {
        return arrMReport.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
