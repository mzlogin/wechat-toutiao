package org.mazhuang.wechattoutiao.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by mazhuang on 2017/2/26.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
