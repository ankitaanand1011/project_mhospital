package sketch.m_hospital.com.m_hospital.Adapters;

/**
 * Created by Developer on 4/12/17.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sketch.m_hospital.com.m_hospital.Activities.GlobalClass;
import sketch.m_hospital.com.m_hospital.Fragments.FragP_Possiblities;
import sketch.m_hospital.com.m_hospital.R;


public class F_Symptoms_Checker_Adapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;
    ArrayList<HashMap<String, String>> posarray = new ArrayList<>();
    ArrayList<HashMap<String, String>> posarrayfiltered = new ArrayList<>();

    FragmentTransaction fragmentTransaction;
    GlobalClass global;

    public F_Symptoms_Checker_Adapter(Context context, FragmentTransaction ft, List<String> listDataHeader,
                                      HashMap<String, List<String>> listChildData,ArrayList<HashMap<String, String>> posarray) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this.posarray = posarray;
        this.fragmentTransaction = ft;
        global=(GlobalClass)_context.getApplicationContext();
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);

        txtListChild.setText(childText);




        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                posarrayfiltered.clear();

                Log.d("jwd", "possixe: "+posarray.size());


                for (int i = 0; i < posarray.size(); i++) {

                    Log.d("jwd", "allkey: "+posarray.get(i).get("key"));

                    if (posarray.get(i).get("key").matches(childText)){

                        posarrayfiltered.add(posarray.get(i));


                    }
                }

                if (posarrayfiltered.size()>0) {

                   global.setArrPossiblities(posarrayfiltered);

                    Fragment fragment = new FragP_Possiblities();
                    fragmentTransaction.replace(R.id.frame, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }else {

                    Toast.makeText(_context,"No Data Available",Toast.LENGTH_SHORT).show();
                }
            }
        });


        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
        ImageView arw_img = (ImageView) convertView.findViewById(R.id.image_arrow);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);


        if (isExpanded) {
            arw_img.setImageResource(R.drawable.arow_down);
        } else {
            arw_img.setImageResource(R.drawable.arow_right);
        }

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}