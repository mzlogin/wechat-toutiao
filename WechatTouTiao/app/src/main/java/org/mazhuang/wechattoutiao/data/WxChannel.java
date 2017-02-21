package org.mazhuang.wechattoutiao.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by mazhuang on 2017/1/23.
 */

public class WxChannel implements Parcelable {
    public String channel_type;
    public String dotnotify;
    public String editable;
    public Entrance h5_entrance;
    public int id;
    public String name;
    public String selected;
    public String subid;
    public List<Entrance> external_entrance;
    public String h5_link;

    WxChannel(Parcel in) {
        channel_type = in.readString();
        dotnotify = in.readString();
        editable = in.readString();
        h5_entrance = in.readParcelable(Entrance.class.getClassLoader());
        id = in.readInt();
        name = in.readString();
        selected = in.readString();
        subid = in.readString();
        in.readTypedList(external_entrance, Entrance.CREATOR);
        h5_link = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(channel_type);
        dest.writeString(dotnotify);
        dest.writeString(editable);
        dest.writeParcelable(h5_entrance, flags);
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(selected);
        dest.writeString(subid);
        dest.writeTypedList(external_entrance);
        dest.writeString(h5_link);
    }

    public static final Parcelable.Creator<WxChannel> CREATOR
            = new Parcelable.Creator<WxChannel>() {
        @Override
        public WxChannel createFromParcel(Parcel parcel) {
            return new WxChannel(parcel);
        }

        @Override
        public WxChannel[] newArray(int size) {
            return new WxChannel[size];
        }
    };

    static class Entrance implements Parcelable {
        String target_url;
        String title;
        String icon;
        int is_local;

        Entrance(Parcel in) {
            target_url = in.readString();
            title = in.readString();
            icon = in.readString();
            is_local = in.readInt();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(target_url);
            dest.writeString(title);
            dest.writeString(icon);
            dest.writeInt(is_local);
        }

        public static final Parcelable.Creator<Entrance> CREATOR
                = new Parcelable.Creator<Entrance>() {
            @Override
            public Entrance createFromParcel(Parcel parcel) {
                return new Entrance(parcel);
            }

            @Override
            public Entrance[] newArray(int size) {
                return new Entrance[size];
            }
        };
    }
}
