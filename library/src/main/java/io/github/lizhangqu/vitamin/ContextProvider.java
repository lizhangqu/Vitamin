package io.github.lizhangqu.vitamin;

import android.content.Context;

import com.android.support.application.ApplicationCompat;

/**
 * 功能介绍
 *
 * @author lizhangqu
 * @version V1.0
 * @since 2017-07-18 20:17
 */
class ContextProvider {
    public static Context getContext() {
        return ApplicationCompat.getApplicationContext();
    }
}
