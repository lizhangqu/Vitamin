package io.github.lizhangqu.vitamin;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * SharedPreferences 配置
 *
 * @author lizhangqu
 * @version V1.0
 * @since 2017-07-18 19:44
 */
class SharedPreferencesConfig implements ReadableConfig {
    String name;

    SharedPreferencesConfig(String name) {
        this.name = name;
    }

    private SharedPreferences getSharedPreferences() {
        Context context = ContextProvider.getContext();
        if (context == null) {
            return null;
        }
        return context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    @Override
    public String getString(String key, String defaultValue) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        if (sharedPreferences != null) {
            return sharedPreferences.getString(key, defaultValue);
        }
        return defaultValue;
    }

    @Override
    public String getString(String key) {
        return getString(key, null);
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        if (sharedPreferences != null) {
            return sharedPreferences.getBoolean(key, defaultValue);
        }
        return defaultValue;
    }

    @Override
    public short getShort(String key, short defaultValue) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        if (sharedPreferences != null) {
            return (short) sharedPreferences.getInt(key, defaultValue);
        }
        return defaultValue;
    }

    @Override
    public int getInt(String key, int defaultValue) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        if (sharedPreferences != null) {
            return sharedPreferences.getInt(key, defaultValue);
        }
        return defaultValue;
    }

    @Override
    public long getLong(String key, long defaultValue) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        if (sharedPreferences != null) {
            return sharedPreferences.getLong(key, defaultValue);
        }
        return defaultValue;
    }

    @Override
    public float getFloat(String key, float defaultValue) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        if (sharedPreferences != null) {
            return sharedPreferences.getFloat(key, defaultValue);
        }
        return defaultValue;
    }

    @Override
    public double getDouble(String key, double defaultValue) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        if (sharedPreferences != null) {
            return sharedPreferences.getFloat(key, (float) defaultValue);
        }
        return defaultValue;
    }

    @Override
    public List<String> getList(String key, List<String> defaultValue) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        if (sharedPreferences != null) {
            Set<String> stringSet = sharedPreferences.getStringSet(key, null);
            if (stringSet != null && stringSet.size() > 0) {
                List<String> list = new ArrayList<>();
                list.addAll(stringSet);
                return list;
            }
        }
        return defaultValue;
    }

    @Override
    public List<String> getList(String key) {
        return getList(key, null);
    }

    @Override
    public Map<String, ?> getMap(String key, Map<String, ?> defaultValue) {
        //ignore key
        SharedPreferences sharedPreferences = getSharedPreferences();
        if (sharedPreferences != null) {
            return sharedPreferences.getAll();
        }
        return defaultValue;
    }

    @Override
    public Map<String, ?> getMap(String key) {
        return getMap(key, null);
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        //not support
        return null;
    }

}
