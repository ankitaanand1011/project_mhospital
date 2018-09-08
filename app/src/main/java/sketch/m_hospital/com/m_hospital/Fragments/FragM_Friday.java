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

import sketch.m_hospital.com.m_hospital.Activities.GlobalClass;
import sketch.m_hospital.com.m_hospital.Adapters.M_FridayAdapter;
import sketch.m_hospital.com.m_hospital.Adapters.M_SundayAdapter;
import sketch.m_hospital.com.m_hospital.R;

/**
 * Created by nazia on 30/8/17.
 */

public class FragM_Friday extends Fragment {
    String TAG="M";
    String Rdepsub,Rdep,Rbranch;
    Context context;
    GlobalClass global;
    GridView grid;
    ImageView img_norecord;
    ProgressBar pb_intro ;
    FragmentTransaction fragmentTransaction;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.frag_m_friday, container, false);
        img_norecord = (ImageView)rootView.findViewById(R.id.norecord);
        img_norecord.setVisibility(View.GONE);
        pb_intro = (ProgressBar)rootView.findViewById(R.id.pb_intro);
        pb_intro.setVisibility(View.GONE);
        global = (GlobalClass)getActivity().getApplicationContext();
        grid = (GridView) rootView.findViewById(R.id.grid);
        fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
        if (getArguments()!=null) {
            Rdepsub = getArguments().getString("Subdep");
            Rdep = getArguments().getString("Dep");
            Rbranch = getArguments().getString("branch_id");
        }
        Log.d(TAG,"global.getArrFriday() size "+global.getArrFriday().size());
        if(global.getArrFriday().size()>0) {
            M_FridayAdapter adapter = new M_FridayAdapter(getActivity(), global.getArrFriday() , Rbranch ,Rdep, Rdepsub , fragmentTransaction);
            grid.setAdapter(adapter);
        }else{
            img_norecord.setVisibility(View.VISIBLE);
        }


        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


              /*  Bundle bundle = new Bundle();
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


