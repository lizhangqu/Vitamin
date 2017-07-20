package io.github.lizhangqu.vitamin;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import org.dom4j.Document;
import org.ini4j.Ini;
import org.yaml.snakeyaml.Yaml;

import java.io.Closeable;
import java.io.IOException;
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
            }

            try {
                Gson.class.hashCode();
                jsonEnabled = true;
            } catch (Throwable e) {
            }
        }
        checked = true;
    }


    public void setConfigType(ConfigType configType) {
        this.configType = configType;
    }

    public ReadableConfig getReadableConfig(String name) {
        if (configType != null) {
            return getReadableConfig(this.configType, name);
        }
        ReadableConfig config = null;
        if (name != null) {
            if (name.endsWith(".yaml")) {
                if (yamlEnabled) {
                    config = new YamlConfig(name);
                }
            } else if (name.endsWith(".ini")) {
                if (iniEnabled) {
                    config = new IniConfig(name);
                }
            } else if (name.endsWith(".xml")) {
                if (dom4jEnabled) {
                    config = new XmlConfig(name);
                }
            } else if (name.endsWith(".json")) {
                if (jsonEnabled) {
                    config = new JsonConfig(name);
                }
            } else if (name.endsWith(".properties")) {
                config = new PropertiesConfig(name);
            } else {
                config = new EmptyConfig(name);
            }
        }
        if (config == null) {
            config = new EmptyConfig(name);
        }
        return config;
    }

    public ReadableConfig getReadableConfig(InputStream inputStream) {
        if (configType != null) {
            return getReadableConfig(this.configType, inputStream);

        }
        return new EmptyConfig(inputStream);
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
        }
        if (config == null) {
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

        }
        if (config == null) {
            config = new EmptyConfig(inputStream);
        }
        return config;
    }

    void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
