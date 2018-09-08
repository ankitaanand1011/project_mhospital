package sketch.m_hospital.com.m_hospital.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wbc.fastscroll.FastScrollRecyclerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;
import sketch.m_hospital.com.m_hospital.Activities.GlobalClass;
import sketch.m_hospital.com.m_hospital.Adapters.ExampleAdapter;
import sketch.m_hospital.com.m_hospital.R;

public class FragG_Health_Encyclopedia extends Fragment  {
    ArrayList<HashMap<String , String>> arrayList;
    String TAG = "M";
    FastScrollRecyclerView recyclerView;
    GlobalClass globalClass;
    ProgressBar progressBar;
    FragmentTransaction fragmentTransaction;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_g_health_encyclopedia, container, false);

        TextView tooltext=(TextView)getActivity().findViewById(R.id.text_toolbar);
        tooltext.setText(getString(R.string.HealthEncyclopedia));

        globalClass = (GlobalClass)getActivity().getApplicationContext();
        progressBar = (ProgressBar)view.findViewById(R.id.pb_intro);
        progressBar.setVisibility(View.GONE);
        arrayList = new ArrayList<>();
        recyclerView = (FastScrollRecyclerView)view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        healthencyclopaedia();

        return view;
    }
    public void healthencyclopaedia()
    {
        progressBar.setVisibility(View.VISIBLE);
        String url= globalClass.getBaseUrl()+ "api/healthencyclopedia";
        AsyncHttpClient asyncHttpClient=new AsyncHttpClient();
        final RequestParams requestParams=new RequestParams();
        Log.d(TAG,"url "+url);
        asyncHttpClient.get(url,requestParams,new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Log.d(TAG,"res---"+response.toString());
                progressBar.setVisibility(View.GONE);
                if(response!=null)
                {
                    try {

                        String status=response.optString("status");
                        if(status.equals("1"));
                        {
                            Log.d(TAG,"status is 1");
                            JSONArray msgar=response.getJSONArray("message");
                            Log.d(TAG,"msgar.length"+msgar.length());
                            for(int i=0;i<msgar.length();i++) {
                                JSONObject msgobj = msgar.getJSONObject(i);
                                HashMap<String,String> map=new HashMap<String, String>();
                                map.put("disease_id",msgobj.getString("disease_id"));
                                map.put("disease_name",msgobj.getString("disease_name"));
                                map.put("disease_image",msgobj.getString("disease_image"));
                                map.put("disease_details",msgobj.getString("disease_details"));
                                map.put("doctor_id",msgobj.getString("doctor_id"));
                                map.put("doctor_name",msgobj.getString("doctor_name"));
                                map.put("doctor_branch",msgobj.getString("doctor_branch"));
                                map.put("doctor_dept",msgobj.getString("doctor_dept"));
                                map.put("doctor_sub_dept",msgobj.getString("doctor_sub_dept"));
                                map.put("doctor_image",msgobj.getString("doctor_image"));
                                arrayList.add(map);

                            }

                            Collections.sort(arrayList,new Comparator<HashMap<String,String>>() {
                                public int compare(HashMap<String, String> mapping1,
                                                   HashMap<String, String> mapping2) {
                                    return mapping1.get("disease_name").compareToIgnoreCase(mapping2.get("disease_name"));
                                }
                            });
                            recyclerView.setAdapter(new ExampleAdapter(arrayList, fragmentTransaction));

                        }
                    }catch(JSONException je)
                    {

                    }
                }


            }


            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d(TAG,"Server Error");

            }
        });

    }


}


