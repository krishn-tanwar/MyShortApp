package com.kt.myshortapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

public class PreviewActivity extends AppCompatActivity {

    private VideoView vvVideo;
    private DisplayMetrics dm;
    private MediaController media_Controller;
    private String videoPath;
    private Bundle bundle2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        Intent intent = getIntent();
        videoPath = intent.getStringExtra("videoPath");

        initView();
    }

    private void initView(){
        vvVideo = (VideoView)findViewById(R.id.vv_video);

        Log.v("Krishn ", "mergeVideoPath Preview " + videoPath);
        playVideo();
    }

    public void playVideo() {
        try {
            media_Controller = new MediaController(this);
            dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            int height = dm.heightPixels;
            int width = dm.widthPixels;
//            previewBinding.vvVideo.setMinimumWidth(width);
//            previewBinding.vvVideo.setMinimumHeight(height);
            vvVideo.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));

            vvVideo.setMediaController(media_Controller);
            vvVideo.setVideoPath(videoPath);
            vvVideo.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}