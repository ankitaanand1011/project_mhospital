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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import sketch.m_hospital.com.m_hospital.Activities.GlobalClass;
import sketch.m_hospital.com.m_hospital.Adapters.E_Package_Adapter;
import sketch.m_hospital.com.m_hospital.R;

/**
 * Created by ANDROID on 10/10/2017.
 */

public class FragE_Package extends Fragment {
    Context context;
    ArrayList<HashMap<String , String>> arraylist , arrayList_pkg ;
    GlobalClass globalClass;
    String TAG = "M";
    ListView lv_package;
    ProgressBar pb_intro;
    FragmentTransaction fragmentTransaction;
    JSONArray jarray ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_e_package, container, false);
        globalClass =(GlobalClass)getActivity().getApplication();
        arraylist = new ArrayList<>();
        arrayList_pkg = new ArrayList<>();
        TextView tooltext=(TextView)getActivity().findViewById(R.id.text_toolbar);
        tooltext.setText(getString(R.string.HealthPackage));
        lv_package=(ListView)view.findViewById(R.id.lv_package);
        pb_intro = (ProgressBar)view.findViewById(R.id.pb_intro);
        pb_intro.setVisibility(View.GONE);
        healthPackage();

        lv_package.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                Log.d("HOSPITAL","pkg_id@tobe_sent"+arrayList_pkg.get(position).get("package_id"));
                bundle.putString("pkg_id", arrayList_pkg.get(position).get("package_id"));
                bundle.putString("package_name",arraylist.get(position).get("package_name"));
                bundle.putString("json", jarray.toString());
                Fragment fragment=new FragE_Book_Health_Package();
                fragment.setArguments(bundle);
                FragmentManager fragmentManager=getFragmentManager();
                fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

    public void healthPackage() {
        pb_intro.setVisibility(View.VISIBLE);
        if (globalClass.connectionAvailable()) {
            String url = globalClass.getBaseUrl()+ "api/healthpackages";
            AsyncHttpClient client = new AsyncHttpClient();
            Log.d(TAG, "info "+ url);
            client.get(url, null, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                    if (response != null) {
                        try {
                            pb_intro.setVisibility(View.GONE);
                            String status = response.getString("status");
                            Log.d(TAG, "info "+ response.toString());
                            if (status.equals("1")) {
                                jarray = new JSONArray(response.getString("message"));
                                JSONObject objk=null;
                                arraylist.clear();
                                arrayList_pkg.clear();
                                for(int i = 0 ; i<jarray.length() ; i++){
                                    HashMap<String, String> map = new HashMap<>();
                                    HashMap<String, String> map_pkg = new HashMap<>();
                                    objk= jarray.getJSONObject(i);
                                    map_pkg.put("id", objk.getString("id"));
                                    map_pkg.put("name", objk.getString("name"));
                                    map_pkg.put("details", objk.getString("details"));
                                    map_pkg.put("price", objk.getString("price"));
                                    map_pkg.put("currency", objk.getString("currency"));
                                    map_pkg.put("package_id", objk.getString("package_id"));
                                    map_pkg.put("package_name", objk.getString("package_name"));
                                    map_pkg.put("package_image", objk.getString("package_image"));
                                    map.put("package_id", objk.getString("package_id"));
                                    map.put("package_name", objk.getString("package_name"));
                                    map.put("package_image", objk.getString("package_image"));
                                    arraylist.add(map);
                                    arrayList_pkg.add(map_pkg);
                                }
globalClass.setPackage_id(objk.optString("package_id"));
                                Log.d("HOSPITAL","package_id"+objk.optString("package_id"));
                                Log.d("HOSPITAL","package_id@global"+globalClass.getPackage_id());
                                HashSet<HashMap<String , String>> hashSet = new HashSet<>();
                                hashSet.addAll(arraylist);
                                arraylist.clear();
                                arraylist.addAll(hashSet);

                                E_Package_Adapter adaper=new E_Package_Adapter(getActivity(),arraylist);
                                lv_package.setAdapter(adaper);

                              /*  E_BookHealth_Pkg_Adapter adaper_pkg=new E_BookHealth_Pkg_Adapter(getActivity(),arrayList_pkg);
                                lv_health_packag.setAdapter(adaper_pkg);*/
                            }else{
                                //no data found

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                @Override
                public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String res, Throwable t) {
                }
            });
        } else {
            new LovelyStandardDialog(getActivity())
                    .setTopColorRes(R.color.colorAccent)
                    .setButtonsColorRes(R.color.colorPrimaryDark)
                    .setIcon(R.mipmap.ic_white_cross)
                    .setTitle(R.string.internet_connection)
                    .setMessage(R.string.no_connection)
                    .setPositiveButton(R.string.ok, null)
                    .show();
        }
    }


}
