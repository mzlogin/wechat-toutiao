package org.mazhuang.wechattoutiao.data.model;

import java.util.List;

/**
 * Created by mazhuang on 2017/1/23.
 */

public class WxArticle implements Comparable<WxArticle> {
    public String account_openid;
    public String appendix;
    public List<String> img_list;
    public KeywordTag keyword_tag;
    public String link;
    public String open_link;
    public String pub_source;
    public long pub_time;
    public int read_num;
    public int stream_id;
    public List<KeywordTag> subscribe_list;
    public Tag tag;
    public String title;
    public int type;
    public List<String> unlike_list;
    public int video_type;

    static class KeywordTag {
        public String appendix;
        public String h5_link;
        public String id;
        public String name;
        public int source;
        public int subscribe_num;
        public Tag tag;
        public int type;
    }

    static class Tag {
        public String color;
        public String icon;
        public int tag_id;
    }

    @Override
    public int compareTo(WxArticle wxArticle) {
        if (this.pub_time > wxArticle.pub_time) {
            return -1;
        } else if (this.pub_time < wxArticle.pub_time) {
            return 1;
        } else {
            return 0;
        }
    }
}
