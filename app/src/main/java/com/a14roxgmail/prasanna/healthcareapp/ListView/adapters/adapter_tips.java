package com.a14roxgmail.prasanna.healthcareapp.ListView.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.a14roxgmail.prasanna.healthcareapp.Models.tips;
import com.a14roxgmail.prasanna.healthcareapp.R;

import java.util.List;

/**
 * Created by Prasanna Deshappriya on 12/7/2016.
 */
public class adapter_tips extends BaseAdapter {
    private List<tips> arrTips;
    private Context contect;

    public adapter_tips(List<tips> arrTips, Context contect) {
        this.arrTips = arrTips;
        this.contect = contect;
    }

    @Override
    public int getCount() {
        return arrTips.size();
    }

    @Override
    public Object getItem(int i) {
        return arrTips.get(i);
    }

    @Override
    public long getItemId(int i) {
        return arrTips.get(i).getID();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(contect, R.layout.adapter_lst_tips,null);

        TextView tvNum =(TextView) v.findViewById(R.id.tvAdapterNum);
        TextView tvDetails = (TextView) v.findViewById(R.id.tvAdapterDetails);

        tvNum.setText("Tip #" + String.valueOf(arrTips.get(i).getID()));
        tvDetails.setText(String.valueOf(arrTips.get(i).getDetail()));

        v.setTag(arrTips.get(i).getID());
        return v;
    }
}
