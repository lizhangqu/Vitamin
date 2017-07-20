package io.github.lizhangqu.vitamin;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * yaml配置，根节点不支持list
 *
 * @author lizhangqu
 * @version V1.0
 * @since 2017-07-18 19:43
 */
class YamlConfig implements ReadableConfig {
    InputStream inputStream;

    Map<String, Object> yaml;

    YamlConfig(String name) {
        try {
            inputStream = new FileInputStream(new File(name));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public YamlConfig(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    private void ensureNotNull() {
        if (yaml != null) {
            return;
        }
        synchronized (this) {
            if (yaml == null) {
                Yaml y = new Yaml();
                yaml = (Map<String, Object>) y.load(inputStream);
                Vitamin.getInstance().close(inputStream);
            }
        }
    }

    private boolean canContinue() {
        if (yaml != null) {
            return true;
        }
        return false;
    }

    @Override
    public String getString(String key, String defaultValue) {
        ensureNotNull();
        if (canContinue()) {
            Object o = yaml.get(key);
            if (o != null) {
                return o.toString();
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
            return Boolean.valueOf(result);
        }
        return defaultValue;
    }

    @Override
    public short getShort(String key, short defaultValue) {
        String result = getString(key, String.valueOf(defaultValue));
        if (result != null) {
            return Double.valueOf(result).shortValue();
        }
        return defaultValue;
    }

    @Override
    public int getInt(String key, int defaultValue) {
        String result = getString(key, String.valueOf(defaultValue));
        if (result != null) {
            return Double.valueOf(result).intValue();
        }
        return defaultValue;
    }

    @Override
    public long getLong(String key, long defaultValue) {
        String result = getString(key, String.valueOf(defaultValue));
        if (result != null) {
            return Double.valueOf(result).longValue();
        }
        return defaultValue;
    }

    @Override
    public float getFloat(String key, float defaultValue) {
        String result = getString(key, String.valueOf(defaultValue));
        if (result != null) {
            return Double.valueOf(result).floatValue();
        }
        return defaultValue;
    }

    @Override
    public double getDouble(String key, double defaultValue) {
        ensureNotNull();
        String result = getString(key, String.valueOf(defaultValue));
        if (result != null) {
            return Double.valueOf(result);
        }
        return defaultValue;
    }

    @Override
    public List<String> getList(String key, List<String> defaultValue) {
        ensureNotNull();
        if (canContinue()) {
            return (List<String>) yaml.get(key);
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
            return (Map<String, ?>) yaml.get(key);
        }
        return defaultValue;
    }

    @Override
    public Map<String, ?> getMap(String key) {
        return getMap(key, null);
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        //may not support
        try {
            return (T) yaml.get(key);
        } catch (Exception e) {

        }
        return null;
    }

}
