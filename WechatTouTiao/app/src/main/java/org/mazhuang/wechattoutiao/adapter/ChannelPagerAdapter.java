package org.mazhuang.wechattoutiao.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.mazhuang.wechattoutiao.fragment.ChannelFragment;
import org.mazhuang.wechattoutiao.model.WxChannelsResult;

/**
 * Created by mazhuang on 2017/2/6.
 */

public class ChannelPagerAdapter extends FragmentStatePagerAdapter {

    private WxChannelsResult mData;

    public ChannelPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new ChannelFragment();
        Bundle args = new Bundle();
        args.putString(ChannelFragment.CHANNEL_NAME, mData.result.channel_list.get(position).name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        if (mData == null) {
            return 0;
        } else {
            return mData.result.channel_list.size();
        }
    }

    public void setData(WxChannelsResult data) {
        mData = data;
        notifyDataSetChanged();
    }
}
