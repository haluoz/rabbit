package com.lxj.rabbit.api;

/**
 * callback回调函数
 * @author Xingjing.Li
 * @since 2021/8/3
 */
public interface SendCallback {
    void onSuccess();
    void onFailure();
}
