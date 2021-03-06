## Vitamin

vitamin is an universal config library in android.

## Changelog

See details in [CHANGELOG](https://github.com/lizhangqu/vitamin/blob/master/CHANGELOG.md).

## Examples

I have provided a sample.

See sample [here on Github](https://github.com/lizhangqu/vitamin/tree/master/app).

To run the sample application, simply clone this repository and use android studio to compile, install it on a connected device.

## Usage

### Dependency

**latest version**

[ ![Download](https://api.bintray.com/packages/lizhangqu/maven/vitamin/images/download.svg) ](https://bintray.com/lizhangqu/maven/vitamin/_latestVersion)


**gradle**

```
dependencies {
    //noinspection GradleCompatible
    compile "io.github.lizhangqu:vitamin:${latest_version}"
}
```

**maven**

```
<dependencies>
    <dependency>
      <groupId>io.github.lizhangqu</groupId>
      <artifactId>vitamin</artifactId>
      <version>${latest_version}</version>
    </dependency>
</dependencies>
```

### Third Party

Add third party support if you need to use.

```
    compile("org.ini4j:ini4j:0.5.4")
    compile("org.yaml:snakeyaml:1.18")
    compile("dom4j:dom4j:1.6.1")
//    you should add this for dom4j in android extension
//    dexOptions {
//        //fix for dom4j
//        additionalParameters = ['--core-library']
//    }
    compile("com.alibaba:fastjson:1.1.59.android")
    compile("com.google.code.gson:gson:2.8.0")
```

For example, if you need use ini config, you should add **compile("org.ini4j:ini4j:0.5.4")**.

### Config Type

 - yaml
 - ini
 - json
 - xml
 - properties
 - SharedPreferences
 
### Set Global Config Type If Needed

```
Vitamin.getInstance().setConfigType(ConfigType configType)
```
It's not must be required.
 
### Get ReadableConfig

```
ReadableConfig config = Vitamin.getInstance().getReadableConfig(ConfigType configType, String filePathOrName)
ReadableConfig config = Vitamin.getInstance().getReadableConfig(ConfigType configType, InputStream inputStream)
ReadableConfig config = Vitamin.getInstance().getReadableConfig(String filePathOrName)
ReadableConfig config = Vitamin.getInstance().getReadableConfig(InputStream inputStream)
```

If you not pass config param, it will get the global type first and then according to the extension about the file.

### Get config

```
String getString(String key, String defaultValue);

String getString(String key);

boolean getBoolean(String key, boolean defaultValue);

short getShort(String key, short defaultValue);

int getInt(String key, int defaultValue);

long getLong(String key, long defaultValue);

float getFloat(String key, float defaultValue);

double getDouble(String key, double defaultValue);

//may return null if not support
List<String> getList(String key, List<String> defaultValue);

//may return null if not support
List<String> getList(String key);

//may return null if not support
//may ignore key if needed
Map<String, ?> getMap(String key, Map<String, ?> defaultValue);

//may return null if not support
//may ignore key if needed
Map<String, ?> getMap(String key);

//may return null if not support
<T> T get(String key, Class<T> clazz);
```

## License

vitamin is under the BSD license. See the [LICENSE](https://github.com/lizhangqu/vitamin/blob/master/LICENSE) file for details.