package sketch.m_hospital.com.m_hospital.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import sketch.m_hospital.com.m_hospital.Activities.GlobalClass;
import sketch.m_hospital.com.m_hospital.Activities.MyPreferences;
import sketch.m_hospital.com.m_hospital.R;

/**
 * Created by Developer on 7/26/17.
 */

public class FragM_Doctor_Calendar extends Fragment {
    String TAG= "HOSPITAL";
    Context context;
    GlobalClass globalClass;
    MyPreferences prefs;
    TabLayout tabLayout;
    Toolbar toolbar;
    String dayofCalendar;
    ActionBar actionBar;
    ViewPager mViewPager;
    String subdept_id,dept_id, branch_id;
    int i=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_m_doctor_calendar, container, false);

        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        mViewPager = (ViewPager)view.findViewById(R.id.viewpager);
        mViewPager.removeAllViewsInLayout();
        mViewPager.setAdapter(mSectionsPagerAdapter);
        globalClass =(GlobalClass)getActivity().getApplicationContext();
        Log.d("HOSPITAL","day@global"+globalClass.getDayCalendar());
        dayofCalendar= globalClass.getDayCalendar();
        if(dayofCalendar!=null) {
            Log.d("HOSPITAL", "dayofCalendar" + dayofCalendar);
            if (dayofCalendar.equalsIgnoreCase("Monday")) {
                mViewPager.setCurrentItem(1);
            } else if (dayofCalendar.equalsIgnoreCase("Tuesday")) {
                mViewPager.setCurrentItem(2);
            } else if (dayofCalendar.equalsIgnoreCase("wednesday")) {
                mViewPager.setCurrentItem(3);
            } else if (dayofCalendar.equalsIgnoreCase("Thursday")) {
                mViewPager.setCurrentItem(4);
            } else if (dayofCalendar.equalsIgnoreCase("Friday")) {
                mViewPager.setCurrentItem(5);
            } else if (dayofCalendar.equalsIgnoreCase("Saturday")) {
                mViewPager.setCurrentItem(6);
            } else {
                mViewPager.setCurrentItem(0);
            }
        }

        tabLayout = (TabLayout)view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(mSectionsPagerAdapter.getTabView(i));
        }


        Log.d("make" , "make"+globalClass.getUser_name());
        prefs = new MyPreferences(getActivity());
        subdept_id= globalClass.getGsubdept_id();
        dept_id=globalClass.getGdept_id();
        branch_id = globalClass.getGbranch_id();
        Log.d(TAG,"doctor calendar");

        TextView tooltext=(TextView)getActivity().findViewById(R.id.text_toolbar);
        tooltext.setText(getString(R.string.DoctorCalendar));

       /* if(i==0) {
            Fragment fragment2 = new FragM_Sunday();
            return "SUN";
            i=1;
        }*/


        return view;
    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            Log.d("M", "adapter");
        }

        @Override
        public int getItemPosition(Object object){
            return PagerAdapter.POSITION_NONE;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {

                //mViewPager.setCurrentItem(tabLayout.getPosition(position));

                case 0:
                    Log.d("M", "adapter "+position);
                    FragM_Sunday tab1 = new FragM_Sunday();
                    Log.d("M", "sun");
                    return tab1;
                case 1:
                    FragM_Monday tab2 = new FragM_Monday();
                    Log.d("M", "mon");
                    return tab2;
                case 2:
                    FragM_Tuesday tab3 = new FragM_Tuesday();
                    Log.d("M", "tue");
                    return tab3;
                case 3:
                    FragM_Wednesday tab4 = new FragM_Wednesday();
                    Log.d("M", "wed");
                    return tab4;
                case 4:
                    FragM_Thursday tab5 = new FragM_Thursday();
                    Log.d("M", "thu");
                    return tab5;
                case 5:
                    FragM_Friday tab6 = new FragM_Friday();
                    Log.d("M", "fri");
                    return tab6;
         /*       case 6:
                    FragM_Saturday tab7 = new FragM_Saturday();
                    Log.d("M", "sat");
                    return tab7;*/

                default:
                    FragM_Saturday tab7 = new FragM_Saturday();
                    Log.d("M", "sat");
                    return tab7;
            }

        }

        @Override
        public int getCount() {
            return 7;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.SUN);
                case 1:
                    return getString(R.string.MON);
                case 2:
                    return getString(R.string.TUE);
                case 3:
                    return getString(R.string.WED);
                case 4:
                    return getString(R.string.THU);
                case 5:
                    return getString(R.string.FRI);
                case 6:
                    return getString(R.string.SAT);
            }
            return null;
        }

        public View getTabView(int position) {
            View tab = LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
            final TextView tv = (TextView) tab.findViewById(R.id.custom_text);
            tv.setText(getPageTitle(position));


            return tab;
        }
    }
}
