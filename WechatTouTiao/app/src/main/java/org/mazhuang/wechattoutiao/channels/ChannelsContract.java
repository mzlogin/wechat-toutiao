package org.mazhuang.wechattoutiao.channels;

import org.mazhuang.wechattoutiao.base.BasePresenter;
import org.mazhuang.wechattoutiao.base.BaseView;
import org.mazhuang.wechattoutiao.data.model.WxChannel;

import java.util.List;

/**
 * Created by mazhuang on 2017/2/25.
 */

public interface ChannelsContract {

    interface View extends BaseView<Presenter> {

        void showChannels(List<WxChannel> channels);

        void showNoChannels();

        void showLoadingChannelsError();

    }

    interface Presenter extends BasePresenter {

        void loadChannels();

        void saveAllData();

    }

}
