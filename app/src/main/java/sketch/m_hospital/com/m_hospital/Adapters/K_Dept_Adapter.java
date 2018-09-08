package sketch.m_hospital.com.m_hospital.Adapters;

/**
 * Created by Developer on 6/29/17.
 */


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import sketch.m_hospital.com.m_hospital.Activities.GlobalClass;
import sketch.m_hospital.com.m_hospital.Fragments.FragL_Sub_Dept;
import sketch.m_hospital.com.m_hospital.R;

/**
 * Created by Developer on 6/29/17.
 */

public class K_Dept_Adapter extends BaseAdapter {
    FragmentTransaction fragmentTransaction;
    String TAG="M";
    private Context mContext;
    GlobalClass globalClass;
    View grid;
    String branch;
    ArrayList<HashMap<String, String>> arrayList ;
    public ImageLoader loader;
    DisplayImageOptions defaultOptions;
    String fromwhere, branch_name , branch_lat , branch_lon , dept_name;
    public K_Dept_Adapter(Context c, ArrayList<HashMap<String, String>> arr, String branch , String from ,   FragmentTransaction fragmentTransact, String branch_name , String branch_lat , String branch_lon , String dept_name) {
        mContext = c;
        this.branch=branch;
        this.arrayList = arr;
        this.fromwhere = from;
        this.branch_name = branch_name;
        this.branch_lat = branch_lat;
        this.branch_lon = branch_lon;
        this.dept_name = dept_name;
        this.fragmentTransaction = fragmentTransact;
        globalClass =(GlobalClass)mContext.getApplicationContext();
        loader = ImageLoader.getInstance();
        if(!loader.isInited()) {
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
        grid = inflater.inflate(R.layout.department_grid, null);
        TextView textView = (TextView) grid.findViewById(R.id.grid_text);
        ImageView imageView = (ImageView)grid.findViewById(R.id.grid_image);
        textView.setText(arrayList.get(position).get("name"));
        if(!arrayList.get(position).get("img_url").equals(""))
        {
            loader.displayImage(globalClass.getImageBaseUrl()+arrayList.get(position).get("img_url"), imageView , defaultOptions);
        }

        grid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("branch_id", branch);
                bundle.putString("Dep", arrayList.get(position).get("id"));
                bundle.putString("from", fromwhere);
                bundle.putString("branch_name", branch_name);
                bundle.putString("branch_lat", branch_lat);
                bundle.putString("branch_lon", branch_lon);
                bundle.putString("dept_name", dept_name);
                Fragment fragment=new FragL_Sub_Dept();
                fragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.frame,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return grid;
    }
}