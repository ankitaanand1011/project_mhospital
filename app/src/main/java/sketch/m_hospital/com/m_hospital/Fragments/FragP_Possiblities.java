package sketch.m_hospital.com.m_hospital.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import sketch.m_hospital.com.m_hospital.Activities.GlobalClass;
import sketch.m_hospital.com.m_hospital.Adapters.P_Possibilities_Adapter;
import sketch.m_hospital.com.m_hospital.R;

/**
 * Created by Developer on 7/26/17.
 */

public class FragP_Possiblities extends Fragment {
    Context context;
    GlobalClass globalClass;
    ArrayList<HashMap<String, String>> posarray = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_p_possiblities, container, false);
        globalClass=(GlobalClass)getActivity().getApplicationContext();
        TextView tooltext=(TextView)getActivity().findViewById(R.id.text_toolbar);
        tooltext.setText(getString(R.string.possibilities));
        ListView list=(ListView)view.findViewById(R.id.listview);
        posarray=globalClass.getArrPossiblities();
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        P_Possibilities_Adapter adaper=new P_Possibilities_Adapter(getActivity(),posarray , fragmentTransaction);
        list.setAdapter(adaper);

        return view;
    }

}