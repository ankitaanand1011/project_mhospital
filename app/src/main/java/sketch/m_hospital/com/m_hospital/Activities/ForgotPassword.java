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

public class ForgotPassword extends AppCompatActivity {
    ImageView image;
    ProgressDialog progressDialog;
    GlobalClass globalClass;
    TextView tv_verify;
    EditText editText_mobile;
    String TAG = "M";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.b_forgetpassword);
        globalClass =(GlobalClass)getApplicationContext();
        progressDialog=new ProgressDialog(this);
        if(progressDialog.isShowing())
            progressDialog.dismiss();
        editText_mobile=(EditText) findViewById(R.id.editText_mobile);
        image=(ImageView)findViewById(R.id.image_arrow);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tv_verify=(TextView) findViewById(R.id.tv_verify);
        tv_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText_mobile.getText()!=null)
                {
                    ForgotPass();
                }else {
                    editText_mobile.setError(getString(R.string.enter_phone_number));
                    editText_mobile.requestFocus();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void ForgotPass() {
        if (globalClass.connectionAvailable()) {
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.show();
            String url =    globalClass.getBaseUrl()+ "api/forgetpassword";
            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            params.put("mobile", editText_mobile.getText().toString());
            Log.d(TAG, "forgot "+url);
            Log.d(TAG, "forgot "+params);
            client.post(url, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    if (response != null) {
                        try {
                            String status = response.getString("status");
                            Log.d(TAG,"response "+ response.toString());
                            if (status.equals("1")) {
                                String OTP=response.optString("OTP");
                                globalClass.setMobile(editText_mobile.getText().toString());
                                Intent intent=new Intent(ForgotPassword.this,Resetpassword.class);
                                intent.putExtra("otp",OTP);
                                startActivity(intent);
                                finish();
                            } else {
                                new LovelyStandardDialog(ForgotPassword.this)
                                        .setTopColorRes(R.color.colorAccent)
                                        .setButtonsColorRes(R.color.colorPrimaryDark)
                                        .setIcon(R.mipmap.ic_white_cross)
                                        .setTitle(R.string.verification_failed)
                                        .setMessage(R.string.wrong_code)
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
            new LovelyStandardDialog(ForgotPassword.this)
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
