package sketch.m_hospital.com.m_hospital.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import org.json.JSONObject;

import sketch.m_hospital.com.m_hospital.R;

/**
 * Created by Developer on 7/18/17.
 */

public class Resetpassword extends AppCompatActivity {
    ImageView image;
    TextView button;
    GlobalClass globalClass;
    ProgressDialog progressDialog;
    EditText editText_code,editText_password,editText_Repeatpassword;
    Boolean codeverified=false;
    String TAG = "M";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.resetpassword);
        globalClass =(GlobalClass)getApplicationContext();
        progressDialog=new ProgressDialog(this);
        if(progressDialog.isShowing())
            progressDialog.dismiss();
        image=(ImageView)findViewById(R.id.image_arrow);
        editText_Repeatpassword=(EditText) findViewById(R.id.editText_Repeatpassword);
        editText_code=(EditText) findViewById(R.id.editText_code);
        editText_code.setText(getIntent().getStringExtra("otp"));
        editText_password=(EditText) findViewById(R.id.editText_password);
        button=(TextView)findViewById(R.id.text_login);
        button=(TextView)findViewById(R.id.text_login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText_code.getText().toString().isEmpty()){
                    editText_code.setError(getString(R.string.enter_otp));
                }else {
                    if (editText_password.getText().toString().isEmpty()){
                        editText_password.setError(getString(R.string.enter_new_password));
                    }else {
                        if (editText_password.getText().length()<6){
                            editText_password.setError(getString(R.string.password_length));
                        }else {
                            if (editText_Repeatpassword.getText().toString().isEmpty()){

                                editText_Repeatpassword.setError(getString(R.string.renter_password));
                            }else {
                                if (!editText_password.getText().toString().matches(editText_Repeatpassword.getText().toString())){

                                    editText_Repeatpassword.setError(getString(R.string.password_mismatch));
                                }
                                else {

                                    if (codeverified){
                                        resetpass();
                                    }
                                    else {
                                        verifyotp();
                                    }
                                }
                            }
                        }
                    }
                }


            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void verifyotp() {
        if (globalClass.connectionAvailable()) {
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.show();
            String url = globalClass.getBaseUrl()  + "api/verify_otp_forgot";
            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            params.put("mobile", globalClass.getMobile());
            params.put("otp", editText_code.getText().toString());
            Log.d(TAG , "url "+url);
            Log.d(TAG , "url "+params);
            client.post(url, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                    if (response != null) {
                        try {
                            String status = response.getString("status");
                            Log.d(TAG , "response"+ response.toString());
                            if (status.equals("1")) {
                                String id=response.optString("ID");
                                globalClass.setId(id);
                                resetpass();
                            }else if (status.equals("3")){
                                if (progressDialog.isShowing())
                                    progressDialog.dismiss();
                                new LovelyStandardDialog(Resetpassword.this)
                                        .setTopColorRes(R.color.colorAccent)
                                        .setButtonsColorRes(R.color.colorPrimaryDark)
                                        .setIcon(R.mipmap.ic_white_cross)
                                        .setTitle(R.string.otp_verification)
                                        .setMessage(R.string.wrong_code)
                                        .setPositiveButton(R.string.ok, null)
                                        .show();
                            }
                            else {
                                if (progressDialog.isShowing())
                                    progressDialog.dismiss();
                                new LovelyStandardDialog(Resetpassword.this)
                                        .setTopColorRes(R.color.colorAccent)
                                        .setButtonsColorRes(R.color.colorPrimaryDark)
                                        .setIcon(R.mipmap.ic_white_cross)
                                        .setTitle(R.string.verification_failed)
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
            new LovelyStandardDialog(Resetpassword.this)
                    .setTopColorRes(R.color.colorAccent)
                    .setButtonsColorRes(R.color.colorPrimaryDark)
                    .setIcon(R.mipmap.ic_white_cross)
                    .setTitle(R.string.internet_connection)
                    .setMessage(R.string.no_connection)
                    .setPositiveButton(R.string.ok, null)
                    .show();
        }
    }


    public void resetpass() {
        codeverified=true;
        if (globalClass.connectionAvailable()) {
            String url = globalClass.getBaseUrl()  + "api/resetpassword";
            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            params.put("id", globalClass.getId());
            params.put("new_password", editText_password.getText().toString());
            Log.d(TAG , "url "+url);
            Log.d(TAG , "url "+params);
            client.post(url, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    if (response != null) {
                        try {
                            String status = response.getString("status");
                            Log.d("response", response.toString());
                            if (status.equals("1")) {
                               /* AlertDialog success=new AlertDialog.Builder(Resetpassword.this).create();
                                success.setMessage("Password Successfully Update");
                                success.setCancelable(false);
                                success.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        startActivity(new Intent(Resetpassword.this,LoginScreen.class));
                                        finish();
                                    }
                                });*/

                                new LovelyStandardDialog(Resetpassword.this)
                                        .setTopColorRes(R.color.colorAccent)
                                        .setButtonsColorRes(R.color.colorPrimaryDark)
                                        .setIcon(R.mipmap.ic_white_tick)
                                        .setTitle(R.string.reset_password)
                                        .setMessage(R.string.password_set)
                                        .setPositiveButton(R.string.ok, new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                startActivity(new Intent(Resetpassword.this,LoginScreen.class));
                                                finish();
                                            }
                                        })
                                        .show();
                            }
                            else if (status.equals("2")) {
                                new LovelyStandardDialog(Resetpassword.this)
                                        .setTopColorRes(R.color.colorAccent)
                                        .setButtonsColorRes(R.color.colorPrimaryDark)
                                        .setIcon(R.mipmap.ic_white_cross)
                                        .setTitle(R.string.use_diff_password)
                                        .setMessage(R.string.cannot_same_as_old)
                                        .setPositiveButton(R.string.ok, null)
                                        .show();
                            }
                            else {
                                new LovelyStandardDialog(Resetpassword.this)
                                        .setTopColorRes(R.color.colorAccent)
                                        .setButtonsColorRes(R.color.colorPrimaryDark)
                                        .setIcon(R.mipmap.ic_white_cross)
                                        .setTitle(R.string.updating_failed)
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
            new LovelyStandardDialog(Resetpassword.this)
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
    }
}
