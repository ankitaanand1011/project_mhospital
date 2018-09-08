package sketch.m_hospital.com.m_hospital.Fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import sketch.m_hospital.com.m_hospital.Activities.GlobalClass;
import sketch.m_hospital.com.m_hospital.Models.Branch;
import sketch.m_hospital.com.m_hospital.R;

public class FragA_Home extends Fragment{
    String TAG="M";
    RelativeLayout r1, r2, r3, r4, r5, r6;
    private static final int REQUEST_PHONE_CALL = 1;
    Fragment fragment=null;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    GlobalClass globalClass;
    public JSONArray obj_branches;
    private SliderLayout mDemoSlider;
    NavigationView navigationView;
    Menu menu;
    MenuItem hospital_information,book_doctor,Health_blog,symptom_checker,Encyclo,book_package;
    ProgressBar pb_intro;
    TextView grid_text,grid_text2,grid_text3,grid_text4,grid_text5,grid_text6;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("frag", "onCreateView: ");
        globalClass = (GlobalClass)getActivity().getApplication();

        View rootView = inflater.inflate(R.layout.frg_a_home, container, false);
        mDemoSlider = rootView.findViewById(R.id.slider);
        pb_intro = rootView.findViewById(R.id.pb_intro);
        pb_intro.setVisibility(View.GONE);
        TextView tooltext=getActivity().findViewById(R.id.text_toolbar);
        tooltext.setText(getString(R.string.app_name));
        navigationView=globalClass.getNavigationView();
        menu=navigationView.getMenu();

        hospital_information=menu.findItem(R.id.hospital_information);
        book_doctor=menu.findItem(R.id.book_doctor);
        book_package=menu.findItem(R.id.book_package);
        symptom_checker=menu.findItem(R.id.system_checker);
        Encyclo=menu.findItem(R.id.health_encyclopedia);
        Health_blog=menu.findItem(R.id.health_blog);

        grid_text = rootView.findViewById(R.id.grid_text);
        grid_text2 = rootView.findViewById(R.id.grid_text2);
        grid_text3 = rootView.findViewById(R.id.grid_text3);
        grid_text4 = rootView.findViewById(R.id.grid_text4);
        grid_text5 = rootView.findViewById(R.id.grid_text5);
        grid_text6 = rootView.findViewById(R.id.grid_text6);

        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(),  "fonts/Roboto-Regular_1.ttf");

        grid_text.setTypeface(custom_font);
        grid_text2.setTypeface(custom_font);
        grid_text3.setTypeface(custom_font);
        grid_text4.setTypeface(custom_font);
        grid_text5.setTypeface(custom_font);
        grid_text6.setTypeface(custom_font);

        r1 =  rootView.findViewById(R.id.Rel_left);
        r2 =  rootView.findViewById(R.id.Rel_right);
        r3 =  rootView.findViewById(R.id.Rel_rightmost);
        r4 =  rootView.findViewById(R.id.Rel_second);
        r5 =  rootView.findViewById(R.id.Rel_second_right);
        r6 =  rootView.findViewById(R.id.Rel_second_right_most);
        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hospital_information.setChecked(true);
                fragment=new FragC_Hospital_Info();
                fragmentManager=getFragmentManager();
                fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                book_doctor.setChecked(true);
                globalClass.setDrawer_item("book_your_doctor");
                fragment=new FragD_Book_Your_Doctor();
                fragmentManager=getFragmentManager();
                fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        r3.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        book_package.setChecked(true);
        fragment=new FragE_Package();
        fragmentManager=getFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
});
        r4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
