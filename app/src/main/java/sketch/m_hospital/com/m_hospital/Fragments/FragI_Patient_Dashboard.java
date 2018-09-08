package sketch.m_hospital.com.m_hospital.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import sketch.m_hospital.com.m_hospital.Activities.Appointment_Activity;
import sketch.m_hospital.com.m_hospital.Activities.Invoice_Activity;
import sketch.m_hospital.com.m_hospital.Activities.Report_Activity;
import sketch.m_hospital.com.m_hospital.R;

/**
 * Created by Developer on 7/26/17.
 */

public class FragI_Patient_Dashboard extends Fragment {
    Context context;
    RelativeLayout invoice_rl , appointment_rl , report_rl;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_i_patient_dashboard, container, false);

        TextView tooltext=(TextView)getActivity().findViewById(R.id.text_toolbar);
        tooltext.setText(getString(R.string.PatientDashboard));

        invoice_rl = (RelativeLayout)view.findViewById(R.id.invoice_rl);
        appointment_rl = (RelativeLayout)view.findViewById(R.id.appointment_rl);
        report_rl = (RelativeLayout)view.findViewById(R.id.report_rl);

        appointment_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Appointment_Activity.class);
                getActivity().startActivity(intent);
            }
        });

        invoice_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Invoice_Activity.class);
                getActivity().startActivity(intent);
            }
        });

        report_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Report_Activity.class);
                getActivity().startActivity(intent);
            }
        });

        return view;

    }
}
