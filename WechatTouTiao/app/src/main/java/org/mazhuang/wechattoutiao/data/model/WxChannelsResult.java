package org.mazhuang.wechattoutiao.data.model;

import org.mazhuang.wechattoutiao.data.model.BaseResult;
import org.mazhuang.wechattoutiao.data.model.WxChannel;

import java.util.List;

/**
 * Created by mazhuang on 2017/1/23.
 */

public class WxChannelsResult extends BaseResult {

    public ChannelResult result;

    public static class ChannelResult {
        public AuxiliaryModules auxiliary_modules;
        public List<WxChannel> channel_list;
        public int font_size;
        public int header_style;
        public NewChannel new_channel;
        public int showhobby;
        public int stream_style;
    }

    static class AuxiliaryModules {
        List<String> comment;
        List<String> relate_article;
        List<String> relate_tag;
        List<String> share;
    }

    static class NewChannel {
        String alert;
        int id;
        String name;
    }
}
