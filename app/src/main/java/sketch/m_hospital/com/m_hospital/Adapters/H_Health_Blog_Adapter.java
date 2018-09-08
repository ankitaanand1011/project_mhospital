package sketch.m_hospital.com.m_hospital.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
import sketch.m_hospital.com.m_hospital.Fragments.FragH_Health_expanded;
import sketch.m_hospital.com.m_hospital.R;

/**
 * Created by Developer on 6/29/17.
 */

public class H_Health_Blog_Adapter extends BaseAdapter {
    String TAG="HOSPITAL";
    ArrayList<HashMap<String, String>> arraylist;
    public ImageLoader loader;
    DisplayImageOptions defaultOptions;
    ImageView imageView;
    private Context mContext;
    GlobalClass globalClass;
    FragmentTransaction fragmentTransact;

   public H_Health_Blog_Adapter(Context context, ArrayList<HashMap<String, String>> arraylist, FragmentTransaction fragmentTransaction)
    {
        this.mContext=context;
        this.arraylist=arraylist;
        globalClass =(GlobalClass)mContext.getApplicationContext();
        this.fragmentTransact = fragmentTransaction;
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
        loader = ImageLoader.getInstance();
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return arraylist.size();
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

    public View getView(final int position, View convertview, ViewGroup parent)
    {
        LayoutInflater layoutInflater=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rv=layoutInflater.inflate(R.layout.healthblog,parent,false);

        TextView tv_blog_name = (TextView)rv.findViewById(R.id.tv_blog_name);
        ImageView imageView=(ImageView)rv.findViewById(R.id.image_branch);
        tv_blog_name.setText(arraylist.get(position).get("name"));
        if(!arraylist.get(position).get("img_url").equals(""))
        {
            loader.displayImage(globalClass.getImageBaseUrl()+arraylist.get(position).get("img_url"), imageView , defaultOptions);
        }

        rv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("name", arraylist.get(position).get("name"));
                bundle.putString("description", arraylist.get(position).get("description"));
                Fragment fragment=new FragH_Health_expanded();
                fragment.setArguments(bundle);
                fragmentTransact.replace(R.id.frame,fragment);
                fragmentTransact.addToBackStack(null);
                fragmentTransact.commit();
            }
        });
        return  rv;
    }

}
