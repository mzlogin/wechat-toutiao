package org.mazhuang.wechattoutiao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.mazhuang.wechattoutiao.R;

/**
 * Created by mazhuang on 2017/2/6.
 */

public class ChannelFragment extends Fragment {

    public static final String CHANNEL_NAME = "channel_name";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_channel, container, false);

        Bundle args = getArguments();

        ((TextView) rootView.findViewById(R.id.channel_name)).setText(
                args.getString(CHANNEL_NAME)
        );

        return rootView;
    }
}
