package io.github.lizhangqu.vitamin;

import java.util.List;
import java.util.Map;

/**
 * 可读配置
 *
 * @author lizhangqu
 * @version V1.0
 * @since 2017-07-18 18:09
 */
public interface ReadableConfig {

    String getString(String key, String defaultValue);

    String getString(String key);

    boolean getBoolean(String key, boolean defaultValue);

    short getShort(String key, short defaultValue);

    int getInt(String key, int defaultValue);

    long getLong(String key, long defaultValue);

    float getFloat(String key, float defaultValue);

    double getDouble(String key, double defaultValue);

    List<String> getList(String key, List<String> defaultValue);

    List<String> getList(String key);

    Map<String, ?> getMap(String key, Map<String, ?> defaultValue);

    Map<String, ?> getMap(String key);

    <T> T get(String key, Class<T> clazz);


}
