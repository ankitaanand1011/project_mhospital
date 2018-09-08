package sketch.m_hospital.com.m_hospital.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import sketch.m_hospital.com.m_hospital.Activities.GlobalClass;
import sketch.m_hospital.com.m_hospital.Adapters.M_SaturdayAdapter;
import sketch.m_hospital.com.m_hospital.Adapters.M_SundayAdapter;
import sketch.m_hospital.com.m_hospital.R;

/**
 * Created by nazia on 30/8/17.
 */

public class FragM_Saturday extends Fragment {
    String TAG="HOSPITAL";
    RelativeLayout Rel_one_left,Rel_one_right;
    String Rdepsub,Rdep,Rbranch;
    Context context;
    GlobalClass global;
    GridView grid;
    ImageView img_norecord;
    FragmentTransaction fragmentTransaction;
    ProgressBar pb_intro ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.frag_m_saturday, container, false);
        img_norecord = (ImageView)rootView.findViewById(R.id.norecord);
        img_norecord.setVisibility(View.GONE);
        pb_intro = (ProgressBar)rootView.findViewById(R.id.pb_intro);
        pb_intro.setVisibility(View.GONE);
        global = (GlobalClass)getActivity().getApplicationContext();
        fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
        grid = (GridView) rootView.findViewById(R.id.grid);
        if (getArguments()!=null) {
            Rdepsub = getArguments().getString("Subdep");
            Rdep = getArguments().getString("Dep");
            Rbranch = getArguments().getString("branch_id");
        }
        Log.d(TAG,"global.getArrSaturday() size "+global.getArrSaturday().size());
        if(global.getArrSaturday().size()>0) {
            M_SaturdayAdapter adapter = new M_SaturdayAdapter(getActivity(), global.getArrSaturday(), Rbranch ,Rdep, Rdepsub , fragmentTransaction);
            grid.setAdapter(adapter);
        }


        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


           /*     Bundle bundle = new Bundle();
                bundle.putString("Dep", Rdep);
                bundle.putString("Depsub", Rdepsub);
                bundle.putString("Branch", Rbranch);
                bundle.putString("Docnamecl", name[position]);

                Fragment fragment=new FragN_Doctor_Schedular();
                fragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();*/



            }
        });


        return rootView;
    }}


