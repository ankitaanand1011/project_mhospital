package sketch.m_hospital.com.m_hospital.Activities;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by nazia on 10-07-2017.
 */

public class MyPreferences
{
    public static final String PREFS_NAME = "Shoffers_Pref";
    public static final String PREFS_login = "login";
    public boolean pref_login_status ;
    public static final String PREFS_userid = "userid";
    public String pref_userid ;
    public static final String PREFS_user_name = "user_name";
    public String pref_user_name ;
    public static final String PREFS_fcm = "fcm";
    public String pref_fcm ;
    public static final String PREFS_password = "password";
    public String pref_password ;
    public static final String PREFS_lang = "lang";
    public String pref_lang ;

    public SharedPreferences settings;
    public SharedPreferences.Editor editor;

    public MyPreferences(Context context){
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
    }

    public  boolean getPref_login_status() {
        pref_login_status= settings.getBoolean(PREFS_login , false);
        return pref_login_status;
    }

    public void setPref_login_status(boolean pref_login_status) {
        this.pref_login_status = pref_login_status;
        editor.putBoolean(PREFS_login,pref_login_status);
        editor.commit();
    }

    public void setPrefuserid(String pref_userid){
        this.pref_userid = pref_userid;
        editor.putString(PREFS_userid,pref_userid);
        editor.commit();
    }

    public String getPREFS_userid() {
        pref_userid= settings.getString(PREFS_userid , null);
        return pref_userid;
    }

    public String getPref_fcm() {
        pref_fcm= settings.getString(PREFS_fcm , null);
        return pref_fcm;
    }

    public void setPref_fcm(String pref_fcm) {
        this.pref_fcm = pref_fcm;
        editor.putString(PREFS_fcm,pref_fcm);
        editor.commit();
    }

    public String getPref_password() {
        pref_password= settings.getString(PREFS_password , null);
        return pref_password;
    }

    public void setPref_password(String pref_password) {
        this.pref_password = pref_password;
        editor.putString(PREFS_password,pref_password);
        editor.commit();
    }

    public String getPref_user_name() {
        pref_user_name= settings.getString(PREFS_user_name , null);
        return pref_user_name;
    }

    public void setPref_user_name(String pref_user_name) {
        this.pref_user_name = pref_user_name;
        editor.putString(PREFS_user_name,pref_user_name);
        editor.commit();
    }

    public String getPref_lang() {
        pref_lang= settings.getString(PREFS_lang, null);
        return pref_lang;
    }

    public void setPref_lang(String pref_lang) {
        this.pref_lang = pref_lang;
        editor.putString(PREFS_lang,pref_lang);
        editor.commit();
    }






    public void saveArraymsg(ArrayList<HashMap<String, String>> arrMessage)
    {
        editor.putInt("Status_size", arrMessage.size());

        for(int i=0;i<arrMessage.size();i++)
        {
            editor.remove("message" + i);
            editor.putString("message" + i, arrMessage.get(i).get("message"));

            editor.remove("time" + i);
            editor.putString("time" + i, arrMessage.get(i).get("time"));
        }
        editor.commit();
    }

    public void loadArraymsg(ArrayList<HashMap<String, String>> arrMessage)
    {
        arrMessage.clear();
        int size = settings.getInt("Status_size", 0);

        for(int i=0;i<size;i++)
        {
            HashMap<String , String> map = new HashMap<>();
            map.put("message" ,settings.getString("message" + i, null) );
            map.put("time" ,settings.getString("time" + i, null));
            arrMessage.add(map);
        }

    }

}
