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
