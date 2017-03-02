package org.mazhuang.wechattoutiao.articledetail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import org.mazhuang.wechattoutiao.R;
import org.mazhuang.wechattoutiao.base.BaseActivity;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class VideoActivity extends BaseActivity {

    public static final String PARAM_URL = "url";

    private String mUrl;

    private VideoView mVideoView;
    private MediaController mController;

    private boolean mNeedResume = false;
    private int mVideoPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        mVideoView = (VideoView) findViewById(R.id.video);
        mController = new MediaController(this);
        mVideoView.setMediaController(mController);

        Intent intent = getIntent();
        if (intent != null) {
            Bundle args = intent.getExtras();
            if (args != null) {
                mUrl = args.getString(PARAM_URL, null);
            }
        }

        if (mUrl != null) {
            try {
                URL url = new URL(mUrl);
                Uri uri = Uri.parse(url.toURI().toString());
                mVideoView.setVideoURI(uri);
                mVideoView.start();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mNeedResume) {
            mNeedResume = false;
            mVideoView.start();
            mVideoView.seekTo(mVideoPos);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mVideoView.isPlaying()) {
            mNeedResume = true;
            mVideoPos = mVideoView.getCurrentPosition();
            mVideoView.pause();
        }
    }
}
