package io.github.lizhangqu.vitamin;

import android.content.Context;
import android.content.SharedPreferences;

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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 功能介绍
 *
 * @author lizhangqu
 * @version V1.0
 * @since 2017-07-19 09:44
 */
@Config(sdk = 23)
@RunWith(RobolectricTestRunner.class)
public class SharePreferencesConfigTest {

    private static final String NAME = "test";
    private static final Logger logger = Logger.getLogger(SharePreferencesConfigTest.class);


    @BeforeClass
    public static void setupClass() {
        logger.setLevel(Level.ALL);
        logger.addAppender(new ConsoleAppender(new PatternLayout("%-6r [%p] %c - %m%n")));
    }

    @Before
    public void setup() {
        Context context = ContextProvider.getContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        Set<String> set = new HashSet<>();
        set.add("123");
        set.add("456");
        set.add("789");
        sharedPreferences.edit()
                .putFloat("float", 11.12f)
                .putFloat("double", 1234.121f)
                .putInt("int", 2)
                .putInt("short", 32)
                .putLong("long", 1234532134)
                .putBoolean("boolean", true)
                .putString("string", "just a string")
                .putStringSet("list", set)
                .apply();
    }

    @Test
    @Config(manifest = Config.NONE, shadows = {ContextProviderShadow.class})
    public void testGet() {
        ReadableConfig config = Vitamin.getInstance().getReadableConfig(ConfigType.PREFERENCES, NAME);

        int anInt = config.getInt("int", -1);
        Assert.assertEquals(2, anInt);

        short aShort = config.getShort("short", (short) -1);
        Assert.assertEquals(32, aShort);

        long aLong = config.getLong("long", 121);
        Assert.assertEquals(1234532134, aLong);

        boolean aBoolean = config.getBoolean("boolean", false);
        Assert.assertEquals(true, aBoolean);

        float aFloat = config.getFloat("float", -1f);
        Assert.assertEquals(11.12f, aFloat, 1);

        double aDouble = config.getDouble("double", -1f);
        Assert.assertEquals(1234.121f, aDouble, 1);

        String string = config.getString("string", null);
        Assert.assertEquals("just a string", string);

        List<?> list = config.getList("list", null);
        List<String> set = new ArrayList<>();
        set.add("123");
        set.add("456");
        set.add("789");
        Assert.assertArrayEquals(list.toArray(), set.toArray());

        logger.debug("int:" + anInt);
        logger.debug("short:" + aShort);
        logger.debug("long:" + aLong);
        logger.debug("boolean:" + aBoolean);
        logger.debug("float:" + aFloat);
        logger.debug("double:" + aDouble);
        logger.debug("string:" + string);
        logger.debug("list:" + list);
    }

    @Test
    @Config(manifest = Config.NONE, shadows = {ContextProviderShadow.class})
    public void testUnSupport() {
        ReadableConfig config = Vitamin.getInstance().getReadableConfig(ConfigType.PREFERENCES, NAME);
        Exception exception = null;
        try {
            Map obj = config.get("obj", Map.class);
        } catch (UnsupportedOperationException e) {
            exception = e;
        }

        Assert.assertNotNull(exception);
        logger.debug(exception);
        exception = null;
        try {
            Map obj = config.getMap("map");
        } catch (UnsupportedOperationException e) {
            exception = e;
        }

        Assert.assertNotNull(exception);
        logger.debug(exception);
    }
}
