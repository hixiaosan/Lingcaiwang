package com.bgf.lingcaiwang.ui;

import android.content.Intent;

public interface OnChildCallbackListener {

    /**
     * 切换到某页面
     *
     * @param id
     * @param intent
     */
    void switchTo(String id, Intent intent);

    /**
     * 回退到上一个Fragment
     */
    void back();

    /**
     * 点击左菜单按钮
     */
    void onProblemButtonClick();

    /**
     * 请求登录
     */
    void onRequestLogin();

}
