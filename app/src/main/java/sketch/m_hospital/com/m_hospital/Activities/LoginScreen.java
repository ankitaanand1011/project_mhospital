package sketch.m_hospital.com.m_hospital.Activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import org.json.JSONObject;

import me.pushy.sdk.Pushy;
import sketch.m_hospital.com.m_hospital.R;

/**
 * Created by Developer on 6/1/17.
 */

public class LoginScreen extends AppCompatActivity {

    EditText editText_usernames,editText_passwords;
    TextView text_login,text_no_accounts,forgot_password, text_sign_up;
    ProgressDialog progressDialog;
    GlobalClass globalClass;
    String TAG = "M" , fcmtoke;
    MyPreferences prefs;
    ImageView leftarrow;
    String refreshedToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.b_login_screen);
        text_login = (TextView) findViewById(R.id.text_login);
        text_no_accounts = (TextView) findViewById(R.id.text_no_account);
        text_sign_up = (TextView) findViewById(R.id.text_sign_up);
        globalClass = (GlobalClass) getApplicationContext();
        prefs = new MyPreferences(this);
        progressDialog = new ProgressDialog(this);
        if (progressDialog.isShowing())
            progressDialog.dismiss();
        editText_passwords = (EditText) findViewById(R.id.editText_password);
        editText_usernames = (EditText) findViewById(R.id.editText_username);
        forgot_password = (TextView) findViewById(R.id.text_frgt_pwd);
        leftarrow = (ImageView) findViewById(R.id.image_arrow);

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginScreen.this, ForgotPassword.class);
                startActivity(intent);
            }
        });

        text_no_accounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_no_account = new Intent(LoginScreen.this, SignupScreen.class);
                startActivity(intent_no_account);
            }
        });

        text_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_no_account = new Intent(LoginScreen.this, SignupScreen.class);
                startActivity(intent_no_account);
            }
        });

        text_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText_usernames.getText().toString().isEmpty()) {
                    editText_usernames.setError(getString(R.string.enter_phone_number));
                    editText_usernames.requestFocus();
                } else if (editText_passwords.getText().toString().isEmpty()) {
                    editText_passwords.setError(getString(R.string.enter_password));
                    editText_passwords.requestFocus();
                } else {
                    LoginTask();
                }
            }
        });

        leftarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginScreen.this,SplashScreen.class);
                startActivity(intent);
                finish();
                Intent launchNextActivity;
                launchNextActivity = new Intent(LoginScreen.this,SplashScreen.class);
                launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(launchNextActivity);

            }
        });
        isStoragePermissionGranted();
    }


    public void LoginTask() {
        String android_id = Settings.Secure.getString(this.getContentResolver(),Settings.Secure.ANDROID_ID);
        if (globalClass.connectionAvailable()) {
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.show();
            String url = globalClass.getBaseUrl()+ "api/login";
            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            params.put("mobile", editText_usernames.getText().toString());
            params.put("password", editText_passwords.getText().toString());
            params.put("deviceid", android_id);
            params.put("fcm_reg_token",refreshedToken );
            Log.d(TAG, "login "+ url);
            Log.d(TAG, "login "+params.toString());
            client.post(url, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    if (response != null) {
                        try {
                            String status = response.getString("status");
                            Log.d(TAG, "login "+ response.toString());
                            if (status.equals("1")) {
                                JSONObject user=response.getJSONObject("user");
                                String name=user.optString("name");
                                String mobile=user.optString("mobile");
                                String id=user.optString("id");
                                globalClass.setId(id);
                                globalClass.setMobile(mobile);
                                globalClass.setUser_name(name);
                                prefs.setPref_login_status(true);
                                prefs.setPrefuserid(id);
                                prefs.setPref_password(editText_passwords.getText().toString());
                                prefs.setPref_user_name(name);
                                Intent intent = new Intent(LoginScreen.this, Container.class);
                                startActivity(intent);
                                finish();
                            } else if(status.equals("2")){
                                new LovelyStandardDialog(LoginScreen.this)
                                        .setTopColorRes(R.color.colorAccent)
                                        .setButtonsColorRes(R.color.colorPrimaryDark)
                                        .setIcon(R.mipmap.ic_white_cross)
                                        .setTitle(R.string.login_failed)
                                        .setMessage(R.string.mobile_not_registered)
                                        .setPositiveButton(R.string.ok, null)
                                        .show();
                            } else if(status.equals("4")){
                                new LovelyStandardDialog(LoginScreen.this)
                                        .setTopColorRes(R.color.colorAccent)
                                        .setButtonsColorRes(R.color.colorPrimaryDark)
                                        .setIcon(R.mipmap.ic_white_cross)
                                        .setTitle(R.string.login_failed)
                                        .setMessage(R.string.wrong_password)
                                        .setPositiveButton(R.string.ok, null)
                                        .show();
                            }else{
                                new LovelyStandardDialog(LoginScreen.this)
                                        .setTopColorRes(R.color.colorAccent)
                                        .setButtonsColorRes(R.color.colorPrimaryDark)
                                        .setIcon(R.mipmap.ic_white_cross)
                                        .setTitle(R.string.login_failed)
                                        .setMessage(R.string.something_went_wrong)
                                        .setPositiveButton(R.string.ok, null)
                                        .show();
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
            new LovelyStandardDialog(LoginScreen.this)
                    .setTopColorRes(R.color.colorAccent)
                    .setButtonsColorRes(R.color.colorPrimaryDark)
                    .setIcon(R.mipmap.ic_white_cross)
                    .setTitle(R.string.internet_connection)
                    .setMessage(R.string.no_connection)
                    .setPositiveButton(R.string.ok, null)
                    .show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent launchNextActivity;
        launchNextActivity = new Intent(LoginScreen.this,SplashScreen.class);
        launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(launchNextActivity);

    }

  /*  public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_CONTACTS)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            //resume tasks needing this permission
        }
    }*/

    public  void isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
                    builder.setTitle(getString(R.string.gallery_access));
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setMessage(getString(R.string.gallery_access_prevent));//TODO put real question
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @TargetApi(Build.VERSION_CODES.M)
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

                        }
                    });
                    builder.show();
                } else {
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }
            }else{
                try {
                    new LoginScreen.RegisterForPushNotificationsAsync().execute();
                }
                catch (Exception e) {
                    // TODO Auto-generated catch block
                }
            }
        }
        else{
            try {
            }
            catch (Exception e) {
                // TODO Auto-generated catch block
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        new LoginScreen.RegisterForPushNotificationsAsync().execute();
                    }
                    catch (Exception e) {
                        // TODO Auto-generated catch block
                    }
                } else {
                }
                return;
            }
        }
    }

    private class RegisterForPushNotificationsAsync extends AsyncTask<Void, Void, Exception> {
        protected Exception doInBackground(Void... params) {
            try {
                // Assign a unique token to this device
                refreshedToken = Pushy.register(getApplicationContext());
                prefs.setPref_fcm(refreshedToken);
                // Log it for debugging purposes
                Log.d("MyApp", "Pushy device token: " + refreshedToken);

                // Send the token to your backend server via an HTTP GET request
                //new URL("https://{YOUR_API_HOSTNAME}/register/device?token=" + deviceToken).openConnection();
            }
            catch (Exception exc) {
                // Return exc to onPostExecute
                return exc;
            }

            // Success
            return null;
        }

        @Override
        protected void onPostExecute(Exception exc) {
            // Failed?
            if (exc != null) {
                // Show error as toast message
                Toast.makeText(getApplicationContext(), exc.toString(), Toast.LENGTH_LONG).show();
                return;
            }

            // Succeeded, do something to alert the user
        }
    }
}
