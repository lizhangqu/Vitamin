package io.github.lizhangqu.vitamin;

import android.content.Context;

import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

/**
 * shadow
 *
 * @author lizhangqu
 * @version V1.0
 * @since 2017-07-19 09:42
 */
@Implements(ContextProvider.class)
public class ContextProviderShadow {
    @Implementation
    public static Context getContext() {
        return RuntimeEnvironment.application;
    }
}
