package org.mazhuang.wechattoutiao.channels;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.mazhuang.wechattoutiao.articles.ChannelTypeOneFragment;
import org.mazhuang.wechattoutiao.articles.ChannelTypeThreeFragment;
import org.mazhuang.wechattoutiao.articles.ChannelTypeTwoFragment;
import org.mazhuang.wechattoutiao.data.model.WxChannel;
import org.mazhuang.wechattoutiao.data.model.WxChannelsResult;

import java.util.List;

/**
 * Created by mazhuang on 2017/2/6.
 */

public class ChannelPagerAdapter extends FragmentStatePagerAdapter {

    private List<WxChannel> mData;

    public ChannelPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    private static Fragment getChannelFragment(String channelType) {
        Fragment fragment;
        switch (channelType) {
            case "1":
                fragment = new ChannelTypeOneFragment();
                break;

            case "2":
                fragment = new ChannelTypeTwoFragment();
                break;

            case "3":
                fragment = new ChannelTypeThreeFragment();
                break;

            default:
                fragment = new ChannelTypeOneFragment();
                break;
        }
        return fragment;
    }

    @Override
    public Fragment getItem(int position) {
        if (mData == null || mData.size() <= position) {
            return null;
        }

        Fragment fragment = getChannelFragment(mData.get(position).channel_type);
        Bundle args = new Bundle();
        args.putParcelable(ChannelTypeOneFragment.CHANNEL_INFO, mData.get(position));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        if (mData == null) {
            return 0;
        } else {
            return mData.size();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mData == null) {
            return null;
        } else {
            return mData.get(position).name;
        }
    }

    public void setData(List<WxChannel> channels) {
        mData = channels;
        notifyDataSetChanged();
    }
}
