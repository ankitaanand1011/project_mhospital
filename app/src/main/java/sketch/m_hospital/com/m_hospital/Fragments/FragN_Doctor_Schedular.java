package sketch.m_hospital.com.m_hospital.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import de.hdodenhof.circleimageview.CircleImageView;
import sketch.m_hospital.com.m_hospital.Activities.GlobalClass;
import sketch.m_hospital.com.m_hospital.Activities.MyPreferences;
import sketch.m_hospital.com.m_hospital.Adapters.N_Doctor_Schedular_Adapter;
import sketch.m_hospital.com.m_hospital.R;

/**
 * Created by Developer on 7/26/17.
 */

public class FragN_Doctor_Schedular extends Fragment {
    Context context;
    GlobalClass globalclass;
    MyPreferences prefs;
    ProgressBar pb_intro;
    TextView img_norecord;
    GridView grid;
    TextView text_doctor , text_docotr_speciality;
    String TAG="M";
    String depsub,dep,branch,doctor_id , qualifications;
    ArrayList<HashMap<String,String>> arraylist ;
    public ImageLoader loader;
    DisplayImageOptions defaultOptions;
    CircleImageView cir_imgvw;
    FragmentTransaction fragmentTransaction;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_n_dotor_schedular, container, false);
        TextView tooltext=(TextView)getActivity().findViewById(R.id.text_toolbar);
        tooltext.setText(getString(R.string.AvailabilityOfDoctors));
        globalclass = (GlobalClass)getActivity().getApplicationContext();
        Log.d("make" , "make"+globalclass.getUser_name());
        prefs = new MyPreferences(getActivity());
        text_doctor=(TextView)view.findViewById(R.id.text_doc_name);
        //doctor_name = getArguments().getString("doctor_name");
        text_doctor.setText(getArguments().getString("doctor_name"));
        text_docotr_speciality = (TextView)view.findViewById(R.id.text_docotr_speciality);
        text_docotr_speciality.setText(getArguments().getString("doctor_speciality"));
        qualifications = getArguments().getString("doctor_qualification");

        cir_imgvw = (CircleImageView)view.findViewById(R.id.image_doctor);
        fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
        loader = ImageLoader.getInstance();
        if(!loader.isInited()) {
            defaultOptions = new DisplayImageOptions.Builder()
                    .cacheOnDisk(true).cacheInMemory(true)
                    .imageScaleType(ImageScaleType.EXACTLY)
                    .displayer(new FadeInBitmapDisplayer(300)).build();
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                    getActivity().getApplicationContext())
                    .defaultDisplayImageOptions(defaultOptions)
                    .memoryCache(new WeakMemoryCache())
                    .diskCacheSize(100 * 1024 * 1024).build();
            ImageLoader.getInstance().init(config);
        }


        if(!getArguments().getString("doctor_image").equals(""))
        {
            Log.d("doctor_image" ,getArguments().getString("doctor_image") );
            loader.displayImage(globalclass.getImageBaseUrl()+getArguments().getString("doctor_image"), cir_imgvw , defaultOptions);
        }
        pb_intro = (ProgressBar)view.findViewById(R.id.pb_intro);
        pb_intro.setVisibility(View.GONE);
        img_norecord = (TextView)view.findViewById(R.id.norecord);
        img_norecord.setVisibility(View.GONE);
        grid = (GridView) view.findViewById(R.id.grid);
        doctor_id = getArguments().getString("Docnamecl");
        arraylist = new ArrayList<>();
        doctor_schedular(doctor_id);
        text_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s = text_docotr_speciality.getText().toString()+"\n\n"+qualifications;
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                builder1.setTitle(getArguments().getString("doctor_name"));
                builder1.setMessage(s);
                builder1.setCancelable(true);
                builder1.setPositiveButton(getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.setCanceledOnTouchOutside(false);
                alert11.show();
            }
        });

        return view;
    }

    public void doctor_schedular(String doctor_id) {
        pb_intro.setVisibility(View.VISIBLE);
        String android_id = Settings.Secure.getString(getActivity().getContentResolver(),Settings.Secure.ANDROID_ID);
        if (globalclass.connectionAvailable()) {
            String url = globalclass.getBaseUrl()+ "api/doctorschedule";
            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams param = new RequestParams();
            param.put("deviceid" ,android_id );
            param.put("fcm_reg_token" ,prefs.getPref_fcm());
            param.put("password" ,prefs.getPref_password());
            param.put("doctor_id" , doctor_id);
            param.put("branch" , globalclass.getGbranch_id() );
            param.put("department" ,globalclass.getGdept_id() );
            param.put("sub_department" ,globalclass.getGsubdept_id() );
            Log.d(TAG, "info "+ url);
            Log.d(TAG, "param "+ param);
            client.post(url, param, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {

                    if (response != null) {
                        try {
                            String status = response.getString("status");
                            pb_intro.setVisibility(View.GONE);
                            Log.d(TAG, "info "+ response.toString());
                            if (status.equals("1")) {
                                JSONArray jarray = new JSONArray(response.getString("message"));
                                JSONObject objk=null;
                                for(int i = 0 ; i<jarray.length() ; i++){
                                    HashMap<String, String> map = new HashMap<>();
                                    objk= jarray.getJSONObject(i);
                                    map.put("schedule_id", objk.getString("schedule_id"));
                                    map.put("doctor_id", objk.getString("doctor_id"));
                                    map.put("doctor_name", objk.getString("doctor_name"));
                                    map.put("doctor_image", objk.getString("doctor_image"));
                                    map.put("branch_id", objk.getString("branch_id"));
                                    map.put("dept_id", objk.getString("dept_id"));
                                    map.put("day", objk.getString("day"));
                                    map.put("time_slot", objk.getString("time_slot"));
                                    map.put("date", objk.getString("date"));
                                    map.put("fee", objk.getString("fee"));
                                    map.put("currency", objk.getString("currency"));
                                    arraylist.add(map);
                                }
                                N_Doctor_Schedular_Adapter adapter = new N_Doctor_Schedular_Adapter(getActivity(), arraylist, fragmentTransaction, "flow" );
                                grid.setAdapter(adapter);
                            }else{
                                img_norecord.setVisibility(View.VISIBLE);
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

  /*  public void doctor_by_id(String doctor_id) {
        pb_intro.setVisibility(View.VISIBLE);
        String android_id = Settings.Secure.getString(getActivity().getContentResolver(),Settings.Secure.ANDROID_ID);
        if (globalclass.connectionAvailable()) {
            String url = globalclass.getBaseUrl()+ "api/doctorbyid";
            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams param = new RequestParams();
            param.put("deviceid" ,android_id );
            param.put("fcm_reg_token" ,prefs.getPref_fcm());
            param.put("password" ,prefs.getPref_password());
            param.put("doctor_id" , doctor_id);
            Log.d(TAG, "info "+ url);
            Log.d(TAG, "param "+ param);
            client.post(url, param, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {

                    if (response != null) {
                        try {
                            String status = response.getString("status");
                            pb_intro.setVisibility(View.GONE);
                            Log.d(TAG, "info "+ response.toString());
                            if (status.equals("1")) {
                                JSONArray jarray = new JSONArray(response.getString("message"));
                                JSONObject objk=null;
                                for(int i = 0 ; i<jarray.length() ; i++){
                                    HashMap<String, String> map = new HashMap<>();
                                    objk= jarray.getJSONObject(i);
                                    map.put("schedule_id", objk.getString("schedule_id"));
                                    map.put("doctor_id", objk.getString("doctor_id"));
                                    map.put("doctor_name", objk.getString("doctor_name"));
                                    map.put("doctor_image", objk.getString("doctor_image"));
                                    map.put("branch_id", objk.getString("branch_id"));
                                    map.put("dept_id", objk.getString("dept_id"));
                                    map.put("day", objk.getString("day"));
                                    map.put("time_slot", objk.getString("time_slot"));
                                    map.put("date", objk.getString("date"));
                                    map.put("fee", objk.getString("fee"));
                                    map.put("currency", objk.getString("currency"));
                                    arraylist.add(map);
                                }

                                //  N_Doctor_Schedular_Adapter adapter = new N_Doctor_Schedular_Adapter(getActivity(), time, day, date, request,depsub,dep,docnamecl,branch,detaildoc1,detaildoc2,dnameD,dnameE);
                                N_Doctor_Schedular_Adapter adapter = new N_Doctor_Schedular_Adapter(getActivity(), arraylist);
                                //adapter.notifyDataSetChanged();
                                grid.setAdapter(adapter);

                               *//* Bundle bundle = new Bundle();
                                bundle.putString("Dep", dep);
                                bundle.putString("Depsub", depsub);
                                bundle.putString("Branch", branch);
                                bundle.putString("Dname", dnameD);
                                bundle.putString("Docdate", date[position]);
                                bundle.putString("Dnamedetail1",detaildoc1);
                                bundle.putString("Dnamedetail2",detaildoc2);
                                bundle.putString("DnameD",dnameD);

                                Fragment fragment=new FragO_Payment();
                                fragment.setArguments(bundle);
                                FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.frame,fragment);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();*//*
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
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
        }
    }*/
}
