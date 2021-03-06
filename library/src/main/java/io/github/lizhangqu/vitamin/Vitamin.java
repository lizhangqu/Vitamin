//Copyright 2017 区长. All rights reserved.
//
//Redistribution and use in source and binary forms, with or without
//modification, are permitted provided that the following conditions are
//met:
//
//* Redistributions of source code must retain the above copyright
//notice, this list of conditions and the following disclaimer.
//* Redistributions in binary form must reproduce the above
//copyright notice, this list of conditions and the following disclaimer
//in the documentation and/or other materials provided with the
//distribution.
//* Neither the name of Google Inc. nor the names of its
//contributors may be used to endorse or promote products derived from
//this software without specific prior written permission.
//
//THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
//"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
//LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
//A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
//OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
//SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
//LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
//DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
//THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
//(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
//OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
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
                Class clazz = Ini.class;
                iniEnabled = true;
            } catch (Throwable e) {
                iniEnabled = false;
            }

            try {
                Class clazz = Yaml.class;
                yamlEnabled = true;
            } catch (Throwable e) {
                yamlEnabled = false;
            }
            try {
                Class clazz = Document.class;
                dom4jEnabled = true;
            } catch (Throwable e) {
                dom4jEnabled = false;
            }

            try {
                Class clazz = JSON.class;
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
        check();
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
        check();
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
