package sketch.m_hospital.com.m_hospital.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import java.util.ArrayList;
import java.util.HashMap;
import sketch.m_hospital.com.m_hospital.Activities.Appointment_Activity;
import sketch.m_hospital.com.m_hospital.Activities.GlobalClass;
import sketch.m_hospital.com.m_hospital.R;

/**
 * Created by ANDROID on 9/19/2017.
 */

public class Adapter_appointment  extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String, String>>  invoice_list;
    ImageView img;
    TextView doc_name,fee, date_time;
    public ImageLoader loader;
    DisplayImageOptions defaultOptions;
    GlobalClass globalclass;
   // ArrayList<String>  rl_arr;

    public Adapter_appointment(Appointment_Activity context, ArrayList<HashMap<String, String>> invoice_list) {
        this.context = context;
        globalclass = (GlobalClass)context.getApplicationContext();
        this.invoice_list = invoice_list;
        loader = ImageLoader.getInstance();
        if (!loader.isInited()) {
            defaultOptions = new DisplayImageOptions.Builder()
                    .cacheOnDisk(true).cacheInMemory(true)
                    .imageScaleType(ImageScaleType.EXACTLY)
                    .displayer(new FadeInBitmapDisplayer(300)).build();
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                    context.getApplicationContext())
                    .defaultDisplayImageOptions(defaultOptions)
                    .memoryCache(new WeakMemoryCache())
                    .diskCacheSize(100 * 1024 * 1024).build();
            ImageLoader.getInstance().init(config);
        }
    }


    @Override
    public int getCount() {

        return invoice_list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return invoice_list.size();
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v= convertView;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listViewItem = inflater.inflate(R.layout.row_appoinment , null);
        //RelativeLayout rl2 = (RelativeLayout)listViewItem.findViewById(R.id.rl2);

      /*  if(rl_arr.get(position).equals("green")){
            rl2.setBackgroundColor(Color.GREEN);
        }else{
            rl2.setBackgroundColor(Color.WHITE);
        }*/
        img = (ImageView)listViewItem.findViewById(R.id.img);
        doc_name  = (TextView)listViewItem.findViewById(R.id.doc_name);
        fee  = (TextView)listViewItem.findViewById(R.id.fee);
        date_time= (TextView)listViewItem.findViewById(R.id.date_time);

        doc_name.setText(invoice_list.get(position).get("doctor_name"));
        fee.setText("Fee :"+invoice_list.get(position).get("fee"));
        date_time.setText(invoice_list.get(position).get("day")+"  "+invoice_list.get(position).get("date")+"  "+invoice_list.get(position).get("time_slot"));

        if(!invoice_list.get(position).get("doctor_image").equals(""))
        {
            loader.displayImage(globalclass.getImageBaseUrl()+invoice_list.get(position).get("doctor_image"), img , defaultOptions);
        }

        listViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* rl_arr.clear();
                for(int i = 0;i<invoice_list.size();i++){
                    rl_arr.add("white");
                }
                rl_arr.set(position,"green");
                notifyDataSetChanged();*/
            }
        });
        return listViewItem;

    }
}
