package io.github.lizhangqu.vitamin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.TypeUtils;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * json配置，根节点不支持jsonArray
 *
 * @author lizhangqu
 * @version V1.0
 * @since 2017-07-18 19:43
 */
class JsonConfig implements ReadableConfig {
    static boolean fastjsonEnabled;
    static boolean gsonEnabled;

    InputStream inputStream;

    Map<String, Object> json;

    JsonConfig(String name) {
        try {
            inputStream = new FileInputStream(new File(name));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    JsonConfig(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    static {
        try {
            JSON.class.hashCode();
            fastjsonEnabled = true;
        } catch (Throwable e) {
            fastjsonEnabled = false;
        }

        try {
            Gson.class.hashCode();
            gsonEnabled = true;
        } catch (Throwable e) {
            gsonEnabled = false;
        }
    }

    public static long copy(InputStream input, OutputStream output) throws IOException {
        return copyLarge(input, output, new byte[2048]);
    }

    public static long copyLarge(InputStream input, OutputStream output, byte[] buffer)
            throws IOException {
        long count = 0;
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    private void ensureNotNull() {
        if (json != null) {
            return;
        }
        synchronized (this) {
            if (json == null) {
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                try {
                    copy(inputStream, output);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    Vitamin.getInstance().close(inputStream);
                    Vitamin.getInstance().close(output);
                }
                if (fastjsonEnabled) {
                    json = JSON.parseObject(output.toByteArray(), Map.class);
                } else if (gsonEnabled) {
                    Gson gson = new Gson();
                    json = gson.fromJson(new String(output.toByteArray()), Map.class);
                }

            }
        }
    }

    private boolean canContinue() {
        if (json != null) {
            return true;
        }
        return false;
    }

    @Override
    public String getString(String key, String defaultValue) {
        ensureNotNull();
        if (canContinue()) {
            Object o = json.get(key);
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
            if (fastjsonEnabled) {
                JSONArray jsonArray = (JSONArray) json.get(key);
                if (jsonArray != null) {
                    return jsonArray.toJavaObject(List.class);
                }
            } else if (gsonEnabled) {
                return (List<String>) json.get(key);
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
        if (canContinue()) {
            if (fastjsonEnabled) {
                JSONObject jsonObject = (JSONObject) json.get(key);
                if (jsonObject != null) {
                    return jsonObject.toJavaObject(Map.class);
                }
            } else if (gsonEnabled) {
                return (Map<String, ?>) json.get(key);
            }
        }
        return defaultValue;
    }

    @Override
    public Map<String, ?> getMap(String key) {
        return getMap(key, null);
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        if (canContinue()) {
            if (fastjsonEnabled) {
                Object o = json.get(key);
                return TypeUtils.cast(o, clazz, ParserConfig.getGlobalInstance());
            } else if (gsonEnabled) {
                Gson gson = new Gson();
                Object o = json.get(key);
                if (o != null) {
                    return gson.fromJson(o.toString(), clazz);
                }
            }
        }
        return null;
    }

}
