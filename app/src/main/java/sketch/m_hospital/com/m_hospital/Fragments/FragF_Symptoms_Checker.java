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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

import cz.msebera.android.httpclient.Header;
import sketch.m_hospital.com.m_hospital.Activities.GlobalClass;
import sketch.m_hospital.com.m_hospital.Activities.MyPreferences;
import sketch.m_hospital.com.m_hospital.Adapters.F_Symptoms_Checker_Adapter;
import sketch.m_hospital.com.m_hospital.Models.Branch;
import sketch.m_hospital.com.m_hospital.R;

/**
 * Created by Developer on 7/26/17.
 */
public class FragF_Symptoms_Checker extends Fragment {
    Context context;
    MyPreferences prefs;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    ArrayList<HashMap<String, String>> possiblitiesarray = new ArrayList<>();
    F_Symptoms_Checker_Adapter listAdapter;
    ExpandableListView expListView;
    ProgressBar progressBar, image_search_pb;
    GlobalClass globalClass;
    String TAG="M";
    AutoCompleteTextView edt_search;
    boolean searchnow=true;
    String android_id;
    ImageView image_search;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_f_symptoms_checker, container, false);

        globalClass=(GlobalClass)getActivity().getApplicationContext();
        prefs = new MyPreferences(getActivity());
        android_id = Settings.Secure.getString(getActivity().getContentResolver(),Settings.Secure.ANDROID_ID);
        edt_search=(AutoCompleteTextView) view.findViewById(R.id.edt_search);
        TextView tooltext=(TextView)getActivity().findViewById(R.id.text_toolbar);
        tooltext.setText(getString(R.string.SymptomsChecker));

        expListView = (ExpandableListView) view.findViewById(R.id.lvExp);

        progressBar = (ProgressBar)view.findViewById(R.id.pb_intro);
        progressBar.setVisibility(View.GONE);
        image_search_pb= (ProgressBar)view.findViewById(R.id.image_search_pb);
        image_search_pb.setVisibility(View.GONE);
        image_search = (ImageView)view.findViewById(R.id.image_search);
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
                }
                if (s.toString().length() >= 2) {

                    if (searchnow) {
                        search(s.toString());
                        Log.d(TAG, "search called ");
                        searchnow=false;
                    }
                }



            }
        });




        symptoms();
        return  view;
    }

    public void symptoms() {
        progressBar.setVisibility(View.VISIBLE);
        final ArrayList<Branch> arrayList = new ArrayList<>();
        if (globalClass.connectionAvailable()) {
            String url = globalClass.getBaseUrl()+ "api/symptoms";
            AsyncHttpClient client = new AsyncHttpClient();
            Log.d(TAG, "info "+ url);
            client.get(url, null, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    if (response != null) {
                        try {
                            progressBar.setVisibility(View.GONE);
                            String status = response.getString("status");
                            Log.d(TAG, "info "+ response.toString());
                            if (status.equals("1")) {

                                listDataHeader = new ArrayList<String>();
                                listDataChild = new HashMap<String, List<String>>();
                                possiblitiesarray.clear();
                                JSONArray message=response.getJSONArray("message");

                                for (int i=0;i<message.length();i++){

                                    JSONObject obj=message.getJSONObject(i);
                                    listDataHeader.add(obj.getString("name"));

                                    JSONArray array=obj.getJSONArray("organs");
                                    List<String> list = new ArrayList<String>();

                                    for (int k=0;k<array.length();k++){
                                        JSONObject obj2=array.getJSONObject(k);
                                        list.add(obj2.getString("name"));

                                        JSONArray pos=obj2.optJSONArray("possibilities");
                                            for (int l = 0; l < pos.length(); l++) {
                                                JSONObject obj3 = pos.getJSONObject(l);

                                                HashMap<String, String> map2 = new HashMap<String, String>();
                                                map2.put("key", obj2.getString("name"));
                                                map2.put("id", obj3.getString("id"));
                                                map2.put("possibility", obj3.getString("name"));
                                                map2.put("doctor_id", obj3.getString("doctor_id"));
                                                map2.put("possibility_details", obj3.getString("condition_details"));

                                                possiblitiesarray.add(map2);
                                            }
                                    }
                                    listDataChild.put(listDataHeader.get(i), list);

                                }

                                FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
                                listAdapter = new F_Symptoms_Checker_Adapter(getActivity(),ft, listDataHeader, listDataChild,possiblitiesarray);

                                // setting list adapter
                                expListView.setAdapter(listAdapter);
                            }else{
                                //no data found
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
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
            image_search_pb.setVisibility(View.VISIBLE);
            image_search.setVisibility(View.INVISIBLE);
            String url = globalClass.getBaseUrl() + "api/searchsymptom";
            final AsyncHttpClient client = new AsyncHttpClient();
            try {
                RequestParams param = new RequestParams();
                param.put("search_keyword", key);
                param.put("deviceid" ,android_id );
                param.put("fcm_reg_token" ,prefs.getPref_fcm());
                param.put("password" ,prefs.getPref_password());
                Log.d(TAG, "doctorbyname "+ url);
                Log.d(TAG, "param "+ param);
                client.post(getActivity(),url, param,new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                        searchnow=true;
                        image_search_pb.setVisibility(View.GONE);
                        image_search.setVisibility(View.VISIBLE);
                        if (response != null) {
                            try {

                                String status = response.getString("status");
                                Log.d(TAG, "doctorbyname "+response.toString());


                                if (status.equals("1")) {
                                    final List<HashMap<String, String>> list= new ArrayList<HashMap<String,String>>();
                                    final ArrayList<HashMap<String, String>> arr_list= new ArrayList<HashMap<String,String>>();
                                    JSONArray array=response.getJSONArray("message");
                                    for (int i=0;i<array.length();i++)
                                    {
                                        JSONObject obj=array.getJSONObject(i);
                                        HashMap<String, String> map= new HashMap<String, String>();
                                        map.put("possibility",  obj.getString("possibility"));
                                        map.put("possibility_details",  obj.getString("possibility_details"));
                                        map.put("doctor_id",  obj.getString("doctor_id"));
                                        list.add(map);
                                        arr_list.add(map);
                                    }

                                    String[] from = { "possibility" };
                                    int[] to = { R.id.txt};
                                    SimpleAdapter arrayAdapter = new SimpleAdapter(getActivity(),list,R.layout.list_doctors,from,to);
                                    edt_search.setAdapter(arrayAdapter);
                                    edt_search.showDropDown();
                                    edt_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            hideKeyboard(edt_search);
                                            edt_search.setText("");
                                            globalClass.setArrPossiblities(arr_list);
                                            Bundle bundle = new Bundle();
                                            bundle.putString("possibility", arr_list.get(position).get("possibility"));
                                            bundle.putString("possibility_details", arr_list.get(position).get("possibility_details"));
                                            bundle.putString("doctor_id", arr_list.get(position).get("doctor_id"));
                                            FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
                                            Fragment fragment = new FragQ_Condition_Details();
                                            fragment.setArguments(bundle);
                                            fragmentTransaction.replace(R.id.frame, fragment);
                                            fragmentTransaction.addToBackStack(null);
                                            fragmentTransaction.commit();

                                         /*   Bundle bundle = new Bundle();
                                            bundle.putString("doctor_id", list.get(position).get("doctor_id"));
                                            Fragment fragment=new FragN_Doctor_Schedular_From_Symptoms();
                                            fragment.setArguments(bundle);
                                            FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
                                            fragmentTransaction.replace(R.id.frame,fragment);
                                            fragmentTransaction.addToBackStack(null);
                                            fragmentTransaction.commit();*/
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
