package sketch.m_hospital.com.m_hospital.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import sketch.m_hospital.com.m_hospital.Adapters.PaymentAdapter;
import sketch.m_hospital.com.m_hospital.R;

/**
 * Created by ANDROID on 10/9/2017.
 */

public class FragO_PayOptions  extends Fragment {

    Button btn_proceed ;
    ListView lv_pay_opt ;
    int gateways[] , img_gateway[];
    Toolbar toolbar;
    ArrayList<HashMap<String , Integer>> arraylist ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_o_pay_options, container, false);
        lv_pay_opt = view.findViewById(R.id.lv_pay_opt);
        btn_proceed = view.findViewById(R.id.btn_proceed);
        String doctor_id =  getArguments().getString("doctor_id");
        TextView tooltext=getActivity().findViewById(R.id.text_toolbar);
        tooltext.setText(getString(R.string.select_payment_mode));

        gateways = new int[]{R.string.alipay, R.string.WeChat, R.string.apple_pay, R.string.bank_card};
        img_gateway = new int[]{R.mipmap.ic_alipay, R.mipmap.wechatlogo, R.mipmap.ic_apple_pay, R.mipmap.ic_card_pay};
        arraylist = new ArrayList<>();
        for (int i = 0; i < gateways.length; i++) {
            HashMap<String, Integer> map = new HashMap<>();
            map.put("name", gateways[i]);
            map.put("img", img_gateway[i]);
            arraylist.add(map);
        }

        if(!doctor_id.equals("no")) {
            String schedule_id = getArguments().getString("schedule_id");
            String fee = getArguments().getString("fee");
            String currency = getArguments().getString("currency");
            PaymentAdapter adaper=new PaymentAdapter(getActivity(),arraylist, doctor_id, schedule_id, fee, currency, btn_proceed);
            lv_pay_opt.setAdapter(adaper);
        }else{
            String schedule_id = getArguments().getString("purchase_id");
            String fee = getArguments().getString("price");
            String currency = getArguments().getString("currency");
            PaymentAdapter adaper=new PaymentAdapter(getActivity(),arraylist, "no", schedule_id, fee, currency , btn_proceed);
            lv_pay_opt.setAdapter(adaper);
        }



        return view;
    }


}
