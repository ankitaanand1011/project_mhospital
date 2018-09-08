package sketch.m_hospital.com.m_hospital.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sketch.m_hospital.com.m_hospital.Activities.GlobalClass;
import sketch.m_hospital.com.m_hospital.Activities.MyPreferences;
import sketch.m_hospital.com.m_hospital.Adapters.L_Sub_Dept_Adapter;
import sketch.m_hospital.com.m_hospital.R;

/**
 * Created by Developer on 7/27/17.
 */

public class FragL_Sub_Dept extends Fragment {
    ImageView img_norecord;
    ProgressBar pb_intro ;
    String TAG="HOSPITAL",branch_id, dept_id, branch_name , branch_lat , branch_lon , dept_name;
    GridView grid;
    EditText editText;
    GlobalClass globalClass;
    String fromwhere,edit_search_string;
    RelativeLayout Lin_search;
    MyPreferences prefs;
    ArrayList<HashMap<String, String>> arrayList ;
    FragmentTransaction fragmentTransaction;
    AutoCompleteTextView edt_search;
    boolean searchnow=true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_l_sub_departments, container, false);
        globalClass =(GlobalClass)getActivity().getApplicationContext();
        Log.d("make" , "make"+globalClass.getUser_name());
        prefs = new MyPreferences(getActivity());
        TextView tooltext=(TextView)getActivity().findViewById(R.id.text_toolbar);
        tooltext.setText(getString(R.string.sub_department));
        edt_search=(AutoCompleteTextView) view.findViewById(R.id.edt_search);
        img_norecord = (ImageView)view.findViewById(R.id.norecord);
        img_norecord.setVisibility(View.GONE);
        pb_intro = (ProgressBar)view.findViewById(R.id.pb_intro);
        pb_intro.setVisibility(View.GONE);
        grid=(GridView)view.findViewById(R.id.grid);
        arrayList = new ArrayList<>();
        Lin_search=(RelativeLayout) view.findViewById(R.id.Lin_search);
        branch_id=getArguments().getString("branch_id");
        fromwhere=getArguments().getString("from");
        dept_id = getArguments().getString("Dep");
        branch_name = getArguments().getString("branch_name");
        branch_lat = getArguments().getString("branch_lat");
        branch_lon = getArguments().getString("branch_lon");
        dept_name = getArguments().getString("dept_name");
        if(fromwhere.equals("info"))
        {
            Lin_search.setVisibility(View.GONE);
        }
        getActivity(). getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
        ImageView  search = (ImageView) view.findViewById(R.id.image_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_search_string=editText.getText().toString();
                if(edit_search_string.isEmpty())
                {
                    editText.setError("Kindly enter a doctor's name");
                }
            }
        });
        sub_departments();

        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                if (s.toString().length()<2){
                    edt_search.dismissDropDown();

                    Log.d("drop", "dismisscalled: ");
                }


                if (s.toString().length() >= 2) {

                    if (searchnow) {
                        search(s.toString());
                        searchnow=false;
                    }
                }
            }
        });
        return view;
    }

    public void sub_departments() {
        String android_id = Settings.Secure.getString(getActivity().getContentResolver(),Settings.Secure.ANDROID_ID);
        if (globalClass.connectionAvailable()) {
            pb_intro.setVisibility(View.VISIBLE);
            String url = globalClass.getBaseUrl()+ "api/branchsubdepartment";
            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams param = new RequestParams();
            param.put("department" ,dept_id );
            param.put("deviceid" ,android_id );
            param.put("fcm_reg_token" ,prefs.getPref_fcm());
            param.put("password" ,prefs.getPref_password());
            param.put("branch" ,branch_id);
            Log.d(TAG, "info "+ url);
            Log.d(TAG, "param "+ param);
            client.post(url, param, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                    pb_intro.setVisibility(View.GONE);
                    if (response != null) {
                        try {
                            String status = response.getString("status");
                            Log.d(TAG, "info "+ response.toString());
                            if (status.equals("1")) {
                                JSONArray jarray = new JSONArray(response.getString("message"));
                                JSONObject objk=null;
                                for(int i = 0 ; i<jarray.length() ; i++){
                                    HashMap<String, String> map = new HashMap<>();
                                    objk= jarray.getJSONObject(i);
                                    map.put("id", objk.getString("id"));
                                    map.put("branch", objk.getString("branch"));
                                    map.put("department_id", objk.getString("department_id"));
                                    map.put("name", objk.getString("name"));
                                    map.put("img_url", objk.getString("img_url"));
                                    arrayList.add(map);
                                }
                                String subdept_name = objk.getString("name");
                                L_Sub_Dept_Adapter adapter = new L_Sub_Dept_Adapter(getActivity(), arrayList, branch_id , fromwhere,dept_id, fragmentTransaction, branch_name , branch_lat, branch_lat, dept_name , subdept_name );
                                grid.setAdapter(adapter);
                            }else{
                                //no data found
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

    public void search(String key) {
        if(globalClass.connectionAvailable()){
        String url = globalClass.getBaseUrl() + "api/doctorbyname";
            final AsyncHttpClient client = new AsyncHttpClient();
            try {
                RequestParams param = new RequestParams();
                param.put("doctor", key);
                param.put("deviceid", key);
                param.put("fcm_reg_token", key);
                param.put("password", key);

                client.post(getActivity(),url, param,new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {


                        searchnow=true;
                        if (response != null) {
                            try {

                                String status = response.getString("status");
                                Log.d("response", response.toString());


                                if (status.equals("1")) {



                                    final List<HashMap<String, String>> list= new ArrayList<HashMap<String,String>>();

                                    JSONArray array=response.getJSONArray("message");
                                    for (int i=0;i<array.length();i++)
                                    {
                                        JSONObject obj=array.getJSONObject(i);
                                        HashMap<String, String> map= new HashMap<String, String>();
                                        map.put("id",  obj.getString("id"));
                                        map.put("name",  obj.getString("name"));
                                        list.add(map);
                                    }

                                    String[] from = { "name" };

                                    int[] to = { R.id.txt};
                                    SimpleAdapter arrayAdapter = new SimpleAdapter(getActivity(),list,R.layout.list_doctors,from,to);
                                    edt_search.setAdapter(arrayAdapter);
                                    edt_search.showDropDown();
                                    Log.d("drop", "showcalled: ");




                                    edt_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                            hideKeyboard(edt_search);
                                            edt_search.setText("");
                                            Bundle bundle = new Bundle();
                                            bundle.putString("doctor_id", list.get(position).get("id"));
                                            Fragment fragment=new FragN_Doctor_Schedular_From_Symptoms();
                                            fragment.setArguments(bundle);
                                            FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
                                            fragmentTransaction.replace(R.id.frame,fragment);
                                            fragmentTransaction.addToBackStack(null);
                                            fragmentTransaction.commit();


                                        }
                                    });


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

            }catch (Exception e) {
                e.printStackTrace();
            }

        } else {

            AlertDialog alert1 = new AlertDialog.Builder(getActivity()).create();
            alert1.setMessage(getActivity().getString(R.string.no_connection));
            alert1.show();
        }
    }

    private void hideKeyboard(EditText ed) {
        if (ed != null) {
            InputMethodManager imanager = (InputMethodManager) getActivity()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imanager.hideSoftInputFromWindow(ed.getWindowToken(), 0);

        }

    }
}
