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

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 功能介绍
 *
 * @author lizhangqu
 * @version V1.0
 * @since 2017-07-19 09:44
 */
@Config(sdk = 23)
@RunWith(RobolectricTestRunner.class)
public class JsonConfigTest {

    private static final Logger logger = Logger.getLogger(JsonConfigTest.class);


    @BeforeClass
    public static void setupClass() {
        logger.setLevel(Level.ALL);
        logger.addAppender(new ConsoleAppender(new PatternLayout("%-6r [%p] %c - %m%n")));
    }

    @Before
    public void setup() {

    }

    @Test
    @Config(manifest = Config.NONE, shadows = {ContextProviderShadow.class})
    public void testGet() throws IOException {
        InputStream resourceAsStream = this.getClass().getResourceAsStream("/testData/test.json");
        ReadableConfig readableConfig = Vitamin.getInstance().getReadableConfig(ConfigType.JSON, resourceAsStream);

        boolean aBoolean = readableConfig.getBoolean("boolean", false);
        short aShort = readableConfig.getShort("short", (short) 0);
        int anInt = readableConfig.getInt("int", 0);
        long aLong = readableConfig.getLong("long", 0);

        float aFloat = readableConfig.getFloat("float", -1F);
        double aDouble = readableConfig.getDouble("double", -1);
        String string = readableConfig.getString("string");

        Map<String, ?> map = readableConfig.getMap("map");
        List<String> list = readableConfig.getList("list");

        Map obj = readableConfig.get("map", Map.class);

        Assert.assertEquals(true, aBoolean);
        Assert.assertEquals(1, aShort);
        Assert.assertEquals(2, anInt);
        Assert.assertEquals(1.2, aFloat, 1);
        Assert.assertEquals(1123.2, aDouble, 1);
        Assert.assertEquals(1234567, aLong);
        Assert.assertEquals("qwerdfvxcz", string);

        Assert.assertNotNull(list);
        Assert.assertNotNull(map);
        Assert.assertTrue(list.size() > 0);
        Assert.assertTrue(map.size() > 0);

        logger.debug("boolean:" + aBoolean);
        logger.debug("short:" + aShort);
        logger.debug("int:" + anInt);
        logger.debug("float:" + aFloat);
        logger.debug("double:" + aDouble);
        logger.debug("long:" + aLong);
        logger.debug("string:" + string);
        logger.debug("map:" + map);
        logger.debug("list:" + list);
        logger.debug("obj:" + obj);

    }


}
