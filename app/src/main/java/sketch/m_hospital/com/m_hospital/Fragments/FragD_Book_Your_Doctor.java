package sketch.m_hospital.com.m_hospital.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import sketch.m_hospital.com.m_hospital.Activities.GlobalClass;
import sketch.m_hospital.com.m_hospital.Adapters.D_Branch_Adapter;
import sketch.m_hospital.com.m_hospital.Models.Branch;
import sketch.m_hospital.com.m_hospital.R;

/**
 * Created by Developer on 7/26/17.
 */

public class FragD_Book_Your_Doctor extends Fragment {
    Context context;
    GlobalClass global;
    ProgressBar pb_intro;
    ImageView img_norecord;
    String TAG = "M";
    LayoutInflater layoutInflater;
    ArrayList<Branch> arrayList;
    ListView listView;
    FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_d_bookyourdoctor, container, false);
        TextView tooltext=getActivity().findViewById(R.id.text_toolbar);
        tooltext.setText(getString(R.string.SelectHospitalBranch));
        global = (GlobalClass)getActivity().getApplicationContext();
        layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater = LayoutInflater.from(getActivity());
        arrayList = new ArrayList<>();
        pb_intro = view.findViewById(R.id.pb_intro);
        pb_intro.setVisibility(View.GONE);
        img_norecord = view.findViewById(R.id.norecord);
        img_norecord.setVisibility(View.GONE);
        fragmentManager=getActivity().getSupportFragmentManager();
        listView = view.findViewById(R.id.listview);
       /* String[] branches={"Mhospital1","Mhospital2","Mhospital3","Mhospital4","Mhospital5","Mhospital6"};
        Integer[] imageid = {R.drawable.default_app, R.drawable.default_app, R.drawable.default_app, R.drawable.default_app,
                R.drawable.default_app, R.drawable.default_app};



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle =new Bundle();
                bundle.putString("from","home");
                Fragment fragment=new FragK_Dept();
                fragment.setArguments(bundle);
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
*/
        hospitalinfo();

        return view;
    }

    public void hospitalinfo() {
        //String android_id = Settings.Secure.getString(getActivity().getContentResolver(),Settings.Secure.ANDROID_ID);
        if (global.connectionAvailable()) {
            pb_intro.setVisibility(View.VISIBLE);
            String url = global.getBaseUrl()+ "api/hospitalinfo";
            AsyncHttpClient client = new AsyncHttpClient();
            Log.d(TAG, "hospitalinfo "+ url);
            client.get(url, null, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                    pb_intro.setVisibility(View.GONE);
                    if (response != null) {
                        try {
                            String status = response.getString("status");
                            Log.d(TAG, "hospitalinfo "+ response.toString());
                            if (status.equals("1")) {
                                JSONArray obj_message = response.getJSONArray("message");
                                JSONArray obj_branches = obj_message.getJSONObject(0).getJSONArray("branches");
                                for(int i=0;i<obj_branches.length();i++){
                                    JSONObject objBranch = obj_branches.getJSONObject(i);
                                    arrayList.add(new Branch(objBranch.getString("id"),objBranch.getString("main_hospital"),objBranch.getString("name"),
                                            objBranch.getString("img_url"),objBranch.getString("latitude"),objBranch.getString("longitude"),
                                            objBranch.getString("video_file"),objBranch.getString("video_type"),objBranch.getString("video_link")));
                                    D_Branch_Adapter adapter_branchScreen = new D_Branch_Adapter(getActivity(), arrayList , fragmentManager);
                                    listView.setAdapter(adapter_branchScreen);
                                }
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
