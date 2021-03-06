package org.mazhuang.wechattoutiao.channels;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import org.mazhuang.wechattoutiao.R;
import org.mazhuang.wechattoutiao.articles.BaseFragment;
import org.mazhuang.wechattoutiao.base.BaseActivity;
import org.mazhuang.wechattoutiao.data.model.WxChannel;
import org.mazhuang.wechattoutiao.util.Security;

import java.util.List;

public class ChannelsActivity extends BaseActivity implements ChannelsContract.View {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private ChannelPagerAdapter mChannelAdapter;

    private ChannelsContract.Presenter mPresenter;

    private long mLastBackMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_channels);

        initViews();

        mPresenter = new ChannelsPresenter(this);

        mPresenter.start();
    }

    @Override
    protected void onStop() {
        super.onStop();

        mPresenter.saveAllData();
    }

    private void initViews() {
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mTabLayout = (TabLayout) findViewById(R.id.tab);
        mChannelAdapter = new ChannelPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mChannelAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onBackPressed() {
        if (mChannelAdapter.getCount() > 0) {
            BaseFragment currentFragment = (BaseFragment) mChannelAdapter.instantiateItem(mViewPager, mViewPager.getCurrentItem());
            if (currentFragment != null && currentFragment.onBackPressed()) {
                return;
            }
        }

        long currentTime = System.currentTimeMillis();
        if (currentTime - mLastBackMillis > 1000) {
            Toast.makeText(this, getString(R.string.back_to_quit), Toast.LENGTH_SHORT).show();
            mLastBackMillis = currentTime;
            return;
        }

        super.onBackPressed();
    }

    @Override
    public void setPresenter(ChannelsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showChannels(List<WxChannel> channels) {
        mChannelAdapter.setData(channels);
    }

    @Override
    public void showNoChannels() {
        Toast.makeText(ChannelsActivity.this, "没有频道信息", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadingChannelsError() {
        Toast.makeText(ChannelsActivity.this, "获取频道失败", Toast.LENGTH_SHORT).show();
    }
}
