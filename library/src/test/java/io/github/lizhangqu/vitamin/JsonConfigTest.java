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
