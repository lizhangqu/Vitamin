package io.github.lizhangqu.vitamin;

import org.ini4j.Ini;
import org.ini4j.Profile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * ini配置读取，使用.作为section分隔
 *
 * @author lizhangqu
 * @version V1.0
 * @since 2017-07-18 19:43
 */
class IniConfig implements ReadableConfig {
    InputStream inputStream;

    Ini ini;

    IniConfig(String name) {
        try {
            inputStream = new FileInputStream(new File(name));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    IniConfig(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    private void ensureNotNull() {
        if (ini != null) {
            return;
        }
        synchronized (this) {
            if (ini == null) {
                ini = new Ini();
                try {
                    ini.load(inputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean canContinue() {
        if (ini != null) {
            return true;
        }
        return false;
    }

    private <T> T get(String key, T defaultValue, Class<T> clazz) {
        ensureNotNull();
        if (canContinue()) {
            if (key.contains(".")) {
                int i = key.indexOf('.');
                String sectionKey = key.substring(0, i);
                Profile.Section section = ini.get(sectionKey);
                if (section != null) {
                    String realKey = key.substring(i + 1);
                    return section.get(realKey, clazz);
                }
            }
        }
        return defaultValue;
    }


    @Override
    public String getString(String key, String defaultValue) {
        return get(key, defaultValue, String.class);
    }


    @Override
    public String getString(String key) {
        return getString(key, null);
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        return get(key, defaultValue, Boolean.class);
    }

    @Override
    public short getShort(String key, short defaultValue) {
        return get(key, defaultValue, Short.class);
    }

    @Override
    public int getInt(String key, int defaultValue) {
        return get(key, defaultValue, Integer.class);
    }

    @Override
    public long getLong(String key, long defaultValue) {
        return get(key, defaultValue, Long.class);
    }

    @Override
    public float getFloat(String key, float defaultValue) {
        return get(key, defaultValue, Float.class);
    }

    @Override
    public double getDouble(String key, double defaultValue) {
        return get(key, defaultValue, Double.class);
    }

    @Override
    public List<String> getList(String key, List<String> defaultValue) {
        String s = get(key, null, String.class);
        if (s != null) {
            String[] split = s.split(",");
            return Arrays.asList(split);
        }
        return defaultValue;
    }

    @Override
    public List<String> getList(String key) {
        return getList(key, null);
    }

    @Override
    public Map<String, ?> getMap(String key, Map<String, ?> defaultValue) {
        ensureNotNull();
        if (canContinue()) {
            return ini.get(key);
        }
        return defaultValue;
    }

    @Override
    public Map<String, ?> getMap(String key) {
        return getMap(key, null);
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        try {
            return get(key, null, clazz);
        } catch (Exception e) {
            //not support
        }
        return null;
    }

}
