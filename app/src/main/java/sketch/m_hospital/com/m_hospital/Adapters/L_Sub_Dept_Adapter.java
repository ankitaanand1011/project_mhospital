package sketch.m_hospital.com.m_hospital.Adapters;

/**
 * Created by Developer on 6/29/17.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
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
import sketch.m_hospital.com.m_hospital.Activities.GlobalClass;
import sketch.m_hospital.com.m_hospital.Activities.MyPreferences;
import sketch.m_hospital.com.m_hospital.Fragments.FragM_Doctor_Calendar;
import sketch.m_hospital.com.m_hospital.R;

/**
 * Created by Developer on 6/29/17.
 */

public class L_Sub_Dept_Adapter extends BaseAdapter {
    ProgressDialog progressDialog;
    String TAG="M" , branch_name , branch_lat , branch_lon , dept_name , subdept_name;
    private Context mContext;
    String dep;
    View grid;
    ArrayList<HashMap<String, String>> arrayList ;
    String fromwhere, branch;
    FragmentTransaction fragmentTransact;
    public ImageLoader loader;
    DisplayImageOptions defaultOptions;
    TextView text_doctor;
    GlobalClass globalClass;
    MyPreferences prefs;
    ArrayList<HashMap<String , String>> arr_monday , arr_tuesday , arr_wednesday , arr_thursday , arr_friday ,
            arr_saturday , arr_sunday;

