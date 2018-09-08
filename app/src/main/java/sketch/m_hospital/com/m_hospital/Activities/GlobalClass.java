package sketch.m_hospital.com.m_hospital.Activities;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.NavigationView;
import android.support.multidex.MultiDexApplication;
import android.support.v7.widget.Toolbar;

import com.baidu.mapapi.SDKInitializer;

import org.json.JSONArray;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import sketch.m_hospital.com.m_hospital.Models.Branch;

/**
 * Created by Jonny on 7/23/2017.
 */

public class GlobalClass extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(this);
    }

    String BaseUrl="http://lab-5.sketchdemos.com/PHP-WEB-SERVICES/P-930-MHospital/";
    String ImageBaseUrl ="http://lab-5.sketchdemos.com/PHP-WEB-SERVICES/P-930-MHospital/assets/uploads/files/";
    String reportBaseUrl = "http://lab-5.sketchdemos.com/PHP-WEB-SERVICES/P-930-MHospital/assets/reports_pdf/";

    public String notification , hospital_intro;
    Toolbar toolbar;
    public String user_name;
    public String mobile;
    public String id;

    public String getDayCalendar() {
        return dayCalendar;
    }

    public void setDayCalendar(String dayCalendar) {
        this.dayCalendar = dayCalendar;
    }

    public String dayCalendar;

    public String getPackage_id() {
        return package_id;
    }

    public void setPackage_id(String package_id) {
        this.package_id = package_id;
    }

    public String package_id;
    public static Bitmap cropped_Picture ;
    public static Bitmap profile_Picture ;
    String pic_extension ;
    File pic_path;
    ArrayList<HashMap<String, String>> arrMonday = new ArrayList<>();
    ArrayList<HashMap<String, String>> arrTuesday = new ArrayList<>();
    ArrayList<HashMap<String, String>> arrWednesday = new ArrayList<>();
    ArrayList<HashMap<String, String>> arrThursday = new ArrayList<>();
    ArrayList<HashMap<String, String>> arrFriday = new ArrayList<>();
    ArrayList<HashMap<String, String>> arrSaturday = new ArrayList<>();
    ArrayList<HashMap<String, String>> arrSunday = new ArrayList<>();
    //ArrayList<HashMap<String, String>> arrMessage = new ArrayList<>();
    ArrayList<Branch> arrBranch = new ArrayList<>();
    public String emergency_number;
    public String gbranch_id, gdept_id , gsubdept_id , qbranch_name , qbranch_lat , qbranch_lon , qdept_name , qsubdept_name,
    qdoctor_name , qdate_time , qfee;
    public JSONArray branch_jArray;
    public String drawer_item;

    public NavigationView getNavigationView() {
        return navigationView;
    }

    public void setNavigationView(NavigationView navigationView) {
        this.navigationView = navigationView;
    }

    NavigationView navigationView;


    public boolean connectionAvailable()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }



    ArrayList<HashMap<String, String>> arrPossiblities = new ArrayList<>();

    public ArrayList<HashMap<String, String>> getArrPossiblities() {
        return arrPossiblities;
    }

    public void setArrPossiblities(ArrayList<HashMap<String, String>> arrPossiblities) {
        this.arrPossiblities = arrPossiblities;
    }
    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBaseUrl() {
        return BaseUrl;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public static Bitmap getCropped_Picture() {
        return cropped_Picture;
    }

    public static void setCropped_Picture(Bitmap cropped_Picture) {
        GlobalClass.cropped_Picture = cropped_Picture;
    }

    public static Bitmap getProfile_Picture() {
        return profile_Picture;
    }

    public static void setProfile_Picture(Bitmap profile_Picture) {
        GlobalClass.profile_Picture = profile_Picture;
    }

    public String getPic_extension() {
        return pic_extension;
    }

    public void setPic_extension(String pic_extension) {
        this.pic_extension = pic_extension;
    }

    public File getPic_path() {
        return pic_path;
    }

    public void setPic_path(File pic_path) {
        this.pic_path = pic_path;
    }

    public String getImageBaseUrl() {
        return ImageBaseUrl;
    }

    public ArrayList<HashMap<String, String>> getArrMonday() {
        return arrMonday;
    }

    public void setArrMonday(ArrayList<HashMap<String, String>> arrMonday) {
        this.arrMonday = arrMonday;
    }

    public ArrayList<HashMap<String, String>> getArrTuesday() {
        return arrTuesday;
    }

    public void setArrTuesday(ArrayList<HashMap<String, String>> arrTuesday) {
        this.arrTuesday = arrTuesday;
    }

    public ArrayList<HashMap<String, String>> getArrWednesday() {
        return arrWednesday;
    }

    public void setArrWednesday(ArrayList<HashMap<String, String>> arrWednesday) {
        this.arrWednesday = arrWednesday;
    }

    public ArrayList<HashMap<String, String>> getArrThursday() {
        return arrThursday;
    }

    public void setArrThursday(ArrayList<HashMap<String, String>> arrThursday) {
        this.arrThursday = arrThursday;
    }

    public ArrayList<HashMap<String, String>> getArrFriday() {
        return arrFriday;
    }

    public void setArrFriday(ArrayList<HashMap<String, String>> arrFriday) {
        this.arrFriday = arrFriday;
    }

    public ArrayList<HashMap<String, String>> getArrSaturday() {
        return arrSaturday;
    }

    public void setArrSaturday(ArrayList<HashMap<String, String>> arrSaturday) {
        this.arrSaturday = arrSaturday;
    }

    public ArrayList<HashMap<String, String>> getArrSunday() {
        return arrSunday;
    }

    public void setArrSunday(ArrayList<HashMap<String, String>> arrSunday) {
        this.arrSunday = arrSunday;
    }

    public String getEmergency_number() {
        return emergency_number;
    }

    public void setEmergency_number(String emergency_number) {
        this.emergency_number = emergency_number;
    }

   /* public ArrayList<HashMap<String, String>> getArrMessage() {
        return arrMessage;
    }

    public void setArrMessage(ArrayList<HashMap<String, String>> arrMessage) {
        this.arrMessage = arrMessage;
    }*/

    public ArrayList<Branch> getArrBranch() {
        return arrBranch;
    }

    public void setArrBranch(ArrayList<Branch> arrBranch) {
        this.arrBranch = arrBranch;
    }

    public String getGbranch_id() {
        return gbranch_id;
    }

    public void setGbranch_id(String gbranch_id) {
        this.gbranch_id = gbranch_id;
    }

    public String getGdept_id() {
        return gdept_id;
    }

    public void setGdept_id(String gdept_id) {
        this.gdept_id = gdept_id;
    }

    public String getGsubdept_id() {
        return gsubdept_id;
    }

    public void setGsubdept_id(String gsubdept_id) {
        this.gsubdept_id = gsubdept_id;
    }

    public String getQbranch_name() {
        return qbranch_name;
    }

    public void setQbranch_name(String qbranch_name) {
        this.qbranch_name = qbranch_name;
    }

    public String getQbranch_lat() {
        return qbranch_lat;
    }

    public void setQbranch_lat(String qbranch_lat) {
        this.qbranch_lat = qbranch_lat;
    }

    public String getQbranch_lon() {
        return qbranch_lon;
    }

    public void setQbranch_lon(String qbranch_lon) {
        this.qbranch_lon = qbranch_lon;
    }

    public String getQdept_name() {
        return qdept_name;
    }

    public void setQdept_name(String qdept_name) {
        this.qdept_name = qdept_name;
    }

    public String getQsubdept_name() {
        return qsubdept_name;
    }

    public void setQsubdept_name(String qsubdept_name) {
        this.qsubdept_name = qsubdept_name;
    }

    public String getReportBaseUrl() {
        return reportBaseUrl;
    }

    public String getHospital_intro() {
        return hospital_intro;
    }

    public void setHospital_intro(String hospital_intro) {
        this.hospital_intro = hospital_intro;
    }

    public JSONArray getBranch_jArray() {
        return branch_jArray;
    }

    public void setBranch_jArray(JSONArray branch_jArray) {
        this.branch_jArray = branch_jArray;
    }

    public String getQdoctor_name() {
        return qdoctor_name;
    }

    public void setQdoctor_name(String qdoctor_name) {
        this.qdoctor_name = qdoctor_name;
    }

    public String getQdate_time() {
        return qdate_time;
    }

    public void setQdate_time(String qdate_time) {
        this.qdate_time = qdate_time;
    }

    public String getQfee() {
        return qfee;
    }

    public void setQfee(String qfee) {
        this.qfee = qfee;
    }

    public String getDrawer_item() {
        return drawer_item;
    }

    public void setDrawer_item(String drawer_item) {
        this.drawer_item = drawer_item;
    }
}
