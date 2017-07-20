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

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 空实现，降级策略
 *
 * @author lizhangqu
 * @version V1.0
 * @since 2017-07-18 19:46
 */
class EmptyConfig implements ReadableConfig {
    EmptyConfig(String name) {

    }

    EmptyConfig(InputStream inputStream) {
        Vitamin.getInstance().close(inputStream);
    }

    @Override
    public String getString(String key, String defaultValue) {
        return defaultValue;
    }

    @Override
    public String getString(String key) {
        return null;
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        return defaultValue;
    }

    @Override
    public short getShort(String key, short defaultValue) {
        return defaultValue;
    }

    @Override
    public int getInt(String key, int defaultValue) {
        return defaultValue;
    }

    @Override
    public long getLong(String key, long defaultValue) {
        return defaultValue;
    }

    @Override
    public float getFloat(String key, float defaultValue) {
        return defaultValue;
    }

    @Override
    public double getDouble(String key, double defaultValue) {
        return defaultValue;
    }

    @Override
    public List<String> getList(String key, List<String> defaultValue) {
        return defaultValue;
    }

    @Override
    public List<String> getList(String key) {
        return null;
    }

    @Override
    public Map<String, ?> getMap(String key, Map<String, ?> defaultValue) {
        return defaultValue;
    }

    @Override
    public Map<String, ?> getMap(String key) {
        return null;
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        return null;
    }

}
