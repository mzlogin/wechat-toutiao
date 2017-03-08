package org.mazhuang.wechattoutiao;

import android.content.Intent;
import android.os.Bundle;

import org.mazhuang.wechattoutiao.base.BaseActivity;
import org.mazhuang.wechattoutiao.channels.ChannelsActivity;
import org.mazhuang.wechattoutiao.util.DeviceInfo;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DeviceInfo.getInstance().init(this);

        showChannelsPage();
    }

    private void showChannelsPage() {
        Intent intent = new Intent(this, ChannelsActivity.class);
        startActivity(intent);
        finish();
    }
}
