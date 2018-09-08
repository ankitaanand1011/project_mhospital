package sketch.m_hospital.com.m_hospital.Adapters;

/**
 * Created by ANDROID on 9/19/2017.
 */
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;

import sketch.m_hospital.com.m_hospital.Activities.GlobalClass;
import sketch.m_hospital.com.m_hospital.R;
public class Adapter_report  extends BaseAdapter {
    Context context;
    ArrayList<String> invoice_list = new ArrayList<>();
    TextView invoice_name,invoice_url;
    String s;
    GlobalClass globalClass;


    public Adapter_report(Context context ,  ArrayList<String> invoice_list) {
        this.context = context;
        this.invoice_list = invoice_list;
        globalClass = (GlobalClass)context.getApplicationContext();
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
        View listViewItem = inflater.inflate(R.layout.invoice_row_view , null);
        invoice_name  = (TextView)listViewItem.findViewById(R.id.invoice_name);
        invoice_url  = (TextView)listViewItem.findViewById(R.id.invoice_url);
        invoice_url.setText(invoice_list.get(position));
        s = globalClass.getReportBaseUrl()+invoice_list.get(position);

        listViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(s));
                context.startActivity(intent);
            }
        });
        return listViewItem;
    }

    public static String getFileNameFromUrl(URL url) {

        String urlPath = url.getPath();

        return urlPath.substring(urlPath.lastIndexOf('/') + 1);
    }
}
