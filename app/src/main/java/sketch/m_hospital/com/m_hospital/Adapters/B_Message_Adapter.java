package sketch.m_hospital.com.m_hospital.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.json.JSONObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import sketch.m_hospital.com.m_hospital.Activities.GlobalClass;
import sketch.m_hospital.com.m_hospital.Activities.MyPreferences;
import sketch.m_hospital.com.m_hospital.R;

/**
 * Created by Developer on 7/12/17.
 */

public class B_Message_Adapter extends BaseAdapter
{

    String TAG="M";
    TextView time,msg;
    CheckBox chk_box;
    Context context;
    ImageView delete , img_delete_all;
    ArrayList<HashMap<String, String>> messages_arraylist ;
    Button btn_delete;
    private ArrayList<Boolean> status_arr = new ArrayList<>();
    public ArrayList<HashMap<String, String>> selected_ids;
    public ArrayList< String> selected_position;
    ProgressBar pb_intro ;
    GlobalClass globalclass;
    MyPreferences prefs;

    public B_Message_Adapter(Context context, ArrayList<HashMap<String, String>> arraylist , ImageView img_delete, Button btn_del, ProgressBar pb)
    {
        this.context=context;
        globalclass = (GlobalClass)context.getApplicationContext();
        prefs = new MyPreferences(context);
        this.messages_arraylist=arraylist;
        this.img_delete_all = img_delete;
        this.btn_delete = btn_del;
        this.pb_intro = pb;
        for (int i = 0; i < messages_arraylist.size(); i++) {
            status_arr.add(false);
        }
        selected_ids = new ArrayList<>();
        selected_position = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return messages_arraylist.size();
    }


    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public View getView(final int position, View convertview, ViewGroup parent)
    {

        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=null;
        if(messages_arraylist!=null) {
            view = inflater.inflate(R.layout.message_listview, parent, false);
            delete = (ImageView) view.findViewById(R.id.image_del);
            // delete.setVisibility(View.GONE);
            msg = (TextView) view.findViewById(R.id.text_message);
            time = (TextView) view.findViewById(R.id.text_time);
            chk_box = (CheckBox) view.findViewById(R.id.chk_box);
            chk_box.setVisibility(View.GONE);
            msg.setText(messages_arraylist.get(position).get("message"));
            time.setText(changeTimeFormat(messages_arraylist.get(position).get("time")));

            if (messages_arraylist.get(position).get("chkbx").equals("true")) {
                chk_box.setVisibility(View.VISIBLE);
                delete.setVisibility(View.GONE);
                notifyDataSetChanged();
                //check_show = false;
            } else {
                chk_box.setVisibility(View.GONE);
                delete.setVisibility(View.VISIBLE);
                //check_show = true;
            }

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                    builder1.setMessage("Do you really want to delete this message ?");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    deleteMessage(messages_arraylist.get(position).get("id"), position);
                                    //messages.remove(position);
                                    notifyDataSetChanged();

                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.setCanceledOnTouchOutside(false);
                    alert11.show();
                    delete.setVisibility(View.GONE);
                    notifyDataSetChanged();
                }
            });

            //restrict other checkboxes to get automatically checked while scrolling
            chk_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        status_arr.set(position, true);
                        selected_position.add(position+"");
                        HashSet<String> hashSet = new HashSet<String>();
                        hashSet.addAll(selected_position);
                        selected_position.clear();
                        selected_position.addAll(hashSet);
                        //Log.d("checked", position +" "+messages_arraylist.get(position).get("id") +" "+selected_position.get(0));
                    } else {
                        status_arr.set(position, false);
                        // selected_count = selected_count - 1;
                        selected_position.remove(position+"");
                    }
                }
            });
            chk_box.setChecked(status_arr.get(position));

            chk_box.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selected_ids.clear();

                    for (int i = 0; i < status_arr.size(); i++) {
                        if (status_arr.get(i).equals(true)) {
                            //Log.d("status size" , status.size()+"");
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put("holder" ,i+"");
                            map.put("value" ,messages_arraylist.get(i).get("id") );
                            selected_ids.add(map);
                           // selected_ids.add(messages.get(i).get("id"));
                        }
                    }


                    // global.setContacts_selectedUsers(selected_ids);
                    if (selected_ids.size() > 0) {
                        btn_delete.setVisibility(View.VISIBLE);
                    } else {
                        btn_delete.setVisibility(View.GONE);
                    }
                }
            });

            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int i=0;i<selected_ids.size();i++){
                        Log.d(TAG , selected_ids.get(i).get("holder") +" "+selected_ids.get(i).get("value"));
                    }
                    deleteMessageInArray(selected_ids);
                }
            });

            return view;
        }
        return null;
    }

    public String changeTimeFormat(String time){
        SimpleDateFormat df_source = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
        SimpleDateFormat df_destination = new SimpleDateFormat("EEE, d MMM yy, hh:mm a", Locale.ENGLISH);
        Date d = null;
        String s = null;
        try {
            d = df_source.parse(time);
            s = df_destination.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return s;
    }


    public void deleteMessage(String msg_id , final int pos) {

        pb_intro.setVisibility(View.VISIBLE);
        String android_id = Settings.Secure.getString(context.getContentResolver(),Settings.Secure.ANDROID_ID);
        String url = globalclass.getBaseUrl() + "api/deletemessages";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("user_id", globalclass.getId());
        params.put("msg_id[0]", msg_id);
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
                        if(status.equals("1")){
                            Log.d("M" , "deleting "+pos +" ");
                            messages_arraylist.remove(pos);
                            status_arr.remove(pos);
                            notifyDataSetChanged();
                            Toast.makeText(context , "Deleted Successfully" , Toast.LENGTH_SHORT).show();
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

    public void deleteMessageInArray(final ArrayList<HashMap<String, String>> arraylist) {

        pb_intro.setVisibility(View.VISIBLE);
        String android_id = Settings.Secure.getString(context.getContentResolver(),Settings.Secure.ANDROID_ID);
        String url = globalclass.getBaseUrl() + "api/deletemessages";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("user_id", globalclass.getId());
        for(int i =0; i<arraylist.size() ; i++){
            params.put("msg_id["+i+"]", arraylist.get(i).get("value"));
        }
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
                        Log.d(TAG, "response " + response);
                        String status = response.getString("status");
                        if(status.equals("1")){
                            for(int i =0; i<arraylist.size() ; i++){
                                Log.d(TAG, "messages remove " + arraylist.get(i).get("holder"));
                                int b=Integer.parseInt(arraylist.get(i).get("holder"));
                                messages_arraylist.remove(b-i);
                                status_arr.remove(b-i);

                            }
                            notifyDataSetChanged();
                        }

                        Toast.makeText(context , "Deleted Successfully" , Toast.LENGTH_SHORT).show();
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
