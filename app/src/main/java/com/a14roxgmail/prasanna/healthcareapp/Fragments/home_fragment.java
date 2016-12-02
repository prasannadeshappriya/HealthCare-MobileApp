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
public class home_fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false );
        init(view);
        return view;
    }

    private void init(View view) {
    }
}
