package org.mazhuang.wechattoutiao;

import android.app.Application;

import com.flurry.android.FlurryAgent;

import io.realm.Realm;

/**
 * Created by mazhuang on 2017/3/1.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        new FlurryAgent.Builder()
                .withLogEnabled(true)
                .build(this, "JXZJS4245KCBZCZ737HY");

        Realm.init(this);
    }
}
