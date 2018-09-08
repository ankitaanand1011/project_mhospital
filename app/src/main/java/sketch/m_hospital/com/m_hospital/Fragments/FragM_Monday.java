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
import sketch.m_hospital.com.m_hospital.Adapters.M_MondayAdapter;
import sketch.m_hospital.com.m_hospital.Adapters.M_SaturdayAdapter;
import sketch.m_hospital.com.m_hospital.Adapters.M_SundayAdapter;
import sketch.m_hospital.com.m_hospital.R;

/**
 * Created by Jonny on 6/25/2017.
 */
public class FragM_Monday extends Fragment {
    String TAG="M";
    RelativeLayout Rel_one_left,Rel_one_right;
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

        View rootView = inflater.inflate(R.layout.frag_m_monday, container, false);
        fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
        img_norecord = (ImageView)rootView.findViewById(R.id.norecord);
        img_norecord.setVisibility(View.GONE);
        pb_intro = (ProgressBar)rootView.findViewById(R.id.pb_intro);
        pb_intro.setVisibility(View.GONE);
        global = (GlobalClass)getActivity().getApplicationContext();
        grid = (GridView) rootView.findViewById(R.id.grid);
        if (getArguments()!=null) {
            Rdepsub = getArguments().getString("Subdep");
            Rdep = getArguments().getString("Dep");
            Rbranch = getArguments().getString("branch_id");
        }
        Log.d(TAG,"global.getArrMonday() size "+global.getArrMonday().size());
        if(global.getArrMonday().size()>0) {
            M_MondayAdapter adapter = new M_MondayAdapter(getActivity(), global.getArrMonday() , Rbranch ,Rdep, Rdepsub , fragmentTransaction);
            grid.setAdapter(adapter);
           // adapter.notifyDataSetChanged();
        }else{
            img_norecord.setVisibility(View.VISIBLE);
        }

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            /*    Bundle bundle = new Bundle();
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
*/


            }
        });


        return rootView;
    }}
