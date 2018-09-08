
package sketch.m_hospital.com.m_hospital.Adapters;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;
import java.util.ArrayList;
import java.util.HashMap;

import me.biubiubiu.justifytext.library.JustifyTextView;
import sketch.m_hospital.com.m_hospital.Activities.GlobalClass;
import sketch.m_hospital.com.m_hospital.Activities.MyPreferences;
import sketch.m_hospital.com.m_hospital.Fragments.FragO_PayOptions;
import sketch.m_hospital.com.m_hospital.R;

/**
 * Created by ANDROID on 6/21/2016.
 */
public class E_BookHealth_Pkg_Adapter extends BaseAdapter{

    private static LayoutInflater inflater=null;
    Context context;
    String TAG = "TP";
    public ArrayList<HashMap<String,String>> arraylist;
    GlobalClass globalClass;
    MyPreferences prefs;
    FragmentTransaction fragmentTransaction;


    public E_BookHealth_Pkg_Adapter(FragmentActivity activity, ArrayList<HashMap<String, String>> arraylist , FragmentTransaction fragmentTransactio) {
        context = activity;
        this.arraylist = arraylist;
        this.fragmentTransaction =  fragmentTransactio;
        globalClass = (GlobalClass)context.getApplicationContext();
        prefs = new MyPreferences(context);
    }

    public int getCount() {
        return arraylist.size();
    }

    public Object getItem(int position) {
        return arraylist.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @TargetApi(Build.VERSION_CODES.N)
    public View getView(final int position, View convertView, ViewGroup parent) {
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView= inflater.inflate(R.layout.row_book_health_pkg, null, true);
        TextView tv_pack_name=rowView.findViewById(R.id.tv_pack_name);
        JustifyTextView tv_pack_desc=rowView.findViewById(R.id.tv_pack_desc);
        tv_pack_name.setText(arraylist.get(position).get("name"));

        Spanned s = Html.fromHtml(arraylist.get(position).get("details"),Html.FROM_HTML_MODE_LEGACY);
        Spanned s1 = Html.fromHtml(arraylist.get(position).get("details"));
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            tv_pack_desc.setText(s.toString());
        } else {
            tv_pack_desc.setText(s1.toString());
        }

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Dialog dialogpayment = new Dialog(context);
                dialogpayment.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogpayment.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogpayment.setContentView(R.layout.diag_payment);
                dialogpayment.setCancelable(true);
                dialogpayment.setCanceledOnTouchOutside(false);
                dialogpayment.show();
                TextView package_name=dialogpayment.findViewById(R.id.package_name);
                TextView package_details=dialogpayment.findViewById(R.id.package_details);
                TextView fees=dialogpayment.findViewById(R.id.fees);
                Button proceed = dialogpayment.findViewById(R.id.proceed);
                final  RadioButton rb_yes = dialogpayment.findViewById(R.id.rb_yes);
                RadioButton rb_no = dialogpayment.findViewById(R.id.rb_no);

                package_name.setText(arraylist.get(position).get("name"));
                package_details.setText(Html.fromHtml(arraylist.get(position).get("details")));
                fees.setText(arraylist.get(position).get("price") +" "+arraylist.get(position).get("currency"));

                proceed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(rb_yes.isChecked()) {
                            Bundle bundle =new Bundle();
                            bundle.putString("doctor_id","no");
                            bundle.putString("purchase_id",arraylist.get(position).get("id"));
                            bundle.putString("price",arraylist.get(position).get("price"));
                            bundle.putString("currency",arraylist.get(position).get("currency"));
                            Fragment fragment=new FragO_PayOptions();
                            fragment.setArguments(bundle);
                            fragmentTransaction.replace(R.id.frame,fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            dialogpayment.dismiss();
                        }else{
                            new LovelyStandardDialog(context)
                                    .setTopColorRes(R.color.colorAccent)
                                    .setButtonsColorRes(R.color.colorPrimaryDark)
                                    .setIcon(R.mipmap.ic_white_cross)
                                    .setTitle(context.getString(R.string.PackageBooking))
                                    .setMessage(context.getString(R.string.Pleaseconfirm))
                                    .setPositiveButton(R.string.ok, null)
                                    .show();
                        }

                    }
                });

            }
        });


        return rowView;
    }
}
