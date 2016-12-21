package com.a14roxgmail.prasanna.healthcareapp.ListView.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.a14roxgmail.prasanna.healthcareapp.Models.mreports;
import com.a14roxgmail.prasanna.healthcareapp.R;
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
        return arrMReport.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.adapter_lst_medical_report,null);

        TextView tvOfficerName = (TextView) v.findViewById(R.id.tvAdapterNameMedicalReport);
        TextView tvDiseaseName = (TextView) v.findViewById(R.id.tvAdapterDiseaseMedicalOfficer);
        TextView tvPrescription = (TextView) v.findViewById(R.id.tvAdapterPrescriptionMedicalofficer);
        TextView tvComments = (TextView) v.findViewById(R.id.tvAdapterCommentsMedicalOfficer);

        tvOfficerName.setText(arrMReport.get(i).getPatient_name());
        tvDiseaseName.setText("Disease: " + arrMReport.get(i).getDisease_name());
        tvPrescription.setText("Prescription: " + arrMReport.get(i).getPrescription());
        tvComments.setText("Comments: " + arrMReport.get(i).getComments());

        v.setTag(arrMReport.get(i).getId());
        return v;
    }
}
