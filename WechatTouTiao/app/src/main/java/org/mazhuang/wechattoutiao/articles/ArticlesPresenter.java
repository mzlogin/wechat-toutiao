package org.mazhuang.wechattoutiao.articles;

import org.mazhuang.wechattoutiao.data.DataSource;
import org.mazhuang.wechattoutiao.data.IDataSource;
import org.mazhuang.wechattoutiao.data.model.WxArticle;

import java.util.List;

/**
 * Created by mazhuang on 2017/2/26.
 */

public class ArticlesPresenter implements ArticlesContract.Presenter {

    private ArticlesContract.View mArticlesView;

    public ArticlesPresenter(ArticlesContract.View articlesView) {
        mArticlesView = articlesView;
        mArticlesView.setPresenter(this);
    }

    @Override
    public void loadArticles(boolean force) {
        DataSource.getInstance().getArticles(
                mArticlesView.getChannelInfo(),
                -1,
                force,
                new IDataSource.LoadArticlesCallBack() {
                    @Override
                    public void onArticlesLoaded(List<WxArticle> articles, int addCount) {
                        if (articles == null || articles.size() == 0) {
                            mArticlesView.showNoArticles();
                        } else {
                            mArticlesView.showArticles(articles);
                        }
                    }

                    @Override
                    public void onDataNotAvailable() {
                        mArticlesView.showLoadingArticlesError();
                    }
                });
    }

    @Override
    public void loadMoreArticles() {
        DataSource.getInstance().getMoreArticles(
                mArticlesView.getChannelInfo(),
                0,
                -1,
                new IDataSource.LoadArticlesCallBack() {
                    @Override
                    public void onArticlesLoaded(List<WxArticle> articles, int addCount) {
                        if (addCount <= 0) {
                            mArticlesView.showNoMoreArticles();
                        } else {
                            mArticlesView.showMoreArticles(articles);
                        }
                    }

                    @Override
                    public void onDataNotAvailable() {
                        mArticlesView.showLoadingMoreArticlesError();
                    }
                }
        );
    }

    @Override
    public void start() {
        loadArticles(mArticlesView.needForceRefresh());
    }
}
