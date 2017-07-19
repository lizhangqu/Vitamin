package io.github.lizhangqu.vitamin;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 空实现，降级策略
 *
 * @author lizhangqu
 * @version V1.0
 * @since 2017-07-18 19:46
 */
class EmptyConfig implements ReadableConfig {
    EmptyConfig(String name) {

    }

    EmptyConfig(InputStream inputStream) {

    }

    @Override
    public String getString(String key, String defaultValue) {
        return defaultValue;
    }

    @Override
    public String getString(String key) {
        return null;
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        return defaultValue;
    }

    @Override
    public short getShort(String key, short defaultValue) {
        return defaultValue;
    }

    @Override
    public int getInt(String key, int defaultValue) {
        return defaultValue;
    }

    @Override
    public long getLong(String key, long defaultValue) {
        return defaultValue;
    }

    @Override
    public float getFloat(String key, float defaultValue) {
        return defaultValue;
    }

    @Override
    public double getDouble(String key, double defaultValue) {
        return defaultValue;
    }

    @Override
    public List<String> getList(String key, List<String> defaultValue) {
        return defaultValue;
    }

    @Override
    public List<String> getList(String key) {
        return null;
    }

    @Override
    public Map<String, ?> getMap(String key, Map<String, ?> defaultValue) {
        return defaultValue;
    }

    @Override
    public Map<String, ?> getMap(String key) {
        return null;
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        return null;
    }

}
