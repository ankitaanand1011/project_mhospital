package sketch.m_hospital.com.m_hospital.Adapters;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeIntents;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sketch.m_hospital.com.m_hospital.Activities.GlobalClass;
import sketch.m_hospital.com.m_hospital.Activities.VideoView_fullscreen;
import sketch.m_hospital.com.m_hospital.Models.Branch;
import sketch.m_hospital.com.m_hospital.R;

/**
 * Created by ANDROID on 8/31/2017.
 */

public class J_Hospital360_Adapter extends BaseAdapter {
    String TAG="M";
    Context context;
    View grid;
    private Context mContext;
    ArrayList<Branch> arrayList;
    DisplayImageOptions defaultOptions;
    public ImageLoader loader;
    GlobalClass global;
    FragmentManager mgr;
    String VIDEO_ID;

    public J_Hospital360_Adapter(Context context, ArrayList<Branch> branches)
    {
        this.mContext = context;
        this.arrayList=branches;
        global = (GlobalClass)mContext.getApplicationContext();
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
        loader = ImageLoader.getInstance();
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
        grid = inflater.inflate(R.layout.row_video, null);
        VideoView videoview = (VideoView)grid.findViewById(R.id.vid);
        YouTubeThumbnailView youtube_thumb = (YouTubeThumbnailView)grid.findViewById(R.id.yt_thumbnail);
        ImageView img_vw = (ImageView)grid.findViewById(R.id.img_play1);
        ImageView img_yt = (ImageView)grid.findViewById(R.id.img_play2);
        ProgressBar pb_vw = (ProgressBar)grid.findViewById(R.id.pb_bar1);
        pb_vw.setVisibility(View.GONE);
       // ProgressBar pb_yt = (ProgressBar)grid.findViewById(R.id.pb_bar2);

        if(arrayList.get(position).getVideo_type().equals("youtube")){
            //show youtube thumb nail
            play_youtube_video(arrayList.get(position).getVideo_link(), youtube_thumb, img_yt);
            img_vw.setVisibility(View.GONE);
            //pb_vw.setVisibility(View.GONE);
            videoview.setVisibility(View.GONE);
            img_yt.setVisibility(View.VISIBLE);
           // pb_yt.setVisibility(View.VISIBLE);
            youtube_thumb.setVisibility(View.VISIBLE);
        }else if(arrayList.get(position).getVideo_type().equals("file")){
            //show normal video
            play_video("http://lab-5.sketchdemos.com/PHP-WEB-SERVICES/P-930-MHospital/assets/uploads/files/"+arrayList.get(position).getVideo_file(), videoview, img_vw, pb_vw);
            img_vw.setVisibility(View.VISIBLE);
            //pb_vw.setVisibility(View.VISIBLE);
            videoview.setVisibility(View.VISIBLE);
            img_yt.setVisibility(View.GONE);
           // pb_yt.setVisibility(View.GONE);
            youtube_thumb.setVisibility(View.GONE);
        }else if(arrayList.get(position).getVideo_type().equals("link")){
            //show normal video
            play_video(arrayList.get(position).getVideo_link(), videoview, img_vw, pb_vw);
            img_vw.setVisibility(View.VISIBLE);
            //pb_vw.setVisibility(View.VISIBLE);
            videoview.setVisibility(View.VISIBLE);
            img_yt.setVisibility(View.GONE);
           // pb_yt.setVisibility(View.GONE);
            youtube_thumb.setVisibility(View.GONE);
        }
        return grid;
    }

    public void play_video(final String url, final VideoView videoView, final ImageView play_image , final ProgressBar pb){
        Log.d("video_id", "Play_Video: "+url);
        final MediaController mediaController= new MediaController(mContext);
        mediaController.setAnchorView(videoView);
        mediaController.setVisibility(View.GONE);
        videoView.setMediaController(mediaController);
        videoView.setVideoPath(url);
        videoView.setBackground(null);
        //videoView.start();
        //pb.setVisibility(View.VISIBLE);
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaController.setVisibility(View.GONE);
            }
        });

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setVolume(0, 0);
                //pb.setVisibility(View.GONE);
                play_image.setVisibility(View.VISIBLE);
            }
        });

        play_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,VideoView_fullscreen.class);
                intent.putExtra("v_url",url);
                mContext.startActivity(intent);
            }
        });
    }

    public void play_youtube_video(final String s, final YouTubeThumbnailView youtubeView, final ImageView play_image ){
        String vId = null;
        String pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(s);
        if (matcher.find()) {
            vId = matcher.group();
        }
        VIDEO_ID = vId;
        youtubeView.initialize("AIzaSyDhtdbQzIo-KMKyLV59xhljcaT0_VYli1E", new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, final YouTubeThumbnailLoader youTubeThumbnailLoader) {
                youTubeThumbnailLoader.setVideo(VIDEO_ID);
                youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                    @Override
                    public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                        youTubeThumbnailLoader.release();
                    }
                    @Override
                    public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {
                    }
                });
            }
            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
            }
        });
        youtubeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vId = null;
                String pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
                Pattern compiledPattern = Pattern.compile(pattern);
                Matcher matcher = compiledPattern.matcher(s);
                if (matcher.find()) {
                    vId = matcher.group();
                }
                VIDEO_ID = vId;
                Intent intent = YouTubeIntents.createPlayVideoIntentWithOptions(mContext, VIDEO_ID, true, false);
                mContext.startActivity(intent);
            }
        });
    }

}

