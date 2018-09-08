package sketch.m_hospital.com.m_hospital.Activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;
import org.json.JSONObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import me.pushy.sdk.Pushy;
import sketch.m_hospital.com.m_hospital.R;
import sketch.m_hospital.com.m_hospital.Utils.ImageCropActivity;
import sketch.m_hospital.com.m_hospital.Utils.Utils;

/**
 * Created by Developer on 6/1/17.
 */

public class SignupScreen extends AppCompatActivity {

    TextView textView_signup;
    EditText editText_phonenumbers,editText_yournames,editText_repeatpasswords,editText_passwords, otp;
    ImageView leftarrow ;
    CircleImageView image_userprofile;
    String TAG="M" ,passwords,repeatpasswords;;
    GlobalClass globalClass;
    ProgressDialog progressDialog;
    Dialog dialogverify;
    public static final int GET_FROM_GALLERY = 3;
    Bitmap bitmap;
    private File mFileTemp;
    public static final String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";
    private String mImagePath;
    private Uri mImageUri = null;
    String from , path;
    public static final int REQUEST_CODE_UPDATE_PIC = 0x1;
    String refreshedToken;
    MyPreferences prefs;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Pushy.listen(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.b_signup_screen);
        prefs = new MyPreferences(this);

        globalClass =(GlobalClass)getApplicationContext();
        progressDialog=new ProgressDialog(this);
        if(progressDialog.isShowing())
            progressDialog.dismiss();
        refreshedToken = getString(R.string.yet_to_implement);
        editText_yournames =(EditText) findViewById(R.id.edt_yourname);
        editText_passwords =(EditText) findViewById(R.id.editText_password);
        editText_repeatpasswords =(EditText) findViewById(R.id.editText_Repeatpassword);
        editText_phonenumbers =(EditText) findViewById(R.id.editText_phonenumber);
        image_userprofile = (CircleImageView)findViewById(R.id.img_pic);
        textView_signup=(TextView)findViewById(R.id.text_signup);
        leftarrow=(ImageView)findViewById(R.id.img_left_arrow);

