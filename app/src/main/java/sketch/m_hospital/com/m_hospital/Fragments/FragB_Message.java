package sketch.m_hospital.com.m_hospital.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.json.JSONArray;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import sketch.m_hospital.com.m_hospital.Activities.GlobalClass;
import sketch.m_hospital.com.m_hospital.Activities.MyPreferences;
import sketch.m_hospital.com.m_hospital.Adapters.B_Message_Adapter;
import sketch.m_hospital.com.m_hospital.R;

/**
 * Created by Developer on 6/12/17.
 */

public class FragB_Message extends Fragment {
    String TAG="M" ;
    ListView list;
    ImageView imageView , img,ic_logout;
    GlobalClass globalclass ;
    ProgressBar pb_intro;
    ImageView norecord;
    MyPreferences prefs;
    ArrayList<HashMap<String, String>> arraylist;
    B_Message_Adapter adapter;
    IntentFilter intentFilter;
    boolean checkbx=false;
    B_Message_Adapter adapter_invoice;
    Button btn_delete;

    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState)
    {
        final   View view=inflater.inflate(R.layout.frag_b_message,container,false);
        imageView=(ImageView)view.findViewById(R.id.image_del);
        globalclass = (GlobalClass)getActivity().getApplicationContext();
        prefs = new MyPreferences(getActivity());

        TextView tooltext=(TextView)getActivity().findViewById(R.id.text_toolbar);
        tooltext.setText(getString(R.string.message));

        img = (ImageView)getActivity().findViewById(R.id.ic_delete_all);
        img.setVisibility(View.VISIBLE);


      /*  ic_logout = (ImageView)getActivity().findViewById(R.id.ic_logout);
        ic_logout.setVisibility(View.GONE);*/
       /* tooltext.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
        tooltext.setIcon(R.drawable.ic_launcher);*/
        btn_delete = (Button)view.findViewById(R.id.btn_delete);
        btn_delete.setVisibility(View.GONE);
        pb_intro = (ProgressBar)view.findViewById(R.id.pb_intro);
        pb_intro.setVisibility(View.GONE);
        norecord = (ImageView)view.findViewById(R.id.norecord);
        norecord.setVisibility(View.GONE);
        arraylist = new ArrayList<>();
        list=(ListView)view.findViewById(R.id.listview);
        intentFilter = new IntentFilter("com.hmkcode.android.USER_ACTION");

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int i= view.getId();

                //Log.d(TAG,"xxxid"+i);

            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!checkbx){
                    checkbx = true;
                }else{
                    checkbx = false;
                }

                for(int i=0;i<arraylist.size();i++){
                arraylist.get(i).put("chkbx", checkbx+"");}
                adapter_invoice = new B_Message_Adapter(getActivity(), arraylist, img, btn_delete, pb_intro);
                list.setAdapter(adapter_invoice);
                adapter_invoice.notifyDataSetChanged();

            }
        });
        fetchMessage();
        return  view;

    }

    public void fetchMessage() {
        pb_intro.setVisibility(View.VISIBLE);
        String android_id = Settings.Secure.getString(getActivity().getContentResolver(),Settings.Secure.ANDROID_ID);
        String url = globalclass.getBaseUrl() + "api/fetchmessages";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("user_id", globalclass.getId());
        params.put("deviceid", android_id);
        params.put("fcm_reg_token",prefs.getPref_fcm());
        params.put("password",prefs.getPref_password());
        Log.d(TAG, "login " + url);
        Log.d(TAG, "login " + params.toString());
        client.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                if (response != null) {
                    try {
                        pb_intro.setVisibility(View.GONE);
                        String status = response.getString("status");
                        Log.d(TAG, "login " + response.toString());
                        if (status.equals("1")) {
                            JSONArray jsonArray = response.getJSONArray("message");
                            if(jsonArray.length()>0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject obj = jsonArray.getJSONObject(i);
                                    HashMap<String, String> map = new HashMap<String, String>();
                                    map.put("message" , obj.optString("message"));
                                    map.put("time" , obj.optString("date"));
                                    map.put("id" , obj.optString("id"));
                                    map.put("chkbx" , checkbx+"");
                                    arraylist.add(map);
                                }
                                Collections.reverse(arraylist);
                                adapter_invoice = new B_Message_Adapter(getActivity(), arraylist, img, btn_delete, pb_intro);
                                list.setAdapter(adapter_invoice);
                            }else{
                                norecord.setVisibility(View.VISIBLE);
                            }
                        } else if (status.equals("0")) {
                            norecord.setVisibility(View.VISIBLE);
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
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(broadcastReceiver, new IntentFilter("MESSAGE"));
    }

    @Override
    public void onPause() {
        super.onPause();
        img.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(broadcastReceiver);
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Refresh code here
            //Log.d("jjj","sdfghjk" + intent.getStringExtra("body"));
            addNewMessage(intent.getStringExtra("body"));
        }
    };

    public void addNewMessage(String msg){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("message" , msg);
        map.put("time" , currentDateandTime);
        map.put("id" , "");
        map.put("chkbx" , checkbx+"");
        Collections.reverse(arraylist);
        arraylist.add(map);
        Collections.reverse(arraylist);
        B_Message_Adapter adapter_invoice = new B_Message_Adapter(getActivity(), arraylist, img, btn_delete, pb_intro);
        list.setAdapter(adapter_invoice);
        norecord.setVisibility(View.GONE);
    }

}

