package io.github.lizhangqu.vitamin;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import org.dom4j.Document;
import org.ini4j.Ini;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;

/**
 * 核心类
 *
 * @author lizhangqu
 * @version V1.0
 * @since 2017-07-18 17:39
 */
public class Vitamin {
    private static Vitamin sInstance;

    private boolean checked;
    private boolean iniEnabled;
    private boolean yamlEnabled;
    private boolean dom4jEnabled;
    private boolean jsonEnabled;
    private ConfigType configType;


    private Vitamin() {

    }

    private static class SingletonHolder {
        private static final Vitamin INSTANCE = new Vitamin();
    }

    public static Vitamin getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private void check() {
        if (checked) {
            return;
        }
        synchronized (this) {
            try {
                Ini.class.hashCode();
                iniEnabled = true;
            } catch (Throwable e) {
                iniEnabled = false;
            }

            try {
                Yaml.class.hashCode();
                yamlEnabled = true;
            } catch (Throwable e) {
                yamlEnabled = false;
            }
            try {
                Document.class.hashCode();
                dom4jEnabled = true;
            } catch (Throwable e) {
                dom4jEnabled = false;
            }

            try {
                JSON.class.hashCode();
                jsonEnabled = true;
            } catch (Throwable e) {
                jsonEnabled = false;
            }

            try {
                Gson.class.hashCode();
                jsonEnabled = true;
            } catch (Throwable e) {
                jsonEnabled = false;
            }
        }
        checked = true;
    }


    public void setConfigType(ConfigType configType) {
        this.configType = configType;
    }

    public ReadableConfig getReadableConfig(String name) {
        if (configType == null) {
            return new EmptyConfig(name);
        }
        return getReadableConfig(this.configType, name);
    }

    public ReadableConfig getReadableConfig(InputStream inputStream) {
        if (configType == null) {
            return new EmptyConfig(inputStream);
        }
        return getReadableConfig(this.configType, inputStream);
    }

    public ReadableConfig getReadableConfig(ConfigType configType, String name) {
        check();
        ReadableConfig config = null;
        try {
            switch (configType) {
                case YAML:
                    if (yamlEnabled) {
                        config = new YamlConfig(name);
                    }
                    break;
                case INI:
                    if (iniEnabled) {
                        config = new IniConfig(name);
                    }
                    break;
                case XML:
                    if (dom4jEnabled) {
                        config = new XmlConfig(name);
                    }
                    break;
                case JSON:
                    if (jsonEnabled) {
                        config = new JsonConfig(name);
                    }
                    break;
                case PROPERTIES:
                    config = new PropertiesConfig(name);
                    break;
                case PREFERENCES:
                    config = new SharedPreferencesConfig(name);
                    break;
                default:
                    config = new EmptyConfig(name);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            config = new EmptyConfig(name);
        }
        return config;
    }

    public ReadableConfig getReadableConfig(ConfigType configType, InputStream inputStream) {
        check();
        ReadableConfig config = null;
        try {
            switch (configType) {
                case YAML:
                    if (yamlEnabled) {
                        config = new YamlConfig(inputStream);
                    }
                    break;
                case INI:
                    if (iniEnabled) {
                        config = new IniConfig(inputStream);
                    }
                    break;
                case XML:
                    if (dom4jEnabled) {
                        config = new XmlConfig(inputStream);
                    }
                    break;
                case JSON:
                    if (jsonEnabled) {
                        config = new JsonConfig(inputStream);
                    }
                    break;
                case PROPERTIES:
                    config = new PropertiesConfig(inputStream);
                    break;
                case PREFERENCES:
                    throw new UnsupportedOperationException("not support");
                default:
                    config = new EmptyConfig(inputStream);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            config = new EmptyConfig(inputStream);
        }
        return config;
    }
}
