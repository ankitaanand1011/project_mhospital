package sketch.m_hospital.com.m_hospital.Fragments;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import sketch.m_hospital.com.m_hospital.Activities.GlobalClass;
import sketch.m_hospital.com.m_hospital.Adapters.D_Branch_Adapter;
import sketch.m_hospital.com.m_hospital.Adapters.J_Hospital360_Adapter;
import sketch.m_hospital.com.m_hospital.R;

/**
 * Created by Developer on 7/26/17.
 */

public class FragJ_Hospital_360_View extends Fragment {
    Context context;
    ListView lv_video;
    GlobalClass global;
    ImageView img;
    ProgressBar pb_bar;
    VideoView vid1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_j_hospital_360_view, container, false);
        lv_video = (ListView)view.findViewById(R.id.lv_video);
        global = (GlobalClass)getActivity().getApplicationContext();
        J_Hospital360_Adapter adapter_branchScreen = new J_Hospital360_Adapter(getActivity(), global.getArrBranch());
        lv_video.setAdapter(adapter_branchScreen);

        TextView tooltext=(TextView)getActivity().findViewById(R.id.text_toolbar);
        tooltext.setText(getString(R.string.Hospital360View));

     /*   vid1 = (VideoView)view.findViewById(R.id.vid1);
        img = (ImageView)view.findViewById(R.id.img_play);
        img.setVisibility(View.GONE);
        pb_bar = (ProgressBar)view.findViewById(R.id.pb_bar);
        pb_bar.setVisibility(View.GONE);
        play_video("http://www.html5videoplayer.net/videos/toystory.mp4", vid1,img);

*/
        return view;
    }



}