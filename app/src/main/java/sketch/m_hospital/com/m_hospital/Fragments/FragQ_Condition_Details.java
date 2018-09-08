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

public class FragQ_Condition_Details extends Fragment {
    Context context;
    JustifyTextView text_desc ; TextView text_possibility;
    String pname , pdesc;
    Button button_detail;
    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_q_condition_details, container, false);
        TextView tooltext=getActivity().findViewById(R.id.text_toolbar);
        tooltext.setText(getString(R.string.condition_details));
        pname = getArguments().getString("possibility");
        pdesc = getArguments().getString("possibility_details");
        text_desc = view.findViewById(R.id.text_desc);
        text_possibility =  view.findViewById(R.id.text_possibility);
        text_possibility.setText(pname);

        Spanned s = Html.fromHtml(pdesc,Html.FROM_HTML_MODE_LEGACY);
        Spanned s1 = Html.fromHtml(pdesc);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            text_desc.setText(s.toString());
        } else {
            text_desc.setText(s1.toString());
        }
        button_detail=view.findViewById(R.id.button_doctor);

        if(!getArguments().getString("doctor_id").equals("0")){
            button_detail.setVisibility(View.VISIBLE);
        }else{
            button_detail.setVisibility(View.GONE);
        }


        button_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("doctor_id", getArguments().getString("doctor_id"));
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