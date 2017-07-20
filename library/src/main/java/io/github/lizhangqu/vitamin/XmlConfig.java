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

                } finally {
                    Vitamin.getInstance().close(inputStream);
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
