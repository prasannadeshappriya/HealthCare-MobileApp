package com.a14roxgmail.prasanna.healthcareapp.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.a14roxgmail.prasanna.healthcareapp.R;

/**
 * Created by Prasanna Deshappriya on 12/16/2016.
 */
public class mreports_fragment extends Fragment {
    private TextView lnkAddMReport;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mreports,container,false);
        init(view);

        lnkAddMReport.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mreport_insert_fragment report_insert = new mreport_insert_fragment();
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frmMain,report_insert);
                        fragmentTransaction.commit();
                    }
                }
        );
        return view;
    }

    private void init(View view) {
        //initialize everything
        lnkAddMReport =(TextView)view.findViewById(R.id.lnkAddMReport);
    }
}
