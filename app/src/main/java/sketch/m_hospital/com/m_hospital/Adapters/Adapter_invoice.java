package sketch.m_hospital.com.m_hospital.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

import sketch.m_hospital.com.m_hospital.R;


/**
 * Created by Developer on 9/13/17.
 */

public class Adapter_invoice extends BaseAdapter {
    Context context;
    ArrayList<String> invoice_list = new ArrayList<>();
    TextView invoice_name,invoice_url;
    String string1;

    public Adapter_invoice(Context context ,  ArrayList<String> invoice_list) {
        this.context = context;
        this.invoice_list = invoice_list;
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

        string1 = invoice_list.get(position);
        String string2 = "http://lab-5.sketchdemos.com/PHP-WEB-SERVICES/P-930-MHospital/assets/invoice_pdf/";
        String result = string1.replace(string2,"");
        invoice_url.setText(result);

        listViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(string1));
                context.startActivity(intent);
                          }
        });
        return listViewItem;

    }
}
