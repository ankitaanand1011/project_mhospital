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
import sketch.m_hospital.com.m_hospital.Adapters.M_SundayAdapter;
import sketch.m_hospital.com.m_hospital.R;

/**
 * Created by nazia on 30/8/17.
 */

public class FragM_Sunday extends Fragment {
    String TAG="M";
    String Rdepsub,Rdep,Rbranch, Rbranch_name , Rbranch_lat , Rbranch_lon;
    Context context;
    GlobalClass global;
    GridView grid;
    ImageView img_norecord;
    ProgressBar pb_intro ;
    FragmentTransaction fragmentTransaction;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.frag_m_sunday, container, false);
        img_norecord = (ImageView)rootView.findViewById(R.id.norecord);
        img_norecord.setVisibility(View.GONE);
        fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
        pb_intro = (ProgressBar)rootView.findViewById(R.id.pb_intro);
        pb_intro.setVisibility(View.GONE);
        global = (GlobalClass)getActivity().getApplicationContext();
        grid = (GridView) rootView.findViewById(R.id.grid);
        Rdepsub = global.getGsubdept_id();
        Rdep = global.getGdept_id();
        Rbranch = global.getGbranch_id();


        Log.d(TAG,"global.getArrSunday() size "+global.getArrSunday().size());
        if(global.getArrSunday().size()>0) {
            M_SundayAdapter adapter = new M_SundayAdapter(getActivity(), global.getArrSunday() , Rbranch ,Rdep, Rdepsub , fragmentTransaction);
            grid.setAdapter(adapter);

        }else{
            img_norecord.setVisibility(View.VISIBLE);
        }

         /*grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


               Bundle bundle = new Bundle();
                bundle.putString("Dep", Rdep);
                bundle.putString("Depsub", Rdepsub);
                bundle.putString("Branch", Rbranch);
                bundle.putString("Docnamecl", name[position]);

                Fragment fragment=new FragN_Doctor_Schedular();
                fragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();



            }
        });*/


        return rootView;
    }}


