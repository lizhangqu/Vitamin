package io.github.lizhangqu.vitamin;

import java.util.List;
import java.util.Map;

/**
 * 配置接口层
 *
 * @author lizhangqu
 * @version V1.0
 * @since 2017-07-18 18:09
 */
public interface IConfig {

    String getString(String key);

    boolean getBoolean(String key, boolean defaultValue);

    short getShort(String key, short defaultValue);

    int getInt(String key, int defaultValue);

    long getLong(String key, long defaultValue);

    float getFloat(String key, float defaultValue);

    double getDouble(String key, double defaultValue);

    List<?> getList(String key, List<?> defaultValue);

    List<?> getList(String key);

    Map<?, ?> getMap(String key, Map<?, ?> defaultValue);

    Map<?, ?> getMap(String key);

    <T> T get(String key);

    boolean clear();

    boolean remove(String key);

    boolean putString(String key, String value);

    boolean putBoolean(String key, boolean value);

    boolean putShort(String key, short value);

    boolean putInt(String key, int value);

    boolean putLong(String key, long value);

    boolean putFloat(String key, float value);

    boolean putDouble(String key, double value);

    boolean putList(String key, List<?> value);

    boolean putMap(String key, Map<?, ?> value);

    <T> boolean put(String key, T t);
}
