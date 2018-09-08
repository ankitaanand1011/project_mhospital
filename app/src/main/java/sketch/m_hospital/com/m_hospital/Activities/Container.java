package sketch.m_hospital.com.m_hospital.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;
import org.json.JSONArray;
import org.json.JSONObject;
import sketch.m_hospital.com.m_hospital.Fragments.FragA_Home;
import sketch.m_hospital.com.m_hospital.Fragments.FragB_Message;
import sketch.m_hospital.com.m_hospital.Fragments.FragC_Hospital_Info;
import sketch.m_hospital.com.m_hospital.Fragments.FragD_Book_Your_Doctor;
import sketch.m_hospital.com.m_hospital.Fragments.FragE_Package;
import sketch.m_hospital.com.m_hospital.Fragments.FragF_Symptoms_Checker;
import sketch.m_hospital.com.m_hospital.Fragments.FragG_Health_Encyclopedia;
import sketch.m_hospital.com.m_hospital.Fragments.FragH_Health_Blog;
import sketch.m_hospital.com.m_hospital.Fragments.FragI_Patient_Dashboard;
import sketch.m_hospital.com.m_hospital.Fragments.FragJ_Hospital_360_View;
import sketch.m_hospital.com.m_hospital.R;


public class Container extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Fragment fragment = null;
    FragmentManager fragmentManager;
    GlobalClass globalClass;
    LinearLayout lin1, lin2, lin3, lin4;
    FragmentTransaction fragmentTransaction;
    TextView tooltext;
    MyPreferences prefs;
    String TAG = "M";
    Menu menu;
    MenuItem hospital_information,book_doctor,Health_blog,symptom_checker,Encyclo,book_package,Message,viewr;
    NavigationView navigationView;
    ImageView ic_logout;
    private static final int REQUEST_PHONE_CALL = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.container);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        /*toolbar.getNavigationIcon().setColorFilter(Color.DKGRAY, PorterDuff.Mode.MULTIPLY);
        toolbar.setNavigationIcon(getResources().getColor(R.color.text_color));*/

        globalClass = (GlobalClass) getApplication();
        globalClass.setToolbar(toolbar);
        prefs = new MyPreferences(this);
        tooltext = (TextView) findViewById(R.id.text_toolbar);

        navigationView = (NavigationView) findViewById(R.id.nav_view);

        globalClass.setNavigationView(navigationView);

        menu=navigationView.getMenu();
        hospital_information=menu.findItem(R.id.hospital_information);
        book_doctor=menu.findItem(R.id.book_doctor);
        book_package=menu.findItem(R.id.book_package);
        symptom_checker=menu.findItem(R.id.system_checker);
        Encyclo=menu.findItem(R.id.health_encyclopedia);
        Health_blog=menu.findItem(R.id.health_blog);
        viewr=menu.findItem(R.id.hospital_view);
        Message=menu.findItem(R.id.message);




        View header = navigationView.getHeaderView(0);
        TextView version = header.findViewById(R.id.version);
        version.setText("Version "+getAppVersionName());

        ic_logout = toolbar.findViewById(R.id.ic_logout);

        lin1 = (LinearLayout) findViewById(R.id.Lin1);
        lin2 = (LinearLayout) findViewById(R.id.Lin2);
        lin3 = (LinearLayout) findViewById(R.id.Lin3);
        lin4 = (LinearLayout) findViewById(R.id.Lin4);


        Fragment fragment = new FragA_Home();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);


        lin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewr.setChecked(true);
                clearBackStack();
                ic_logout.setVisibility(View.VISIBLE);
                tooltext.setText(getString(R.string.Hospital360View));
                tooltext.setVisibility(View.VISIBLE);
                Fragment fragment = new FragJ_Hospital_360_View();
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        lin4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message.setChecked(true);
                clearBackStack();
                ic_logout.setVisibility(View.GONE);
                Fragment fragment = new FragB_Message();
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        lin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearBackStack();
                book_doctor.setChecked(true);
                ic_logout.setVisibility(View.VISIBLE);
                Fragment fragment = new FragD_Book_Your_Doctor();
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        lin3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                book_package.setChecked(true);
                Bundle bundle=new Bundle();
                Log.d("HOSPITAL","package_id@container"+globalClass.getPackage_id());
                bundle.putString("pkg_id",globalClass.getPackage_id());
                clearBackStack();
                ic_logout.setVisibility(View.VISIBLE);
                Fragment fragment = new FragE_Package();
                fragmentManager = getSupportFragmentManager();
                fragment.setArguments(bundle);
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        //healthencyclopaedia();
        emergencyNumber();
        //getAlphabetData();


        ic_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefs.setPref_login_status(false);
                prefs.setPrefuserid(null);

                startActivity(new Intent(Container.this, LoginScreen.class)
                        .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
            }
        });

    }


  /*  @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {

            clearBackStack();
            ic_logout.setVisibility(View.VISIBLE);
            fragment = new FragA_Home();
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();


        } else if (id == R.id.message) {

            clearBackStack();
            ic_logout.setVisibility(View.GONE);
            fragment = new FragB_Message();
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();


        } else if (id == R.id.hospital_information) {
            clearBackStack();
            ic_logout.setVisibility(View.VISIBLE);
            fragment = new FragC_Hospital_Info();
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();


        } else if (id == R.id.book_doctor) {

            clearBackStack();
            ic_logout.setVisibility(View.VISIBLE);
            fragment = new FragD_Book_Your_Doctor();
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        } else if (id == R.id.book_package) {

            clearBackStack();
            ic_logout.setVisibility(View.VISIBLE);
            fragment = new FragE_Package();
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        } else if (id == R.id.system_checker) {

            clearBackStack();
            ic_logout.setVisibility(View.VISIBLE);
            fragment = new FragF_Symptoms_Checker();
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        } else if (id == R.id.health_encyclopedia) {

            clearBackStack();
            ic_logout.setVisibility(View.VISIBLE);
            fragment = new FragG_Health_Encyclopedia();
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        } else if (id == R.id.health_blog) {

            clearBackStack();
            ic_logout.setVisibility(View.VISIBLE);
            fragment = new FragH_Health_Blog();
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        } else if (id == R.id.patient_dashboard) {

            clearBackStack();
            ic_logout.setVisibility(View.VISIBLE);
            fragment = new FragI_Patient_Dashboard();
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        } else if (id == R.id.hospital_view) {
            clearBackStack();
            ic_logout.setVisibility(View.VISIBLE);
            tooltext.setText(getString(R.string.Hospital360View));
            tooltext.setVisibility(View.VISIBLE);
            fragment = new FragJ_Hospital_360_View();
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            // startActivity(new Intent(this,Youtube_Activity.class));
        } else if (id == R.id.emergency) {
           /* Uri number = Uri.parse("tel:"+199);
            Intent callIntent = new Intent(Intent.ACTION_CALL, number);
            startActivity(callIntent);*/
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + globalClass.getEmergency_number()));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(Container.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Container.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                } else {
                    startActivity(intent);
                }
            } else {
                startActivity(intent);
            }
            /////
         /*   int checkPermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE);
            if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{android.Manifest.permission.CALL_PHONE},
                        111);
            } else {

            }*/
        } else if (id == R.id.logout) {
            prefs.setPref_login_status(false);
            prefs.setPrefuserid(null);

            startActivity(new Intent(Container.this, LoginScreen.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));

        }
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);




        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case REQUEST_PHONE_CALL: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + globalClass.getEmergency_number()));

                   /* Uri number = Uri.parse("tel:" + 199);
                    Intent callIntent = new Intent(Intent.ACTION_CALL, number);*/
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }else {
                        startActivity(intent);
                    }
                }
                return;
            }
        }
    }


    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {


            new AlertDialog.Builder(Container.this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(getString(R.string.ExitAppp))
                    .setMessage(getString(R.string.AreExitApp))

                    .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }

                    })
                    .setNegativeButton(getString(R.string.no), null)
                    .show();
        }
    }


    private void clearBackStack() {
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
            manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    public void emergencyNumber() {
        if (globalClass.connectionAvailable()) {
            String url = globalClass.getBaseUrl()+ "api/emergencynumber";
            AsyncHttpClient client = new AsyncHttpClient();
            Log.d(TAG, "info "+ url);
            client.get(url, null, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                    if (response != null) {
                        try {
                            String status = response.getString("status");
                            Log.d(TAG, "info "+ response.toString());
                            if (status.equals("1")) {
                                JSONArray obj_message = response.getJSONArray("message");
                                String contact = obj_message.getJSONObject(0).getString("contact");
                                globalClass.setEmergency_number(contact);
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
            new LovelyStandardDialog(Container.this)
                    .setTopColorRes(R.color.colorAccent)
                    .setButtonsColorRes(R.color.colorPrimaryDark)
                    .setIcon(R.mipmap.ic_white_cross)
                    .setTitle(R.string.internet_connection)
                    .setMessage(R.string.no_connection)
                    .setPositiveButton(R.string.ok, null)
                    .show();
        }
    }


   /* public static List<String> getAlphabetData()
    {
        Log.d("tag","getAlphabetData-is-called");
        Log.d("tag","list--"+list);

        return list;
    }*/

   /* public void healthencyclopaedia()
    {
        list.clear();
        String url="http://lab-5.sketchdemos.com/PHP-WEB-SERVICES/P-930-MHospital/api/healthencyclopedia";
        AsyncHttpClient asyncHttpClient=new AsyncHttpClient();
        final RequestParams requestParams=new RequestParams();
        Log.d(TAG,"url"+url);
        asyncHttpClient.get(url,requestParams,new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Log.d(TAG,"res---"+response.toString());
                progressDialog.dismiss();
                if(response!=null)
                {
                    try {

                        String status=response.optString("status");
                        if(status.equals("1"));
                        {
                            Log.d(TAG,"status is 1");
                            JSONArray msgar=response.getJSONArray("message");
                            Log.d(TAG,"msgar.length"+msgar.length());
                            for(int i=0;i<msgar.length();i++) {
                                JSONObject msgobj = msgar.getJSONObject(i);


                                disease_id = msgobj.optString("disease_id");
                                disease_name = msgobj.optString("disease_name");
                                Log.d(TAG,"disease-name-jsonloop"+disease_name);
                                disease_image = msgobj.optString("disease_image");
                                disease_details = msgobj.optString("disease_details");
                                doctor_name = msgobj.optString("doctor_name");
                                doctor_branch = msgobj.optString("doctor_branch");
                                doctor_dept = msgobj.optString("doctor_dept");
                                doctor_sub_dept = msgobj.optString("doctor_sub_dept");
                                doctor_image = msgobj.optString("doctor_image");
                                list.add(disease_name);

//                                list.add(disease_name+"\t"+"id  "+disease_id);
//                                list.add(disease_name+"\t"+"details "+disease_details);
//                                list.add(disease_name+"\t"+"doctor_branch  "+doctor_branch);
//                                list.add(disease_name+"\t"+"doctor_dept  "+doctor_dept);
//                                list.add(disease_name+"\t"+"doctor_sub_dept  "+doctor_sub_dept);
//                                list.add(disease_name+"\t"+"doctor_name  "+doctor_name);
//


                                HashMap<String,String> map=new HashMap<String, String>();
                                map.put("disease_id",disease_id);
                                map.put("disease_name",disease_name);
                                map.put("disease_details",disease_details);
                                map.put("doctor_name",doctor_name);
                                map.put("doctor_branch",doctor_branch);
                                map.put("doctor_dept",doctor_dept);
                                map.put("doctor_sub_dept",doctor_sub_dept);
                                // map.put("doctor_image",doctor_image);
                                arrayList.add(map);

                            }
                            Log.d(TAG,"list-before-sort"+list);
                            Collections.sort(list);
                            Log.d(TAG,"list-after-sort"+list);
                        }
                    }catch(JSONException je)
                    {

                    }
                }
                Log.d(TAG,"disease-list"+list+arrayList.size());

//
//                Log.d(TAG,"disease_id"+disease_id);
//                Log.d(TAG,"disease_name"+disease_name);
//                Log.d(TAG,"disease_image"+disease_image);
//                Log.d(TAG,"disease_details"+disease_details);
//                Log.d(TAG,"doctor_branch"+doctor_branch);
//                Log.d(TAG,"doctor_dept"+doctor_dept);
//                Log.d(TAG,"doctor_sub_dept"+doctor_sub_dept);
//                Log.d(TAG,"doctor_image"+doctor_image);
//                Log.d(TAG,"elements of next object");

            }


            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d(TAG,"Server Error");
                progressDialog.dismiss();

            }
        });

    }*/

 /*   @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(myReceiver, intentFilter);

    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myReceiver);
    }
*/

   /* @Override
    protected void onResume() {
        super.onResume();
        Log.d("msg", "onres: "+globalClass.getArrMessage().toString());
        prefs.loadArraymsg(globalClass.getArrMessage());
        Log.d("msg", "onres2: "+globalClass.getArrMessage().toString());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("msg", "onpau: "+globalClass.getArrMessage().toString());
        prefs.saveArraymsg(globalClass.getArrMessage());
        Log.d("msg", "onpau2: "+globalClass.getArrMessage().toString());
    }*/

    public String getAppVersionName(){
        String version=null;
        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            version = pInfo.versionName;
            int code = pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }
}
