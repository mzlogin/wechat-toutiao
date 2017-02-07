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
        args.putParcelable(ChannelFragment.CHANNEL_INFO, mData.result.channel_list.get(position));
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

    @Override
    public CharSequence getPageTitle(int position) {
        if (mData == null) {
            return null;
        } else {
            return mData.result.channel_list.get(position).name;
        }
    }

    public void setData(WxChannelsResult data) {
        mData = data;
        notifyDataSetChanged();
    }
}
