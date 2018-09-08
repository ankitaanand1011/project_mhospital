package sketch.m_hospital.com.m_hospital.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import sketch.m_hospital.com.m_hospital.Activities.Container;
import sketch.m_hospital.com.m_hospital.Activities.GlobalClass;
import sketch.m_hospital.com.m_hospital.Activities.MyPreferences;
import sketch.m_hospital.com.m_hospital.R;

/**
 * Created by ANDROID on 10/9/2017.
 */

public class PaymentAdapter  extends BaseAdapter {
    Button btn_proceed;
    GlobalClass global;
    String TAG="HOSPITAL" ;
    MyPreferences prefs;
    Context context;
    ArrayList<HashMap<String, Integer>> arraylist ;
    String doctor_id, schedule_id, fee, currency;
    boolean[] status;
    boolean once;
    ProgressDialog progressDialog;

    public PaymentAdapter(FragmentActivity activity, ArrayList<HashMap<String, Integer>> arraylis , String doctor_i,String schedule_i,String fe,String currenc, Button btn_procee) {
        this.context = activity;
        this.arraylist = arraylis;
        global = (GlobalClass)context.getApplicationContext();
        prefs = new MyPreferences(context);
        this.doctor_id = doctor_i;
        this.schedule_id = schedule_i;
        this.fee = fe;
        this.currency = currenc;
        this.btn_proceed = btn_procee;
        status = new boolean[arraylist.size()];
        for(int i = 0 ; i<arraylist.size();i++){
            status[i] = false;
        }
        once=true;
        progressDialog=new ProgressDialog(context);
        if(progressDialog.isShowing())
            progressDialog.dismiss();
    }


    @Override
    public int getCount() {
        return arraylist.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return arraylist.size();
    }

    @NonNull
    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View v= convertView;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowview = inflater.inflate(R.layout.row_payment , null);
        RelativeLayout rl1 = rowview.findViewById(R.id.rl1);
        final RadioButton rb1 = rowview.findViewById(R.id.rb1);
        TextView txt1 = rowview.findViewById(R.id.gateway1) ;
        ImageView img1 = rowview.findViewById(R.id.img1);

        txt1.setText(context.getString(arraylist.get(position).get("name")));
        img1.setImageResource(arraylist.get(position).get("img"));

        if(once&&position==0){
            status[0] = true;
            rb1.setChecked(true);
        }
        if(status[position]){
            rb1.setChecked(true);
        }else{
            rb1.setChecked(false);
        }

        rb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                once=false;
                for(int i =0;i< arraylist.size();i++){
                    status[i] = false;
                }
                status[position] = true;
                notifyDataSetChanged();

            }
        });

        btn_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (!doctor_id.equals("no")) {
                        book_doctor(doctor_id, fee, currency);
                    } else {
                        purchaseHealthPackage(schedule_id, fee, currency);
                    }
            }
        });
        return rowview;
    }

    public void book_doctor(String doctor_id, String fee , String currency) {
        progressDialog.setMessage(context.getString(R.string.loading));
        progressDialog.show();
        String android_id = Settings.Secure.getString(context.getContentResolver(),Settings.Secure.ANDROID_ID);
        if (global.connectionAvailable()) {
            String url = global.getBaseUrl()+ "api/bookdoctor";
            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams param = new RequestParams();
            param.put("deviceid" ,android_id );
            param.put("fcm_reg_token" ,prefs.getPref_fcm());
            param.put("password" ,prefs.getPref_password());
            param.put("user_id" ,prefs.getPREFS_userid());
            param.put("doctor_id" , doctor_id);
            param.put("schedule_id" , schedule_id);
            param.put("branch" , global.getGbranch_id());
            param.put("department" ,global.getGdept_id());
            param.put("sub_department" ,global.getGsubdept_id() );
            param.put("fee" ,fee);
            param.put("currency" ,currency);

            Log.d(TAG, "info "+ url);
            Log.d(TAG, "param "+ param);
            client.post(url, param, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    if (response != null) {
                        try {
                            String status = response.getString("status");
                            if(status.equals("1")){
                                new LovelyStandardDialog(context)
                                        .setTopColorRes(R.color.colorAccent)
                                        .setButtonsColorRes(R.color.colorPrimaryDark)
                                        .setIcon(R.mipmap.ic_white_tick)
                                        .setTitle(R.string.payment)
                                        .setMessage(R.string.payment_done)
                                        .setPositiveButton(R.string.ok, new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                context.startActivity(new Intent(context,Container.class));
                                            }
                                        })
                                        .show();
                            }
                            Log.d(TAG, "info "+ response.toString());
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
        }
    }

    public void purchaseHealthPackage(String service_id , String fee , String currency) {
        progressDialog.setMessage(context.getString(R.string.loading));
        progressDialog.show();
        String android_id = Settings.Secure.getString(context.getContentResolver(),Settings.Secure.ANDROID_ID);
        if (global.connectionAvailable()) {
            String url = global.getBaseUrl()+ "api/paymenthealthpackage";
            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams param = new RequestParams();
            param.put("deviceid" ,android_id );
            param.put("fcm_reg_token" ,prefs.getPref_fcm());
            param.put("password" ,prefs.getPref_password());
            param.put("user_id" ,prefs.getPREFS_userid());
            param.put("service_id" , service_id);
            param.put("fee" , fee);
            param.put("currency" ,currency);

            Log.d(TAG, "info "+ url);
            Log.d(TAG, "param "+ param);
            client.post(url, param, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    if (response != null) {
                        try {
                            String status = response.getString("status");
                            if(status.equals("1")){
                                new LovelyStandardDialog(context)
                                        .setTopColorRes(R.color.colorAccent)
                                        .setButtonsColorRes(R.color.colorPrimaryDark)
                                        .setIcon(R.mipmap.ic_white_tick)
                                        .setTitle(R.string.payment)
                                        .setMessage(R.string.payment_done)
                                        .setPositiveButton(R.string.ok, new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                context.startActivity(new Intent(context,Container.class));
                                            }
                                        })
                                        .show();
                            }
                            Log.d(TAG, "info "+ response.toString());
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
        }
    }
}