        image_userprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
            }
        });

        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mFileTemp = new File(Environment.getExternalStorageDirectory(), TEMP_PHOTO_FILE_NAME);
        } else {
            mFileTemp = new File(getFilesDir(), TEMP_PHOTO_FILE_NAME);
        }
        mImagePath = mFileTemp.getPath();

        mImageUri = Utils.getImageUri(mImagePath);
        if(globalClass.getProfile_Picture()!=null){
            image_userprofile.setImageBitmap(globalClass.getProfile_Picture());
        }
        from = getIntent().getStringExtra("secret");
        path = getIntent().getStringExtra("image_path");
        if(from != null) {
            if (from.equals(null) || from.equals("takephoto")) {
                image_userprofile.setImageBitmap(globalClass.getCropped_Picture());
            }}

        textView_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwords=editText_passwords.getText().toString();
                repeatpasswords=editText_repeatpasswords.getText().toString();
                String phonenum=editText_phonenumbers.getText().toString().trim();
                if(editText_yournames.getText().toString().isEmpty())
                {
                    editText_yournames.setError(getString(R.string.enter_name));
                    editText_yournames.requestFocus();
                }
                else if(editText_passwords.getText().toString().isEmpty())
                {
                    editText_passwords.setError(getString(R.string.enter_password));
                    editText_passwords.requestFocus();
                }
                else if(editText_passwords.getText().toString().length()<6) {
                    editText_passwords.setError(getString(R.string.password_length));
                    editText_passwords.requestFocus();
                }

                else if(editText_repeatpasswords.getText().toString().isEmpty())
                {
                    editText_repeatpasswords.setError(getString(R.string.renter_password));
                    editText_repeatpasswords.requestFocus();
                }
                else if((!passwords.equals(repeatpasswords)))
                {
                    editText_repeatpasswords.setError(getString(R.string.password_mismatch));
                    editText_repeatpasswords.requestFocus();
                }
                else if(phonenum.isEmpty())
                {
                    editText_phonenumbers.setError(getString(R.string.enter_phone_number));
                    editText_phonenumbers.requestFocus();
                }
                else if(phonenum.length()!=11)
                {
                    editText_phonenumbers.setError(getString(R.string.valid_phone_number));
                    editText_phonenumbers.requestFocus();
                }
                else{
                    Register();
                }
            }
        });

        leftarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupScreen.this, SplashScreen.class);
                startActivity(intent);
                finish();
                Intent launchNextActivity;
                launchNextActivity = new Intent(SignupScreen.this,SplashScreen.class);
                launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(launchNextActivity);
            }
        });
        isStoragePermissionGranted();
    }

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
                    new RegisterForPushNotificationsAsync().execute();
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
                        new RegisterForPushNotificationsAsync().execute();
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

 @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        super.onActivityResult(requestCode, resultCode, result);
        //Detects request codes
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = result.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                image_userprofile.setImageBitmap(bitmap);
                InputStream inputStream = getContentResolver().openInputStream(result.getData()); // Got the bitmap .. Copy it to the temp file for cropping
                FileOutputStream fileOutputStream = new FileOutputStream(mFileTemp);
                copyStream(inputStream, fileOutputStream);
                fileOutputStream.close();
                inputStream.close();
                mImagePath = mFileTemp.getPath();
                mImageUri = Utils.getImageUri(mImagePath);
                Intent intent = new Intent(this, ImageCropActivity.class);
                intent.putExtra("img_path", mImageUri);
                intent.putExtra("from", "takephoto");
                startActivityForResult(intent, REQUEST_CODE_UPDATE_PIC);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void Register() {
        String android_id = Settings.Secure.getString(this.getContentResolver(),Settings.Secure.ANDROID_ID);
        if (globalClass.connectionAvailable()) {
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.show();
            String url = globalClass.getBaseUrl()+ "api/registration";
            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            params.put("name", editText_yournames.getText().toString());
            params.put("mobile", editText_phonenumbers.getText().toString());
            params.put("password", editText_passwords.getText().toString());
            params.put("deviceid", android_id);
            params.put("fcm_reg_token", refreshedToken);
            Log.d(TAG, "register "+ url);
            Log.d(TAG, "register "+params.toString());
            client.post(url, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    if (response != null) {
                        try {
                            String status = response.getString("status");
                            String message = response.getString("message");
                            Log.d(TAG, response.toString());
                            if (status.equals("1")) {
                                String ID=response.optString("ID");
                                String OTP=response.optString("OTP");
                                verify(OTP);
                            } else {
                                new LovelyStandardDialog(SignupScreen.this)
                                        .setTopColorRes(R.color.colorAccent)
                                        .setButtonsColorRes(R.color.colorPrimaryDark)
                                        .setIcon(R.mipmap.ic_white_cross)
                                        .setTitle(R.string.registration_failed)
                                        .setMessage(message)
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
            AlertDialog alert1 = new AlertDialog.Builder(SignupScreen.this).create();
            alert1.setMessage(getString(R.string.NoInternetConnection));
            alert1.show();
        }
    }

    public void verify(String otptemp) {
        dialogverify = new Dialog(SignupScreen.this);
        dialogverify.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogverify.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogverify.setContentView(R.layout.code_verification);
        dialogverify.setCancelable(true);
        dialogverify.setCanceledOnTouchOutside(false);
        dialogverify.show();
        TextView shownumber = (TextView) dialogverify.findViewById(R.id.shownumber);
        shownumber.setText(editText_phonenumbers.getText().toString());
        otp = (EditText) dialogverify.findViewById(R.id.otp);
        otp.setText(otptemp);
        Button verify = (Button) dialogverify.findViewById(R.id.verify);
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!otp.getText().toString().isEmpty())
                {
                    verifyotp();
                }else {
                    Toast.makeText(getBaseContext(), getString(R.string.EnterOTP), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void verifyotp() {
        if (globalClass.connectionAvailable()) {
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.show();
            String url = globalClass.getBaseUrl()  + "api/verify_otp_reg";
            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            params.put("mobile", editText_phonenumbers.getText().toString());
            params.put("otp", otp.getText().toString());
            Log.d(TAG, "otp "+url);
            Log.d(TAG, "otp "+params.toString());
            client.post(url, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    if (response != null) {
                        try {
                            String status = response.getString("status");
                            Log.d(TAG, response.toString());
                            if (status.equals("1")) {
                                dialogverify.dismiss();
                                JSONObject user =response.getJSONObject("user_details");
                                String name=user.optString("name");
                                String mobile=user.optString("mobile");
                                String id=user.optString("id");
                                globalClass.setId(id);
                                globalClass.setMobile(mobile);
                                globalClass.setUser_name(name);
                                prefs.setPref_password(editText_passwords.getText().toString());
                                prefs.setPref_login_status(true);
                                prefs.setPrefuserid(id);
                                prefs.setPref_password(editText_passwords.getText().toString());
                                prefs.setPref_user_name(name);
                                Intent intent = new Intent(SignupScreen.this, Container.class);
                                startActivity(intent);
                                finish();
                            }else if (status.equals("3")){
                                new LovelyStandardDialog(SignupScreen.this)
                                        .setTopColorRes(R.color.colorAccent)
                                        .setButtonsColorRes(R.color.colorPrimaryDark)
                                        .setIcon(R.mipmap.ic_white_cross)
                                        .setTitle(R.string.registration_failed)
                                        .setMessage(R.string.wrong_code)
                                        .setPositiveButton(R.string.ok, null)
                                        .show();
                            }
                            else {
                                new LovelyStandardDialog(SignupScreen.this)
                                        .setTopColorRes(R.color.colorAccent)
                                        .setButtonsColorRes(R.color.colorPrimaryDark)
                                        .setIcon(R.mipmap.ic_white_cross)
                                        .setTitle(R.string.registration_failed)
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
        }else {
            new LovelyStandardDialog(SignupScreen.this)
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
        launchNextActivity = new Intent(SignupScreen.this,SplashScreen.class);
        launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(launchNextActivity);
    }

    private static void copyStream(InputStream input, OutputStream output) throws IOException
    {
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
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

