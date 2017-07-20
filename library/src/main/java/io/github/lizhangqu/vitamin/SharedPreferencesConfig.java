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

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * SharedPreferences 配置
 *
 * @author lizhangqu
 * @version V1.0
 * @since 2017-07-18 19:44
 */
class SharedPreferencesConfig implements ReadableConfig {
    String name;

    SharedPreferencesConfig(String name) {
        this.name = name;
    }

    private SharedPreferences getSharedPreferences() {
        Context context = ContextProvider.getContext();
        if (context == null) {
            return null;
        }
        return context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    @Override
    public String getString(String key, String defaultValue) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        if (sharedPreferences != null) {
            return sharedPreferences.getString(key, defaultValue);
        }
        return defaultValue;
    }

    @Override
    public String getString(String key) {
        return getString(key, null);
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        if (sharedPreferences != null) {
            return sharedPreferences.getBoolean(key, defaultValue);
        }
        return defaultValue;
    }

    @Override
    public short getShort(String key, short defaultValue) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        if (sharedPreferences != null) {
            return (short) sharedPreferences.getInt(key, defaultValue);
        }
        return defaultValue;
    }

    @Override
    public int getInt(String key, int defaultValue) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        if (sharedPreferences != null) {
            return sharedPreferences.getInt(key, defaultValue);
        }
        return defaultValue;
    }

    @Override
    public long getLong(String key, long defaultValue) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        if (sharedPreferences != null) {
            return sharedPreferences.getLong(key, defaultValue);
        }
        return defaultValue;
    }

    @Override
    public float getFloat(String key, float defaultValue) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        if (sharedPreferences != null) {
            return sharedPreferences.getFloat(key, defaultValue);
        }
        return defaultValue;
    }

    @Override
    public double getDouble(String key, double defaultValue) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        if (sharedPreferences != null) {
            return sharedPreferences.getFloat(key, (float) defaultValue);
        }
        return defaultValue;
    }

    @Override
    public List<String> getList(String key, List<String> defaultValue) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        if (sharedPreferences != null) {
            if (sharedPreferences.contains(key)) {
                Set<String> stringSet = sharedPreferences.getStringSet(key, null);
                if (stringSet != null && stringSet.size() > 0) {
                    List<String> list = new ArrayList<>();
                    list.addAll(stringSet);
                    return list;
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
        //ignore key
        SharedPreferences sharedPreferences = getSharedPreferences();
        if (sharedPreferences != null) {
            return sharedPreferences.getAll();
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
