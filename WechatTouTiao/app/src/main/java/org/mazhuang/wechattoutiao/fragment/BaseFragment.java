package org.mazhuang.wechattoutiao.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.mazhuang.wechattoutiao.model.WxChannel;

/**
 * Created by mazhuang on 2017/2/13.
 */

public class BaseFragment extends Fragment {
    public static final String CHANNEL_INFO = "channel_info";

    protected WxChannel mChannelInfo;

    protected void fetchArguments() {
        Bundle args = getArguments();
        mChannelInfo = args.getParcelable(CHANNEL_INFO);
    }

    public boolean onBackPressed() {
        return false;
    }
}