symptom_checker.setChecked(true);
                fragment=new FragF_Symptoms_Checker();
                fragmentManager=getFragmentManager();
                fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        r5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Encyclo.setChecked(true);
                fragment=new FragG_Health_Encyclopedia();
                fragmentManager=getFragmentManager();
                fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        r6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Health_blog.setChecked(true);

                fragment=new FragH_Health_Blog();
                fragmentManager=getFragmentManager();
                fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

     //   bannerSlider = (BannerSlider) rootView.findViewById(R.id.banner_slider1);


        RelativeLayout button_emergency = (RelativeLayout) rootView.findViewById(R.id.button_emergency);
        RelativeLayout button_patient_dashboard = (RelativeLayout) rootView.findViewById(R.id.button_dashboard);
        button_patient_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragment=new FragI_Patient_Dashboard();
                fragmentManager=getFragmentManager();
                fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        button_emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + globalClass.getEmergency_number()));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                    } else {
                        startActivity(intent);
                    }
                } else {
                    startActivity(intent);
                }
            }
        });

        hospitalinfo();
        return rootView;
    }

  /*  public void call(){
        Uri number = Uri.parse("tel:"+199);
        Intent callIntent = new Intent(Intent.ACTION_CALL, number);
        startActivity(callIntent);

    }*/
  
    public View rows()
    {
        return  r2;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("frag", "onResume: ");
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        Log.d("frag", "onAttachFragment: ");
    }

    public JSONArray hospitalinfo() {
        pb_intro.setVisibility(View.VISIBLE);
        final ArrayList<Branch> arrayList = new ArrayList<>();
        if (globalClass.connectionAvailable()) {
            String url = globalClass.getBaseUrl()+ "api/hospitalinfo";
            AsyncHttpClient client = new AsyncHttpClient();
            Log.d(TAG, "info "+ url);
            client.get(url, null, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                    if (response != null) {
                        try {
                            pb_intro.setVisibility(View.GONE);
                            String status = response.getString("status");
                            Log.d(TAG, "info "+ response.toString());
                            if (status.equals("1")) {
                                JSONArray obj_message = response.getJSONArray("message");
                                String about = obj_message.getJSONObject(0).getString("about");
                                globalClass.setHospital_intro(about);
                                JSONArray obj_banner_images = obj_message.getJSONObject(0).getJSONArray("banner_images");
                                for(int i=0;i<obj_banner_images.length();i++) {
                                    {
                                        JSONObject objBranch1 = obj_banner_images.getJSONObject(i);
                                    //    bannerSlider.addBanner(new RemoteBanner(globalClass.getImageBaseUrl()+objBranch1.getString("img_url")));
                                        TextSliderView textSliderView = new TextSliderView(getActivity());
                                        textSliderView
                                                .image(globalClass.getImageBaseUrl()+objBranch1.getString("img_url"))
                                                .setScaleType(BaseSliderView.ScaleType.Fit);
                                        textSliderView.bundle(new Bundle());
                                        textSliderView.getBundle().putString("extra","M+Hospital");
                                        mDemoSlider.addSlider(textSliderView);
                                    }
                                }
                                mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
                                mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                                mDemoSlider.setCustomAnimation(new DescriptionAnimation());
                                mDemoSlider.setDuration(4000);
                              //  mDemoSlider.addOnPageChangeListener(FragA_Home.this);

                                //////////////////
                                obj_branches = obj_message.getJSONObject(0).getJSONArray("branches");
                                globalClass.setBranch_jArray(obj_branches);
                                for(int i=0;i<obj_branches.length();i++){
                                    JSONObject objBranch = obj_branches.getJSONObject(i);
                                    arrayList.add(new Branch(objBranch.getString("id"),objBranch.getString("main_hospital"),objBranch.getString("name"),
                                            objBranch.getString("img_url"),objBranch.getString("latitude"),objBranch.getString("longitude"),
                                            objBranch.getString("video_file"),objBranch.getString("video_type"),objBranch.getString("video_link")));
                                }
                                globalClass.setArrBranch(arrayList);
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
        return obj_branches;
    }

   /* public void getPictureFromURL(final BannerSlider ban , String image_url){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(image_url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Bitmap image = BitmapFactory.decodeByteArray(responseBody, 0, responseBody.length);
                ban.addBanner(new RemoteBanner(image_url));
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            }
        });
    }*/
}