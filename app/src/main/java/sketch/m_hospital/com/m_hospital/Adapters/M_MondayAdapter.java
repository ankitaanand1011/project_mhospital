package sketch.m_hospital.com.m_hospital.Adapters;

/**
 * Created by Developer on 6/29/17.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import sketch.m_hospital.com.m_hospital.Activities.GlobalClass;
import sketch.m_hospital.com.m_hospital.Fragments.FragN_Doctor_Schedular;
import sketch.m_hospital.com.m_hospital.R;

public class M_MondayAdapter extends BaseAdapter{
    String TAG="M";
    private Context mContext;
   // String depsub,dep,branch,cldocname;
    String branch_id , dept_id , subdept_id;
    View grid;
    GlobalClass global;
    ArrayList<HashMap<String , String>> arrayList;
    public ImageLoader loader;
    DisplayImageOptions defaultOptions;
    FragmentTransaction fragmentTransact;

    public M_MondayAdapter(FragmentActivity activity, ArrayList<HashMap<String, String>> arrMonday, String rbranch, String rdep, String rdepsub, FragmentTransaction fragmentTransaction) {
        mContext = activity;
        global = (GlobalClass)mContext.getApplicationContext();
        this.arrayList = arrMonday;
        this.branch_id = rbranch;
        this.dept_id = rdep ;
        this.subdept_id = rdepsub;
        this.fragmentTransact = fragmentTransaction;
        loader = ImageLoader.getInstance();
        if(!loader.isInited()){
            defaultOptions = new DisplayImageOptions.Builder()
                    .cacheOnDisk(true).cacheInMemory(true)
                    .imageScaleType(ImageScaleType.EXACTLY)
                    .displayer(new FadeInBitmapDisplayer(300)).build();
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                    mContext.getApplicationContext())
                    .defaultDisplayImageOptions(defaultOptions)
                    .memoryCache(new WeakMemoryCache())
                    .diskCacheSize(100 * 1024 * 1024).build();
            ImageLoader.getInstance().init(config);
        }
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return global.getArrMonday().size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
            grid = new View(mContext);
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grid = inflater.inflate(R.layout.grid_doctor_calendar, null);
        TextView textView = (TextView) grid.findViewById(R.id.grid_text);
        TextView textView2 = (TextView) grid.findViewById(R.id.grid_text2);
        CircleImageView imageView = (CircleImageView)grid.findViewById(R.id.grid_image);
        textView.setText(arrayList.get(position).get("doctor_name"));
        if(!arrayList.get(position).get("doctor_image").equals(""))
        {
            loader.displayImage(global.getImageBaseUrl()+arrayList.get(position).get("doctor_image"), imageView , defaultOptions);
        }
        if(!arrayList.get(position).get("doctor_speciality").equals(""))
        {
            textView2.setText(arrayList.get(position).get("doctor_speciality"));
        }else{
            textView2.setText(mContext.getString(R.string.n_a));
        }

        grid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("Dep", branch_id);
                bundle.putString("Depsub", dept_id);
                bundle.putString("Branch", subdept_id);
                bundle.putString("Docnamecl", arrayList.get(position).get("doctor_id"));
                bundle.putString("doctor_name", arrayList.get(position).get("doctor_name"));
                bundle.putString("doctor_speciality", arrayList.get(position).get("doctor_speciality"));
                bundle.putString("doctor_qualification", arrayList.get(position).get("doctor_qualification"));
                bundle.putString("doctor_image", arrayList.get(position).get("doctor_image"));
                Fragment fragment=new FragN_Doctor_Schedular();
                fragment.setArguments(bundle);
                //FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransact.replace(R.id.frame,fragment);
                fragmentTransact.addToBackStack(null);
                fragmentTransact.commit();
            }
        });
        return grid;
    }
}