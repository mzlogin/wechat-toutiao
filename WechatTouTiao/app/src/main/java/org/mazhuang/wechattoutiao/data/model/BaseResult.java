package org.mazhuang.wechattoutiao.data.model;

/**
 * Created by mazhuang on 2017/1/23.
 */

public class BaseResult {
    public static int STATUS_SUCCESS = 1;

    public String msg;
    public int status;

    public boolean isSuccessful() {
        return (status == STATUS_SUCCESS);
    }
}
