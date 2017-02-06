package org.mazhuang.wechattoutiao.model;

/**
 * Created by mazhuang on 2017/1/23.
 */

public class BaseResult {
    public static int STATUS_SUCCESS = 1;

    String msg;
    int status;

    public boolean isSuccessful() {
        return (status == STATUS_SUCCESS);
    }
}
