package sketch.m_hospital.com.m_hospital.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;
import sketch.m_hospital.com.m_hospital.Activities.GlobalClass;
import sketch.m_hospital.com.m_hospital.Activities.MyPreferences;
import sketch.m_hospital.com.m_hospital.R;

/**
 * Created by Developer on 7/26/17.
 */

public class FragO_Payment extends Fragment {
    Context context;
    GlobalClass global;
    String TAG="HOSPITAL" , schedule_id, doctor_id ;
    TextView patient_name , depart_name , subdept_namr , doc_name , branch_loc , date , fees ;
    ProgressDialog progressDialog;
    MyPreferences prefs;
    Button btn_appointment;
    RadioButton rb_yes , rb_no;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_o_payment, container, false);
        TextView tooltext=getActivity().findViewById(R.id.text_toolbar);
        tooltext.setText(getString(R.string.payment));

        global = (GlobalClass)getActivity().getApplicationContext();
        prefs = new MyPreferences(getActivity());
        patient_name = view.findViewById(R.id.patient_name);
        btn_appointment = view.findViewById(R.id.btn_appointment);
        rb_yes = view.findViewById(R.id.rb_yes);
        rb_no = view.findViewById(R.id.rb_no);
        depart_name = view.findViewById(R.id.depart_name);
        subdept_namr = view.findViewById(R.id.subdept_namr);
        doc_name = view.findViewById(R.id.doc_name);
        branch_loc = view.findViewById(R.id.branch_loc);
        date = view.findViewById(R.id.date);
        fees = view.findViewById(R.id.fees);
        progressDialog = new ProgressDialog(getActivity());
        if (progressDialog.isShowing())
            progressDialog.dismiss();
        //---
        patient_name.setText(global.getUser_name());
        depart_name.setText(global.getQdept_name());
        subdept_namr.setText(global.getQsubdept_name());
        doc_name.setText(global.getQdoctor_name());
        branch_loc.setText(global.getQbranch_name());
        date.setText(global.getQdate_time());
        fees.setText(global.getQfee());
        //---
        schedule_id = getArguments().getString("schedule_id");
        doctor_id = getArguments().getString("doctor_id");
        btn_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rb_yes.isChecked()) {
                    Bundle bundle =new Bundle();
                    bundle.putString("doctor_id",doctor_id);
                    bundle.putString("schedule_id",schedule_id);
                    bundle.putString("fee",getArguments().getString("fee"));
                    bundle.putString("currency",getArguments().getString("currency"));
                    Fragment fragment=new FragO_PayOptions();
                    fragment.setArguments(bundle);
                    FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame,fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }else{
                    new LovelyStandardDialog(getActivity())
                            .setTopColorRes(R.color.colorAccent)
                            .setButtonsColorRes(R.color.colorPrimaryDark)
                            .setIcon(R.mipmap.ic_white_cross)
                            .setTitle("Doctor Appointment")
                            .setMessage("Please confirm your appointment,  by Selecting Yes")
                            .setPositiveButton(R.string.ok, null)
                            .show();
                }
            }
        });
        return view;
    }
}
