package sketch.m_hospital.com.m_hospital.Fragments;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import me.biubiubiu.justifytext.library.JustifyTextView;
import sketch.m_hospital.com.m_hospital.R;

/**
 * Created by Developer on 7/26/17.
 */

public class FragG1_Encyclopedia_Details extends Fragment {
    Context context;
    Button button_doctor;
    JustifyTextView textView; TextView textView2;
    String disease_name,doctor_id,disease_details;
    String TAG="M";
    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_g1_encyclo_details, container, false);

        button_doctor=view.findViewById(R.id.button_doctor) ;
        textView=view.findViewById(R.id.text_details);
        textView2=view.findViewById(R.id.text_disease_name);

        TextView tooltext=getActivity().findViewById(R.id.text_toolbar);
        tooltext.setText(getString(R.string.EncyclopediaDetails));
        disease_name=getArguments().getString("disease_name");
        disease_details=getArguments().getString("disease_details");
        doctor_id=getArguments().getString("doctor_id");
        Spanned s = Html.fromHtml(disease_details,Html.FROM_HTML_MODE_LEGACY);
        Spanned s1 = Html.fromHtml(disease_details);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            textView2.setText(Html.fromHtml(disease_name,Html.FROM_HTML_MODE_LEGACY));
            textView.setText(s.toString());
        } else {
            textView2.setText(Html.fromHtml(disease_name));
            textView.setText(s1.toString());
        }

        button_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("doctor_id", doctor_id);
                Fragment fragment=new FragN_Doctor_Schedular_From_Symptoms();
                fragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return view;
    }
}