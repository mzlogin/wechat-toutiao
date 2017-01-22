package org.mazhuang.wechattoutiao.model;

import java.util.List;

/**
 * Created by mazhuang on 2017/1/23.
 */

public class WxArticlesResult extends BaseResult {

    Result result;

    static class Result {
        int article_cnt;

        List<WxArticle> article_list;
    }
}
