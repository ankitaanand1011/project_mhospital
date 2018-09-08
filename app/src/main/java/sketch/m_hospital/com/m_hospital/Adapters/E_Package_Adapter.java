package sketch.m_hospital.com.m_hospital.Adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
import sketch.m_hospital.com.m_hospital.Activities.MyPreferences;
import sketch.m_hospital.com.m_hospital.R;

/**
 * Created by ANDROID on 10/10/2017.
 */

public class E_Package_Adapter extends BaseAdapter {

    private static LayoutInflater inflater=null;
    Context context;
    String TAG = "M";
    public ArrayList<HashMap<String,String>> arraylist;
    GlobalClass globalClass;
    MyPreferences prefs;
    public ImageLoader loader;
    DisplayImageOptions defaultOptions;



    public E_Package_Adapter(FragmentActivity activity, ArrayList<HashMap<String, String>> arraylist) {
        context = activity;
        this.arraylist = arraylist;
        globalClass = (GlobalClass) context.getApplicationContext();
        prefs = new MyPreferences(context);
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

    public int getCount() {
        return arraylist.size();
    }

    public Object getItem(int position) {
        return arraylist.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView= inflater.inflate(R.layout.row_package, null, true);
        CircleImageView img = (CircleImageView)rowView.findViewById(R.id.img);
        TextView txt = (TextView)rowView.findViewById(R.id.txt);

        txt.setText(arraylist.get(position).get("package_name"));
        if(!arraylist.get(position).get("package_image").equals(""))
        {
            loader.displayImage(globalClass.getImageBaseUrl()+arraylist.get(position).get("package_image"), img , defaultOptions);
        }

        return rowView;
    }
}
