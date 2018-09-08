package sketch.m_hospital.com.m_hospital.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import java.util.ArrayList;
import sketch.m_hospital.com.m_hospital.Activities.GlobalClass;
import sketch.m_hospital.com.m_hospital.Activities.Map_activity;
import sketch.m_hospital.com.m_hospital.Fragments.FragK_Dept;
import sketch.m_hospital.com.m_hospital.Models.Branch;
import sketch.m_hospital.com.m_hospital.R;

/**
 * Created by Developer on 6/29/17.
 */

public class D_Branch_Adapter extends BaseAdapter {
    String TAG="M";
    Context context;
    View grid;
    private Context mContext;
    ArrayList<Branch> arrayList;
    DisplayImageOptions defaultOptions;
    public ImageLoader loader;
    GlobalClass global;
    FragmentManager mgr;
   public D_Branch_Adapter(Context context, ArrayList<Branch> branches , FragmentManager mgr)
    {
        this.mContext = context;
        this.arrayList=branches;
        this.mgr = mgr;
        global = (GlobalClass)mContext.getApplicationContext();
        loader = ImageLoader.getInstance();
        if(!loader.isInited()) {
            DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
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
        return arrayList.size();
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
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        grid = inflater.inflate(R.layout.branchlisttext, null);
        TextView branch_name = (TextView) grid.findViewById(R.id.branch_name);
        ImageView branch_img = (ImageView)grid.findViewById(R.id.branch_img);
        TextView branch_location = (TextView) grid.findViewById(R.id.branch_location);
        branch_name.setText(arrayList.get(position).getBranch_name());
        if(!arrayList.get(position).getImg_url().equals(""))
        {
            loader.displayImage(global.getImageBaseUrl()+arrayList.get(position).getImg_url(), branch_img , defaultOptions);
        }

        branch_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle =new Bundle();
                bundle.putString("from","home");
                bundle.putString("branch_id",arrayList.get(position).getBranch_id());
                bundle.putString("branch_name",arrayList.get(position).getBranch_name());
                bundle.putString("branch_lat",arrayList.get(position).getLatitude());
                bundle.putString("branch_lon",arrayList.get(position).getLongitude());
                Fragment fragment=new FragK_Dept();
                fragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction=mgr.beginTransaction();
                fragmentTransaction.replace(R.id.frame,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        branch_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(mContext , Map_activity.class);
                i.putExtra("branch_name",arrayList.get(position).getBranch_name());
                mContext.startActivity(i);
            }
        });


      /*  grid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("Dep", arrayList.get(position).get("id"));
                bundle.putString("from", fromwhere);
                Fragment fragment=new FragL_Sub_Dept();
                fragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.frame,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        */
        return grid;
    }

}
