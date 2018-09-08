package sketch.m_hospital.com.m_hospital.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import java.util.Locale;
import sketch.m_hospital.com.m_hospital.R;

/**
 * Created by Developer on 6/1/17.
 */

public class SplashScreen extends AppCompatActivity {

    String TAG="M";
    MyPreferences prefs;
    GlobalClass globalClass;
    Button button_logs,button_signs;
    TextView tv_english , tv_chinese ;

    public  void  onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        prefs = new MyPreferences(this);
        globalClass = (GlobalClass) getApplicationContext();
        String lang = Locale.getDefault().getDisplayLanguage();
        Log.d(TAG , prefs.getPref_login_status()+" "+prefs.getPREFS_userid()+" lang "+lang);

        if(!prefs.getPref_login_status()){
            //if not logged in
            setContentView(R.layout.a_splash_screen);

            button_logs=(Button)findViewById(R.id.button_log);
            button_signs=(Button)findViewById(R.id.button_sign);
            tv_english =(TextView)findViewById(R.id.tv_english);
            tv_chinese =(TextView)findViewById(R.id.tv_chinese);
            button_logs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent=new Intent(SplashScreen.this,LoginScreen.class);
                    startActivity(intent);
                    finish();
                }
            });

            button_signs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(SplashScreen.this,SignupScreen.class);
                    startActivity(intent);
                    finish();
                }
            });

            if(lang.equals("English")){
                prefs.setPref_lang("English");
                tv_english.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
                tv_chinese.setTextColor(ContextCompat.getColor(this, R.color.black));
            }else{
                prefs.setPref_lang("Chinese");
                tv_chinese.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
                tv_english.setTextColor(ContextCompat.getColor(this, R.color.black));
            }

            tv_english.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    prefs.setPref_lang("English");
                    tv_english.setTextColor(ContextCompat.getColor(SplashScreen.this, R.color.colorAccent));
                    tv_chinese.setTextColor(ContextCompat.getColor(SplashScreen.this, R.color.black));
                    changeLang("en");
                }
            });

            tv_chinese.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    prefs.setPref_lang("Chinese");
                    tv_chinese.setTextColor(ContextCompat.getColor(SplashScreen.this, R.color.colorAccent));
                    tv_english.setTextColor(ContextCompat.getColor(SplashScreen.this, R.color.black));
                    changeLang("zh");
                }
            });
        }else{
            if(prefs.getPref_lang().equals("English")){
                changeLang("en");
            }else{
                changeLang("zh");
            }
            globalClass.setId(prefs.getPREFS_userid());
            globalClass.setUser_name(prefs.getPref_user_name());
            Intent i = new Intent(SplashScreen.this, Container.class);
            startActivity(i);
            finish();
        }

       // FirebaseApp.initializeApp(this);
        //String refreshedToken = FirebaseInstanceId.getInstance().getToken();


    }

    public void changeLang(String code){

        String languageToLoad  = code;
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        this.getResources().updateConfiguration(config,this.getResources().getDisplayMetrics());
        String lang = Locale.getDefault().getDisplayLanguage();
        Log.d(TAG ," lang "+lang);
        if(!prefs.getPref_login_status()) {
            button_logs.setText(R.string.login);
            button_signs.setText(R.string.sign_up);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
    @Override
    protected void onResume() {
        super.onResume();

    }



}
