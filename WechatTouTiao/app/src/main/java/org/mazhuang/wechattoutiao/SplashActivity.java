package org.mazhuang.wechattoutiao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.mazhuang.wechattoutiao.base.BaseActivity;
import org.mazhuang.wechattoutiao.channels.ChannelsActivity;

import io.realm.Realm;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Realm.init(getApplicationContext());

        showChannelsPage();
    }

    private void showChannelsPage() {
        Intent intent = new Intent(this, ChannelsActivity.class);
        startActivity(intent);
        finish();
    }
}
