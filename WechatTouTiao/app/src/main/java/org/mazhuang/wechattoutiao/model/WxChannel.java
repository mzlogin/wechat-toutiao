package org.mazhuang.wechattoutiao.model;

/**
 * Created by mazhuang on 2017/1/23.
 */

public class WxChannel {
    public String channel_type;
    public String dotnotify;
    public String editable;
    public Entrance h5_entrance;
    public int id;
    public String name;
    public String selected;
    public String subid;

    static class Entrance {
        String target_url;
        String title;
    }
}
