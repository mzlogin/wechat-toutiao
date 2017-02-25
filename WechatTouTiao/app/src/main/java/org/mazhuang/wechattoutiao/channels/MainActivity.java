package org.mazhuang.wechattoutiao.channels;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import org.mazhuang.wechattoutiao.R;
import org.mazhuang.wechattoutiao.articles.BaseFragment;
import org.mazhuang.wechattoutiao.data.model.WxChannelsResult;
import org.mazhuang.wechattoutiao.data.source.remote.WxClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public String TAG = getClass().getSimpleName();

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private ChannelPagerAdapter mChannelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initViews();

        fetchData();
    }

    private void initViews() {
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mTabLayout = (TabLayout) findViewById(R.id.tab);
        mChannelAdapter = new ChannelPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mChannelAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void fetchData() {
        WxClient.getChannels(new Callback<WxChannelsResult>() {
            @Override
            public void onResponse(Call<WxChannelsResult> call, Response<WxChannelsResult> response) {
                Log.d(TAG, response.body().toString());
                if (response.body() != null && response.body().isSuccessful()) {
                    mChannelAdapter.setData(response.body());
                } else {
                    Toast.makeText(MainActivity.this, "获取频道失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WxChannelsResult> call, Throwable t) {
                Toast.makeText(MainActivity.this, "获取频道失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        BaseFragment currentFragment = (BaseFragment) mChannelAdapter.instantiateItem(mViewPager, mViewPager.getCurrentItem());
        if (currentFragment != null && currentFragment.onBackPressed()) {
            return;
        }

        super.onBackPressed();
    }
}
