package io.github.lizhangqu.vitamin;


import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * xml配置
 *
 * @author lizhangqu
 * @version V1.0
 * @since 2017-07-18 19:44
 */
class XmlConfig implements ReadableConfig {
    InputStream inputStream;
    Element document;

    XmlConfig(String name) {
        try {
            inputStream = new FileInputStream(new File(name));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    XmlConfig(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    private void ensureNotNull() {
        if (document != null) {
            return;
        }
        synchronized (this) {
            if (document == null) {
                try {
                    SAXReader sr = new SAXReader();
                    Document doc = sr.read(inputStream);
                    document = doc.getRootElement();
                } catch (Exception e) {

                }

            }
        }
    }

    private boolean canContinue() {
        if (document != null) {
            return true;
        }
        return false;
    }

    @Override
    public String getString(String key, String defaultValue) {
        ensureNotNull();
        if (canContinue()) {
            Element element = document.element(key);
            if (element != null) {
                return element.getTextTrim();
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
        ensureNotNull();
        if (canContinue()) {
            String result = getString(key, String.valueOf(defaultValue));
            if (result != null) {
                return Boolean.valueOf(result);
            }
        }
        return defaultValue;
    }

    @Override
    public short getShort(String key, short defaultValue) {
        ensureNotNull();
        if (canContinue()) {
            String result = getString(key, String.valueOf(defaultValue));
            if (result != null) {
                return Double.valueOf(result).shortValue();
            }
        }
        return defaultValue;
    }

    @Override
    public int getInt(String key, int defaultValue) {
        ensureNotNull();
        if (canContinue()) {
            String result = getString(key, String.valueOf(defaultValue));
            if (result != null) {
                return Double.valueOf(result).intValue();
            }
        }
        return defaultValue;
    }

    @Override
    public long getLong(String key, long defaultValue) {
        ensureNotNull();
        if (canContinue()) {
            String result = getString(key, String.valueOf(defaultValue));
            if (result != null) {
                return Double.valueOf(result).longValue();
            }
        }
        return defaultValue;
    }

    @Override
    public float getFloat(String key, float defaultValue) {
        ensureNotNull();
        if (canContinue()) {
            String result = getString(key, String.valueOf(defaultValue));
            if (result != null) {
                return Double.valueOf(result).floatValue();
            }
        }
        return defaultValue;
    }

    @Override
    public double getDouble(String key, double defaultValue) {
        ensureNotNull();
        if (canContinue()) {
            String result = getString(key, String.valueOf(defaultValue));
            if (result != null) {
                return Double.valueOf(result);
            }
        }
        return defaultValue;
    }

    @Override
    public List<String> getList(String key, List<String> defaultValue) {
        ensureNotNull();
        if (canContinue()) {
            Element element = document.element(key);
            if (element != null) {
                List<Element> elements = element.elements();
                if (elements != null && elements.size() > 0) {
                    List<String> result = new ArrayList<>();
                    for (Element e : elements) {
                        result.add(e.getTextTrim());
                    }
                    return result;
                }
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
        ensureNotNull();
        if (canContinue()) {
            Element element = document.element(key);
            if (element != null) {
                List<Element> elements = element.elements();
                if (elements != null && elements.size() > 0) {
                    Map<String, String> result = new HashMap<>();
                    for (Element e : elements) {
                        result.put(e.getName(), e.getTextTrim());
                    }
                    return result;
                }
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
        //not support
        return null;
    }

}
