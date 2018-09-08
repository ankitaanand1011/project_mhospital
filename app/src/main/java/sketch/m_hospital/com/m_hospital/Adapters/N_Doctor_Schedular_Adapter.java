package sketch.m_hospital.com.m_hospital.Adapters;

/**
 * Created by Developer on 6/29/17.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import sketch.m_hospital.com.m_hospital.Activities.GlobalClass;
import sketch.m_hospital.com.m_hospital.Fragments.FragO_Payment;
import sketch.m_hospital.com.m_hospital.R;

public class N_Doctor_Schedular_Adapter extends BaseAdapter{
    String TAG="M";
    String tame;
    String from;
    private Context mContext;
    View grid;
    ArrayList<HashMap<String,String>> arraylist ;
    TextView timeslot;
    String docdate ;
    FragmentTransaction fragmentTransaction;
    GlobalClass globalClass;


    public N_Doctor_Schedular_Adapter(Context c,  ArrayList<HashMap<String,String>> arrayli ,  FragmentTransaction fragmentTransact , String from) {
        mContext = c;
        this.arraylist = arrayli;
        this.fragmentTransaction = fragmentTransact;
        globalClass = (GlobalClass)mContext.getApplicationContext();
        this.from = from ;

    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return arraylist.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
            grid = new View(mContext);
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grid = inflater.inflate(R.layout.doc_sched_grid, null);
        TextView textView1 = (TextView) grid.findViewById(R.id.grid_text);
        TextView textView2 = (TextView) grid.findViewById(R.id.grid_text2);
        TextView textView3 = (TextView) grid.findViewById(R.id.grid_text3);
        Button btn_appointment = (Button) grid.findViewById(R.id.btn_appointment);
        textView1.setText(arraylist.get(position).get("time_slot"));
        textView2.setText(arraylist.get(position).get("day"));
        textView3.setText(arraylist.get(position).get("date"));
        docdate=textView3.getText().toString();
        timeslot=(TextView)grid.findViewById(R.id.grid_text);
        tame=timeslot.getText().toString();

        btn_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(arraylist.get(position).get("branch_name")!=null){
                    globalClass.setQbranch_name(arraylist.get(position).get("branch_name"));
                }
                if(arraylist.get(position).get("dept_name")!=null){
                    globalClass.setQdept_name(arraylist.get(position).get("dept_name"));
                }
                if(arraylist.get(position).get("sub_dept_name")!=null){
                    globalClass.setQsubdept_name(arraylist.get(position).get("sub_dept_name"));
                }

                globalClass.setQdoctor_name(arraylist.get(position).get("doctor_name"));
                globalClass.setQdate_time(arraylist.get(position).get("day")+" "+arraylist.get(position).get("date")+" "+arraylist.get(position).get("time_slot"));
                globalClass.setQfee(arraylist.get(position).get("fee")+" "+arraylist.get(position).get("currency"));



                Bundle bundle = new Bundle();
                bundle.putString("doctor_name",arraylist.get(position).get("doctor_name"));
                bundle.putString("doctor_id",arraylist.get(position).get("doctor_id"));
                bundle.putString("time",arraylist.get(position).get("time_slot"));
                bundle.putString("day",arraylist.get(position).get("day"));
                bundle.putString("date",arraylist.get(position).get("date"));
                bundle.putString("fee",arraylist.get(position).get("fee"));
                bundle.putString("currency",arraylist.get(position).get("currency"));
                bundle.putString("schedule_id",arraylist.get(position).get("schedule_id"));
                bundle.putString("branch_id",arraylist.get(position).get("branch_id"));
                bundle.putString("dept_id",arraylist.get(position).get("dept_id"));
                bundle.putString("schedule_id",arraylist.get(position).get("schedule_id"));
                Fragment fragment=new FragO_Payment();
                fragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.frame,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return grid;
    }
}