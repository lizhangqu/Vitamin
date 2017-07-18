package io.github.lizhangqu.vitamin;

import android.annotation.SuppressLint;

/**
 * 核心类
 *
 * @author lizhangqu
 * @version V1.0
 * @since 2017-07-18 17:39
 */
public class Vitamin {
    private static Vitamin sInstance;

    private Vitamin() {

    }

    private static class SingletonHolder {
        private static final Vitamin INSTANCE = new Vitamin();
    }

    public static Vitamin getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
