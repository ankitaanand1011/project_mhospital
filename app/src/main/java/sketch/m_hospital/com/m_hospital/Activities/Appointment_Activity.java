 package sketch.m_hospital.com.m_hospital.Activities;

import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import sketch.m_hospital.com.m_hospital.Adapters.Adapter_appointment;
import sketch.m_hospital.com.m_hospital.R;

/**
 * Created by ANDROID on 9/18/2017.
 */

public class Appointment_Activity extends AppCompatActivity {
    private static final String TAG = "M";
    ListView listView;
    GlobalClass globalClass;
    ArrayList<HashMap<String , String>> invoice_list;
    ImageView img_back;
    Adapter_appointment adapter_invoice;
    MyPreferences prefs;
    ProgressBar pb_intro;
    ImageView norecord;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.appointment_activity);
        prefs = new MyPreferences(Appointment_Activity.this);
        globalClass = (GlobalClass) getApplicationContext();
        pb_intro = (ProgressBar)findViewById(R.id.pb_intro);
        pb_intro.setVisibility(View.GONE);
        norecord = (ImageView)findViewById(R.id.norecord);
        norecord.setVisibility(View.GONE);
        invoice_list=new ArrayList<>();
        listView = (ListView) findViewById(R.id.listview_invoice);
        img_back = (ImageView) findViewById(R.id.image_arrow);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        appointment();
    }

    public void appointment() {
        pb_intro.setVisibility(View.VISIBLE);
        String android_id = Settings.Secure.getString(Appointment_Activity.this.getContentResolver(),Settings.Secure.ANDROID_ID);
        String url = globalClass.getBaseUrl() + "api/appointment";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("user_id", globalClass.getId());
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
                                    map.put("doctor_name" , obj.optString("doctor_name"));
                                    map.put("doctor_image" , obj.optString("doctor_image"));
                                    map.put("day" , obj.optString("day"));
                                    map.put("date" , obj.optString("date"));
                                    map.put("time_slot" , obj.optString("time_slot"));
                                    map.put("fee" , obj.optString("fee")+" "+obj.optString("currency"));
                                    invoice_list.add(map);
                                }
                                adapter_invoice = new Adapter_appointment(Appointment_Activity.this, invoice_list);
                                listView.setAdapter(adapter_invoice);
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
}
