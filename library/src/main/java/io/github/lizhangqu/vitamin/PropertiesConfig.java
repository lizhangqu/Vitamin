package io.github.lizhangqu.vitamin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Properties配置，list使用,分割，map返回所有属性
 *
 * @author lizhangqu
 * @version V1.0
 * @since 2017-07-18 19:44
 */
class PropertiesConfig implements ReadableConfig {
    InputStream inputStream;

    Properties properties;

    PropertiesConfig(String name) {
        try {
            inputStream = new FileInputStream(new File(name));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    PropertiesConfig(InputStream inputStream) {
        this.inputStream = inputStream;
    }


    private void ensureNotNull() {
        if (properties != null) {
            return;
        }
        synchronized (this) {
            if (properties == null) {
                properties = new Properties();
                try {
                    properties.load(inputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean canContinue() {
        if (properties != null) {
            return true;
        }
        return false;
    }

    @Override
    public String getString(String key, String defaultValue) {
        ensureNotNull();
        if (canContinue()) {
            if (properties.containsKey(key)) {
                return properties.getProperty(key);
            }
        }
        return defaultValue;
    }

    @Override
    public String getString(String key) {
        return getString(key, null);
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        String result = getString(key, String.valueOf(defaultValue));
        if (result != null) {
            try {
                return Boolean.valueOf(result);
            } catch (Exception e) {

            }
        }
        return defaultValue;
    }

    @Override
    public short getShort(String key, short defaultValue) {
        String result = getString(key, String.valueOf(defaultValue));
        if (result != null) {
            try {
                return Short.valueOf(result);
            } catch (Exception e) {

            }
        }
        return defaultValue;
    }

    @Override
    public int getInt(String key, int defaultValue) {
        String result = getString(key, String.valueOf(defaultValue));
        if (result != null) {
            try {
                return Integer.valueOf(result);
            } catch (Exception e) {

            }
        }
        return defaultValue;
    }

    @Override
    public long getLong(String key, long defaultValue) {
        String result = getString(key, String.valueOf(defaultValue));
        if (result != null) {
            try {
                return Long.valueOf(result);
            } catch (Exception e) {

            }
        }
        return defaultValue;
    }

    @Override
    public float getFloat(String key, float defaultValue) {
        String result = getString(key, String.valueOf(defaultValue));
        if (result != null) {
            try {
                return Float.valueOf(result);
            } catch (Exception e) {

            }
        }
        return defaultValue;
    }

    @Override
    public double getDouble(String key, double defaultValue) {
        String result = getString(key, String.valueOf(defaultValue));
        if (result != null) {
            try {
                return Double.valueOf(result);
            } catch (Exception e) {

            }
        }
        return defaultValue;
    }

    @Override
    public List<String> getList(String key, List<String> defaultValue) {
        String result = getString(key, String.valueOf(defaultValue));
        if (result != null) {
            try {
                String[] split = result.split(",");
                return Arrays.asList(split);
            } catch (Exception e) {

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
        try {
            Map<String, ?> map = new HashMap<String, Object>((Map) properties);
            return map;
        } catch (Exception e) {

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
