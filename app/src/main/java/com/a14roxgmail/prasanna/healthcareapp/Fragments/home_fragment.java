package com.a14roxgmail.prasanna.healthcareapp.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.a14roxgmail.prasanna.healthcareapp.ListView.*;
import com.a14roxgmail.prasanna.healthcareapp.ListView.adapters.adapter_tips;
import com.a14roxgmail.prasanna.healthcareapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prasanna Deshappriya on 12/1/2016.
 */
public class home_fragment extends Fragment {
    private ListView lvTips;
    private adapter_tips adapter;
    private List<tips> lstTips;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false );
        init(view);
        add_temp_items(view);
        return view;
    }

    private void add_temp_items(View view) {
        //this is the temp data
        lstTips.add(new tips("This is the tip number 1",1));
        lstTips.add(new tips("This is the tip number 6 This is the tip number 6" +
                            "This is the tip number 6 This is the tip number 6",2));
        lstTips.add(new tips("This is the tip number 2",3));
        lstTips.add(new tips("This is the tip number 3",4));
        lstTips.add(new tips("This is the tip number 4",5));
        lstTips.add(new tips("This is the tip number 5",6));
        lstTips.add(new tips("This is the tip number 6 This is the tip number 6" +
                "This is the tip number 6 This is the tip number 6" +
                "This is the tip number 6 This is the tip number 6" +
                "This is the tip number 6 This is the tip number 6",7));
        lstTips.add(new tips("This is the tip number 6 This is the tip number 6" +
                "This is the tip number 6 This is the tip number 6",8));
        lstTips.add(new tips("This is the tip number 6 This is the tip number 6" +
                "This is the tip number 6 This is the tip number 6",9));
        adapter = new adapter_tips(lstTips,getContext());
        lvTips.setAdapter(adapter);
    }

    private void init(View view) {
        lvTips = (ListView) view.findViewById(R.id.lstTips);
        lstTips = new ArrayList<tips>();

    }
}
