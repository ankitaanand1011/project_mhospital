package sketch.m_hospital.com.m_hospital.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import sketch.m_hospital.com.m_hospital.Activities.GlobalClass;
import sketch.m_hospital.com.m_hospital.Adapters.E_BookHealth_Pkg_Adapter;
import sketch.m_hospital.com.m_hospital.R;

/**
 * Created by Developer on 7/26/17.
 */

public class FragE_Book_Health_Package extends Fragment {
    Context context;
    ArrayList<HashMap<String , String>> arrayList_pkg ;
    GlobalClass globalClass;
    String TAG = "M";
    ListView lv_health_packag;
    ProgressBar pb_intro;
    JSONArray jarray;
    TextView text_header;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_e_book_health_package, container, false);
        globalClass =(GlobalClass)getActivity().getApplication();
        arrayList_pkg = new ArrayList<>();
        TextView tooltext=(TextView)getActivity().findViewById(R.id.text_toolbar);
        tooltext.setText(getString(R.string.BookHealthPackage));
        lv_health_packag=(ListView)view.findViewById(R.id.lv_health_package);
        pb_intro = (ProgressBar)view.findViewById(R.id.pb_intro);
        pb_intro.setVisibility(View.GONE);
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        Log.d("HOSPITAL","pkg_id@FrdagE_book_health_package"+getArguments().getString("pkg_id"));
        String pkg_id = getArguments().getString("pkg_id");

        String package_name=getArguments().getString("package_name");

      TextView textView=(TextView)getActivity().findViewById(R.id.text_toolbar);
        textView.setText(package_name);

        String jArrayString =  getArguments().getString("json");
        try {
            jarray = new JSONArray(jArrayString);
            // JSONArray jarray = new JSONArray(response.getString("message"));
            JSONObject objk=null;
            arrayList_pkg.clear();
            for(int i = 0 ; i<jarray.length() ; i++){
                HashMap<String, String> map_pkg = new HashMap<>();
                objk = jarray.getJSONObject(i);
                if(pkg_id.equals(objk.getString("package_id"))) {
                    map_pkg.put("id", objk.getString("id"));
                    map_pkg.put("name", objk.getString("name"));
                    map_pkg.put("details", objk.getString("details"));
                    map_pkg.put("price", objk.getString("price"));
                    map_pkg.put("currency", objk.getString("currency"));
                    map_pkg.put("package_id", objk.getString("package_id"));
                    map_pkg.put("package_name", objk.getString("package_name"));
                    map_pkg.put("package_image", objk.getString("package_image"));
                    arrayList_pkg.add(map_pkg);
                }
            }
            E_BookHealth_Pkg_Adapter adaper_pkg=new E_BookHealth_Pkg_Adapter(getActivity(),arrayList_pkg, fragmentTransaction);
            lv_health_packag.setAdapter(adaper_pkg);
        } catch (JSONException e) {
            e.printStackTrace();
        }



     /*   lv_package.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lv_package.setVisibility(View.GONE);
                lv_health_packag.setVisibility(View.VISIBLE);

            }
        });*/

        return view;
    }

}
