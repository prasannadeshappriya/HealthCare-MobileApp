package com.a14roxgmail.prasanna.healthcareapp.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a14roxgmail.prasanna.healthcareapp.R;

/**
 * Created by Prasanna Deshappriya on 12/1/2016.
 */
public class diseases_fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diseases,container,false);
        return view;
    }
}
