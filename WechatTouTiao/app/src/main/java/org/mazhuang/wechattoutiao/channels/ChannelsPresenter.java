package org.mazhuang.wechattoutiao.channels;

import org.mazhuang.wechattoutiao.data.DataSource;
import org.mazhuang.wechattoutiao.data.IDataSource;
import org.mazhuang.wechattoutiao.data.model.WxChannel;

import java.util.List;

/**
 * Created by mazhuang on 2017/2/25.
 */

public class ChannelsPresenter implements ChannelsContract.Presenter {

    private ChannelsContract.View mChannelsView;

    public ChannelsPresenter(ChannelsContract.View channelsView) {
        mChannelsView = channelsView;

        mChannelsView.setPresenter(this);
    }

    @Override
    public void loadChannels() {

        DataSource.getInstance().getChannels(new IDataSource.LoadChannelsCallback() {
            @Override
            public void onChannelsLoaded(List<WxChannel> channels) {
                if (channels == null || channels.size() == 0) {
                    mChannelsView.showNoChannels();
                } else {
                    mChannelsView.showChannels(channels);
                }
            }

            @Override
            public void onDataNotAvailable() {
                mChannelsView.showLoadingChannelsError();
            }
        });
    }

    @Override
    public void saveAllData() {
        DataSource.getInstance().saveAll(null, null);
    }

    @Override
    public void start() {
        loadChannels();
    }
}
