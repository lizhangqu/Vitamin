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
import java.util.ArrayList;
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
public class IniConfigTest {

    private static final Logger logger = Logger.getLogger(IniConfigTest.class);


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
        InputStream resourceAsStream = this.getClass().getResourceAsStream("/testData/test.ini");
        ReadableConfig readableConfig = Vitamin.getInstance().getReadableConfig(ConfigType.INI, resourceAsStream);

        boolean aBoolean = readableConfig.getBoolean("Keyboard.HEIGHT_FIXED", false);
        short aShort = readableConfig.getShort("Keyboard.TOUCH_MODE", (short) 0);
        int anInt = readableConfig.getInt("Key_W_Land.TYPE", 0);
        long aLong = readableConfig.getLong("Key_W_Land.S_STYLE", 0);

        float aFloat = readableConfig.getFloat("Keyboard.H_GAP_QWERTY", -1F);
        double aDouble = readableConfig.getDouble("Keyboard.H", -1);
        String string = readableConfig.getString("Keyboard.FG_STYLE");

        Map<String, ?> map = readableConfig.getMap("Keyboard");
        List<String> list = readableConfig.getList("Keyboard.ROWS");
        String obj = readableConfig.get("Keyboard.H", String.class);

        Assert.assertEquals(true, aBoolean);
        Assert.assertEquals(2, aShort);
        Assert.assertEquals(2, anInt);
        Assert.assertEquals(0.005F, aFloat, 1);
        Assert.assertEquals(0.2909699, aDouble, 1);
        Assert.assertEquals(6, aLong);
        Assert.assertEquals("FGStyle_Qwerty_Land", string);
        Assert.assertEquals("0.2909699", obj);

        List<String> aList = new ArrayList<>();
        aList.add("ROW1");
        aList.add("ROW2");
        aList.add("ROW3");
        aList.add("ROW4");

        Assert.assertArrayEquals(list.toArray(), aList.toArray());
        Assert.assertNotNull(map);
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
