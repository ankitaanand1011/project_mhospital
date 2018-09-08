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

import sketch.m_hospital.com.m_hospital.Activities.GlobalClass;
import sketch.m_hospital.com.m_hospital.Adapters.E_BookHealth_Pkg_Adapter;
import sketch.m_hospital.com.m_hospital.Adapters.H_Health_Blog_Adapter;
import sketch.m_hospital.com.m_hospital.R;

/**
 * Created by Developer on 7/26/17.
 */

public class FragH_Health_Blog extends Fragment{
    Context context;
    ListView listView;
    ProgressBar pb_intro;
    GlobalClass globalClass; String TAG = "M";
    ArrayList<HashMap<String , String>> arraylist ;
    FragmentTransaction fragmentTransaction;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_h_health_blog, container, false);

        TextView tooltext=(TextView)getActivity().findViewById(R.id.text_toolbar);
        tooltext.setText(getString(R.string.HealthBlog));

        globalClass =(GlobalClass)getActivity().getApplication();
        listView=(ListView)view.findViewById(R.id.listview);
        pb_intro = (ProgressBar)view.findViewById(R.id.pb_intro);
        pb_intro.setVisibility(View.GONE);
        arraylist = new ArrayList<>();
        fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
        blog();
      /*  listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Fragment fragment=new FragH_Health_expanded();
                FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });*/
        return view;
    }

    public void blog() {
        pb_intro.setVisibility(View.VISIBLE);
        if (globalClass.connectionAvailable()) {
            String url = globalClass.getBaseUrl()+ "api/blog";
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
                                JSONArray jarray = new JSONArray(response.getString("message"));
                                JSONObject objk=null;
                                for(int i = 0 ; i<jarray.length() ; i++){
                                    HashMap<String, String> map = new HashMap<>();
                                    objk= jarray.getJSONObject(i);
                                    map.put("id", objk.getString("id"));
                                    map.put("name", objk.getString("name"));
                                    map.put("img_url", objk.getString("img_url"));
                                    map.put("description", objk.getString("description"));
                                    arraylist.add(map);
                                }
                                H_Health_Blog_Adapter adapter_healthblog=new H_Health_Blog_Adapter(getActivity(),arraylist, fragmentTransaction);
                                listView.setAdapter(adapter_healthblog);
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

