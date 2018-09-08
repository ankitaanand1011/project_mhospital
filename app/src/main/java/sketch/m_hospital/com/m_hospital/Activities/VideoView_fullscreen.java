package sketch.m_hospital.com.m_hospital.Activities;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import sketch.m_hospital.com.m_hospital.R;

/**
 * Created by Developer on 7/18/17.
 */
public class VideoView_fullscreen extends AppCompatActivity{
    VideoView videoView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.video_fullscreen);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);



        Bundle extras = getIntent().getExtras();
       // String UrlPath = bundle.getString("UrlPath");
        Uri myUri = Uri.parse(extras.getString("v_url"));


        videoView = (VideoView)findViewById(R.id.videoView);
        MediaController mediaController = new MediaController(VideoView_fullscreen.this);
        mediaController.setAnchorView(videoView);
        mediaController.setMediaPlayer(videoView);
        videoView.setMediaController(mediaController);

        videoView.setVideoURI(myUri);
        videoView.start();


    }
}
