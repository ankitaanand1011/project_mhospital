package sketch.m_hospital.com.m_hospital.Fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

import me.biubiubiu.justifytext.library.JustifyTextView;
import sketch.m_hospital.com.m_hospital.Activities.GlobalClass;
import sketch.m_hospital.com.m_hospital.R;


/**
 * Created by Developer on 7/26/17.
 */

public class FragC_Hospital_Info extends Fragment {
    Context context;
    LinearLayout lin_branches,rr;
    LayoutInflater layoutInflater;
    GlobalClass globalClass;
    Toolbar toolbar;
    TextView tooltext ; JustifyTextView text_info;
    Fragment fragment=null;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    String TAG = "M" , from = "info";
    ProgressBar pb_intro ;
    ArrayList<HashMap<String, String>> arrayList ;
    DisplayImageOptions defaultOptions;
    public ImageLoader loader;
    JSONArray obj_branches;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_c_hospital_info, container, false);
        tooltext=getActivity().findViewById(R.id.text_toolbar);
        tooltext.setText(getString(R.string.HospitalInformations));
        arrayList = new ArrayList<>();
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getActivity().getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .diskCacheSize(100 * 1024 * 1024).build();
        ImageLoader.getInstance().init(config);
        loader = ImageLoader.getInstance();
        pb_intro = view.findViewById(R.id.pb_intro);
        pb_intro.setVisibility(View.GONE);
        globalClass =(GlobalClass)getActivity().getApplication();
        toolbar= globalClass.getToolbar();
        text_info=view.findViewById(R.id.text_info);
        lin_branches= view.findViewById(R.id.Lin_branches);
        layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater = LayoutInflater.from(getActivity());
        rr =  view.findViewById(R.id.Lin_branches);
        Spanned s = Html.fromHtml(globalClass.getHospital_intro(),Html.FROM_HTML_MODE_LEGACY);
        Spanned s1 = Html.fromHtml(globalClass.getHospital_intro());
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            text_info.setText(s.toString());
        } else {
            text_info.setText(s1.toString());
        }
        hospitalinfo();
        return  view;
    }

    public void hospitalinfo(){
        obj_branches =  globalClass.getBranch_jArray();
        for(int i=0;i<obj_branches.length();i++) {
            JSONObject objBranch = null;
            try {
                objBranch = obj_branches.getJSONObject(i);
                HashMap<String, ImageView> map = new HashMap<>();
                final View v = layoutInflater.inflate(R.layout.inflate_layout, null);
                final String branch_id = objBranch.getString("id");
                final TextView tv = (TextView) v.findViewById(R.id.branch_name);
                tv.setText(objBranch.getString("name"));
                final ImageView img = (ImageView) v.findViewById(R.id.branch_img);
                map.put(i + "", img);
                if (!objBranch.getString("img_url").equals("")) {
                    //loader.displayImage("http://after12thwhat.com/wp-content/uploads/2016/01/Hospital-Management.jpg", img, defaultOptions);
                    loader.displayImage(globalClass.getImageBaseUrl() + objBranch.getString("img_url"), img, defaultOptions);
                }
                rr.addView(v);
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("M", "tv " + tv.getText());
                        Bundle bundle = new Bundle();
                        bundle.putString("from", from);
                        bundle.putString("branch_id", branch_id);
                        fragment = new FragK_Dept();
                        fragment.setArguments(bundle);
                        fragmentManager = getFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frame, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

 /*   public void hospitalinfo0() {
        //String android_id = Settings.Secure.getString(getActivity().getContentResolver(),Settings.Secure.ANDROID_ID);
        if (globalClass.connectionAvailable()) {
            pb_intro.setVisibility(View.VISIBLE);
            String url = globalClass.getBaseUrl()+ "api/hospitalinfo";
            AsyncHttpClient client = new AsyncHttpClient();
            Log.d(TAG, "info "+ url);
            client.get(url, null, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                    pb_intro.setVisibility(View.GONE);
                    if (response != null) {
                        try {
                            String status = response.getString("status");
                            Log.d(TAG, "info "+ response.toString());
                            if (status.equals("1")) {
                                JSONArray obj_message = response.getJSONArray("message");
                               // String about = obj_message.getJSONObject(0).getString("about");

                                obj_branches = obj_message.getJSONObject(0).getJSONArray("branches");
                                for(int i=0;i<obj_branches.length();i++){
                                    JSONObject objBranch = obj_branches.getJSONObject(i);
                                    HashMap<String , ImageView> map = new HashMap<>();
                                    final View v = layoutInflater.inflate(R.layout.inflate_layout, null);
                                    final String branch_id = objBranch.getString("id");
                                    final TextView tv = (TextView)v.findViewById(R.id.branch_name);
                                    tv.setText(objBranch.getString("name"));
                                    final ImageView img = (ImageView)v.findViewById(R.id.branch_img);
                                    map.put(i+"" , img);
                                    if(!objBranch.getString("img_url").equals(""))
                                    {
                                        //loader.displayImage("http://after12thwhat.com/wp-content/uploads/2016/01/Hospital-Management.jpg", img, defaultOptions);
                                        loader.displayImage(globalClass.getImageBaseUrl()+objBranch.getString("img_url"), img , defaultOptions);
                                    }
                                    rr.addView(v);
                                    img.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Log.d("M" , "tv "+ tv.getText());
                                            Bundle bundle =new Bundle();
                                            bundle.putString("from","info");
                                            bundle.putString("branch_id",branch_id);
                                            fragment=new FragK_Dept();
                                            fragment.setArguments(bundle);
                                            fragmentManager=getFragmentManager();
                                            fragmentTransaction=fragmentManager.beginTransaction();
                                            fragmentTransaction.replace(R.id.frame,fragment);
                                            fragmentTransaction.addToBackStack(null);
                                            fragmentTransaction.commit();
                                        }
                                    });
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
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
        }
    }*/
}