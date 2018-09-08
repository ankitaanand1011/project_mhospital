package sketch.m_hospital.com.m_hospital.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import sketch.m_hospital.com.m_hospital.Fragments.FragQ_Condition_Details;
import sketch.m_hospital.com.m_hospital.R;
/**
 * Created by Developer on 7/7/17.
 */
public class P_Possibilities_Adapter extends ArrayAdapter {
    Context context;
    FragmentTransaction fragmentTransaction;
    ArrayList<HashMap<String, String>> posarray = new ArrayList<>();
    public P_Possibilities_Adapter(Context con, ArrayList<HashMap<String, String>> posarray , FragmentTransaction fragmentTrans)
    {
        super(con, R.layout.sympos_listview,posarray);
        this.context=con;
        this.posarray=posarray;
        this.fragmentTransaction = fragmentTrans;
    }
    public View getView(final int position, View container, ViewGroup parent)
    {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootview=inflater.inflate(R.layout.sympos_listview,parent,false);
        TextView textView=(TextView)rootview.findViewById(R.id.grid_text);
        textView.setText(posarray.get(position).get("possibility"));
        rootview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle =new Bundle();
                bundle.putString("possibility",posarray.get(position).get("possibility"));
                bundle.putString("possibility_details",posarray.get(position).get("possibility_details"));
                bundle.putString("doctor_id",posarray.get(position).get("doctor_id"));
                Fragment fragment=new FragQ_Condition_Details();
                fragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.frame,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return  rootview;
    }
}
