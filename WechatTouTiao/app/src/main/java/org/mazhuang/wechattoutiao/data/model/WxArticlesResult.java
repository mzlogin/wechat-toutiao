package org.mazhuang.wechattoutiao.data.model;

import java.util.List;

/**
 * Created by mazhuang on 2017/1/23.
 */

public class WxArticlesResult extends BaseResult {

    public Result result;

    public static class Result {
        public int article_cnt;

        public List<WxArticle> article_list;
    }
}
