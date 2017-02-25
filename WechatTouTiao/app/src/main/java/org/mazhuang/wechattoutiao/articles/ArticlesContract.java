package org.mazhuang.wechattoutiao.articles;

import org.mazhuang.wechattoutiao.base.BasePresenter;
import org.mazhuang.wechattoutiao.base.BaseView;
import org.mazhuang.wechattoutiao.channels.ChannelsContract;
import org.mazhuang.wechattoutiao.data.model.WxArticle;
import org.mazhuang.wechattoutiao.data.model.WxChannel;

import java.util.List;

/**
 * Created by mazhuang on 2017/2/26.
 */

public interface ArticlesContract {

    interface View extends BaseView<Presenter> {

        void showArticles(List<WxArticle> articles);

        void showNoArticles();

        void showLoadingArticlesError();

        WxChannel getChannelInfo();

    }

    interface Presenter extends BasePresenter {

        void loadArticles();

    }

}
