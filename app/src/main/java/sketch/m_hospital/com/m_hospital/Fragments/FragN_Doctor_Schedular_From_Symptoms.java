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
import android.widget.ImageView;
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

public class FragN_Doctor_Schedular_From_Symptoms extends Fragment {
    Context context;
    GlobalClass globalclass;
    MyPreferences prefs;
    ProgressBar pb_intro;
    TextView img_norecord;
    GridView grid;
    TextView text_doctor , text_docotr_speciality;
    String TAG="M", speciality , qualifications, doctor_name;
    String branch,doctor_id;
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
        tooltext.setText(getString(R.string.DoctorSchedular));
        globalclass = (GlobalClass)getActivity().getApplicationContext();
        prefs = new MyPreferences(getActivity());
        text_doctor=(TextView)view.findViewById(R.id.text_doc_name);

        text_docotr_speciality = (TextView)view.findViewById(R.id.text_docotr_speciality);
        fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
        cir_imgvw = (CircleImageView)view.findViewById(R.id.image_doctor);
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

        pb_intro = (ProgressBar)view.findViewById(R.id.pb_intro);
        pb_intro.setVisibility(View.GONE);
        img_norecord = (TextView)view.findViewById(R.id.norecord);
        img_norecord.setVisibility(View.GONE);
        grid = (GridView) view.findViewById(R.id.grid);
        doctor_id = getArguments().getString("doctor_id");
        arraylist = new ArrayList<>();
        doctor_by_id(doctor_id);
        text_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = speciality+"\n\n"+qualifications;
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                builder1.setTitle(doctor_name);
                builder1.setMessage(s);
                builder1.setCancelable(true);
                builder1.setPositiveButton("OK",
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






      /*  grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Bundle bundle = new Bundle();
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
                fragmentTransaction.commit();
            }
        });
*/
        return view;
    }

    public void doctor_by_id(String doctor_id) {
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
            Log.d(TAG, "doctorbyid "+ url);
            Log.d(TAG, "param "+ param);
            client.post(url, param, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {

                    if (response != null) {
                        try {
                            String status = response.getString("status");
                            pb_intro.setVisibility(View.GONE);
                            Log.d(TAG, "doctorbyid "+ response.toString());
                            if (status.equals("1")) {
                                JSONArray jarray = new JSONArray(response.getString("message"));


                                for (int k=0;k<jarray.length();k++){

                                    JSONObject obj=jarray.getJSONObject(k);
                                    text_doctor.setText(obj.getString("name"));
                                    doctor_name = obj.getString("name");
                                    speciality=obj.getString("specialist");
                                    qualifications=obj.getString("qualification");
                                    loader.displayImage(globalclass.getImageBaseUrl()+obj.getString("image"), cir_imgvw , defaultOptions);

                                    JSONArray array_schedules=obj.getJSONArray("schedules");

                                    if(array_schedules.length()>0) {
                                        for (int i = 0; i < array_schedules.length(); i++) {

                                     /*   HashMap<String, String> map = new HashMap<>();
                                        JSONObject objk= array_schedules.getJSONObject(i);
                                        map.put("day", objk.getString("day"));
                                        map.put("time_slot", objk.getString("time_slot"));
                                        map.put("date", objk.getString("date"));
                                        map.put("fee", objk.getString("fee"));
                                        arraylist.add(map);*/

                                            HashMap<String, String> map = new HashMap<>();
                                            JSONObject objk = array_schedules.getJSONObject(i);

                                            map.put("doctor_id", obj.getString("id"));
                                            map.put("doctor_name", obj.getString("name"));
                                            map.put("doctor_image", obj.getString("image"));
                                            map.put("branch_name", obj.getString("branch_name"));
                                            map.put("dept_name", obj.getString("dept_name"));
                                            map.put("sub_dept_name", obj.optString("sub_dept_name"));

                                            map.put("schedule_id", objk.getString("schedule_id"));
                                            map.put("day", objk.getString("day"));
                                            map.put("time_slot", objk.getString("time_slot"));
                                            map.put("date", objk.getString("date"));
                                            map.put("fee", objk.getString("fee"));
                                            map.put("currency", objk.getString("currency"));
                                            arraylist.add(map);
                                        }
                                    }else{
                                        img_norecord.setVisibility(View.VISIBLE);
                                    }
                                }
                                N_Doctor_Schedular_Adapter adapter = new N_Doctor_Schedular_Adapter(getActivity(), arraylist, fragmentTransaction, "search");
                                grid.setAdapter(adapter);

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
