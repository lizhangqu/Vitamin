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
                } finally {
                    Vitamin.getInstance().close(inputStream);
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
            if (key != null && key.contains(".")) {
                int i = key.indexOf('.');
                String sectionKey = key.substring(0, i);
                if (ini.containsKey(sectionKey)) {
                    Profile.Section section = ini.get(sectionKey);
                    if (section != null) {
                        String realKey = key.substring(i + 1);
                        if (section.containsKey(realKey)) {
                            return section.get(realKey, clazz);
                        }
                    }
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
            if (ini.containsKey(key)) {
                return ini.get(key);
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
        try {
            return get(key, null, clazz);
        } catch (Exception e) {
            //not support
        }
        return null;
    }

}