    public L_Sub_Dept_Adapter(Context c, ArrayList<HashMap<String, String>> arr, String branch , String from ,String dept_id, FragmentTransaction fragmentTran, String branch_name , String branch_lat , String branch_lon , String dept_name , String subdept_name) {
        mContext = c;
        this.arrayList = arr;
        this.fromwhere = from;
        this.dep = dept_id;
        this.branch = branch;
        this.branch_name = branch_name;
        this.branch_lat = branch_lat;
        this.branch_lon = branch_lon;
        this.dept_name = dept_name;
        this.subdept_name = subdept_name;
        this.fragmentTransact = fragmentTran;
        prefs = new MyPreferences(mContext);
        progressDialog = new ProgressDialog(mContext);
        if (progressDialog.isShowing())
            progressDialog.dismiss();
        globalClass =(GlobalClass)mContext.getApplicationContext();
        defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                mContext.getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .diskCacheSize(100 * 1024 * 1024).build();
        ImageLoader.getInstance().init(config);
        loader = ImageLoader.getInstance();
        arr_monday = new ArrayList<>();
        arr_tuesday = new ArrayList<>();
        arr_wednesday = new ArrayList<>();
        arr_thursday = new ArrayList<>();
        arr_friday = new ArrayList<>();
        arr_saturday = new ArrayList<>();
        arr_sunday = new ArrayList<>();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return arrayList.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        grid = new View(mContext);
        grid = inflater.inflate(R.layout.department_sub_grid, null);
        TextView textView =  grid.findViewById(R.id.grid_text);
        text_doctor=grid.findViewById(R.id.grid_text);
        ImageView imageView = grid.findViewById(R.id.grid_image);
        if(!arrayList.get(position).get("img_url").equals(""))
        {
            loader.displayImage(globalClass.getImageBaseUrl()+arrayList.get(position).get("img_url"), imageView , defaultOptions);
        }
        textView.setText(arrayList.get(position).get("name"));
        grid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doctor_calender(branch , dep , arrayList.get(position).get("id"));
                if(fromwhere.equals("info")){

                }else {

                    /*Bundle bundle = new Bundle();
                    bundle.putString("branch_id", branch);
                    bundle.putString("Dep", dep);
                    bundle.putString("subdep", arrayList.get(position).get("id"));
                    Fragment fragment=new FragM_Doctor_Calendar();
                    fragment.setArguments(bundle);
                    fragmentTransact.replace(R.id.frame,fragment);
                    fragmentTransact.addToBackStack(null);
                    fragmentTransact.commit();*/
                }
            }
        });
        return grid;
    }

    public void doctor_calender(String branch_id , String dept_id ,final String subdept_id) {
        progressDialog.setMessage(mContext.getString(R.string.searching_doctor));
        progressDialog.show();
        String android_id = Settings.Secure.getString(mContext.getContentResolver(),Settings.Secure.ANDROID_ID);
        if (globalClass.connectionAvailable()) {
            String url = globalClass.getBaseUrl()+ "api/doctorcalendar";
            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams param = new RequestParams();
            param.put("deviceid" ,android_id );
            param.put("fcm_reg_token" ,prefs.getPref_fcm());
            param.put("password" ,prefs.getPref_password());
            param.put("branch" ,branch_id );
            param.put("department" ,dept_id );
            param.put("sub_department" ,subdept_id );
            Log.d(TAG, "info "+ url);
            Log.d(TAG, "param "+ param);
            client.post(url, param, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {

                    if (response != null) {
                        try {
                            String status = response.getString("status");
                            progressDialog.dismiss();
                            Log.d(TAG, "info "+ response.toString());
                            String dayCalendar=response.optString("day");
                            globalClass.setDayCalendar(dayCalendar);
                            Log.d("HOSPITAL","DAYofcalendar"+globalClass.getDayCalendar());
                            if (status.equals("1")) {
                                JSONArray jarray = new JSONArray(response.getString("message"));
                                JSONObject objk=null;
                                for(int i = 0 ; i<jarray.length() ; i++){
                                    HashMap<String, String> map = new HashMap<>();
                                    objk= jarray.getJSONObject(i);

                                    if(objk.getString("day").equals("Sunday")){
                                        map.put("doctor_id", objk.getString("doctor_id"));
                                        map.put("doctor_name", objk.getString("doctor_name"));
                                        map.put("doctor_image", objk.getString("doctor_image"));
                                        map.put("doctor_speciality", objk.getString("doctor_speciality"));
                                        map.put("doctor_qualification", objk.getString("doctor_qualification"));
                                        map.put("branch_id", objk.getString("branch_id"));
                                        map.put("dept_id", objk.getString("dept_id"));
                                        map.put("time_slot", objk.getString("time_slot"));
                                        map.put("date", objk.getString("date"));
                                        map.put("fee", objk.getString("fee"));
                                        arr_sunday.add(map);
                                    }else if(objk.getString("day").equals("Monday")){
                                        map.put("doctor_id", objk.getString("doctor_id"));
                                        map.put("doctor_name", objk.getString("doctor_name"));
                                        map.put("doctor_image", objk.getString("doctor_image"));
                                        map.put("doctor_speciality", objk.getString("doctor_speciality"));
                                        map.put("doctor_qualification", objk.getString("doctor_qualification"));
                                        map.put("branch_id", objk.getString("branch_id"));
                                        map.put("dept_id", objk.getString("dept_id"));
                                        map.put("time_slot", objk.getString("time_slot"));
                                        map.put("date", objk.getString("date"));
                                        map.put("fee", objk.getString("fee"));
                                        arr_monday.add(map);
                                    }else if(objk.getString("day").equals("Tuesday")){
                                        map.put("doctor_id", objk.getString("doctor_id"));
                                        map.put("doctor_name", objk.getString("doctor_name"));
                                        map.put("doctor_image", objk.getString("doctor_image"));
                                        map.put("doctor_speciality", objk.getString("doctor_speciality"));
                                        map.put("doctor_qualification", objk.getString("doctor_qualification"));
                                        map.put("branch_id", objk.getString("branch_id"));
                                        map.put("dept_id", objk.getString("dept_id"));
                                        map.put("time_slot", objk.getString("time_slot"));
                                        map.put("date", objk.getString("date"));
                                        map.put("fee", objk.getString("fee"));
                                        arr_tuesday.add(map);
                                    }else if(objk.getString("day").equals("Wednesday")){
                                        map.put("doctor_id", objk.getString("doctor_id"));
                                        map.put("doctor_name", objk.getString("doctor_name"));
                                        map.put("doctor_image", objk.getString("doctor_image"));
                                        map.put("doctor_speciality", objk.getString("doctor_speciality"));
                                        map.put("doctor_qualification", objk.getString("doctor_qualification"));
                                        map.put("branch_id", objk.getString("branch_id"));
                                        map.put("dept_id", objk.getString("dept_id"));
                                        map.put("time_slot", objk.getString("time_slot"));
                                        map.put("date", objk.getString("date"));
                                        map.put("fee", objk.getString("fee"));
                                        arr_wednesday.add(map);
                                    }else if(objk.getString("day").equals("Thursday")){
                                        map.put("doctor_id", objk.getString("doctor_id"));
                                        map.put("doctor_name", objk.getString("doctor_name"));
                                        map.put("doctor_image", objk.getString("doctor_image"));
                                        map.put("doctor_speciality", objk.getString("doctor_speciality"));
                                        map.put("doctor_qualification", objk.getString("doctor_qualification"));
                                        map.put("branch_id", objk.getString("branch_id"));
                                        map.put("dept_id", objk.getString("dept_id"));
                                        map.put("time_slot", objk.getString("time_slot"));
                                        map.put("date", objk.getString("date"));
                                        map.put("fee", objk.getString("fee"));
                                        arr_thursday.add(map);
                                    }else if(objk.getString("day").equals("Friday")){
                                        map.put("doctor_id", objk.getString("doctor_id"));
                                        map.put("doctor_name", objk.getString("doctor_name"));
                                        map.put("doctor_image", objk.getString("doctor_image"));
                                        map.put("doctor_speciality", objk.getString("doctor_speciality"));
                                        map.put("doctor_qualification", objk.getString("doctor_qualification"));
                                        map.put("branch_id", objk.getString("branch_id"));
                                        map.put("dept_id", objk.getString("dept_id"));
                                        map.put("time_slot", objk.getString("time_slot"));
                                        map.put("date", objk.getString("date"));
                                        map.put("fee", objk.getString("fee"));
                                        arr_friday.add(map);
                                    }else{
                                        map.put("doctor_id", objk.getString("doctor_id"));
                                        map.put("doctor_name", objk.getString("doctor_name"));
                                        map.put("doctor_image", objk.getString("doctor_image"));
                                        map.put("doctor_speciality", objk.getString("doctor_speciality"));
                                        map.put("doctor_qualification", objk.getString("doctor_qualification"));
                                        map.put("branch_id", objk.getString("branch_id"));
                                        map.put("dept_id", objk.getString("dept_id"));
                                        map.put("time_slot", objk.getString("time_slot"));
                                        map.put("date", objk.getString("date"));
                                        map.put("fee", objk.getString("fee"));
                                        arr_saturday.add(map);
                                    }
                                }
                                    globalClass.setArrMonday(arr_monday);
                                    globalClass.setArrTuesday(arr_tuesday);
                                    globalClass.setArrWednesday(arr_wednesday);
                                    globalClass.setArrThursday(arr_thursday);
                                    globalClass.setArrFriday(arr_friday);
                                    globalClass.setArrSaturday(arr_saturday);
                                    globalClass.setArrSunday(arr_sunday);
                                progressDialog.dismiss();
                                globalClass.setGbranch_id(branch);
                                globalClass.setGdept_id(dep);
                                globalClass.setGsubdept_id(subdept_id);
                                globalClass.setQbranch_name(branch_name);
                                globalClass.setQbranch_lat(branch_lat);
                                globalClass.setQbranch_lon(branch_lon);
                                globalClass.setQdept_name(dept_name);
                                globalClass.setQsubdept_name(subdept_name);
                                Bundle bundle = new Bundle();
                                bundle.putString("branch_id", branch);
                                bundle.putString("Dep", dep);
                                bundle.putString("subdep", subdept_id);
                                bundle.putString("branch_name" , branch_name);
                                bundle.putString("branch_lat" , branch_lat);
                                bundle.putString("branch_lon" , branch_lon);
                                Fragment fragment=new FragM_Doctor_Calendar();
                                fragment.setArguments(bundle);
                                fragmentTransact.replace(R.id.frame,fragment);
                                fragmentTransact.addToBackStack(null);
                                fragmentTransact.commit();

                                /* Bundle bundle = new Bundle();
                                bundle.putString("Dep", dept_id);
                                bundle.putString("Subdep", subdept_id);
                                Fragment fragment=new FragM_Sunday();
                                fragment.setArguments(bundle);*/

                            }else{
                                //no data found
                                progressDialog.dismiss();
                                Toast.makeText(mContext , mContext.getString(R.string.NoDoctorsFound) , Toast.LENGTH_SHORT).show();

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
            new LovelyStandardDialog(mContext)
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