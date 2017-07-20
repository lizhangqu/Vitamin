package sample.lizhangqu.github.io.vitamin;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.github.lizhangqu.vitamin.ConfigType;
import io.github.lizhangqu.vitamin.ReadableConfig;
import io.github.lizhangqu.vitamin.Vitamin;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    InputStream inputStream = MainActivity.this.getAssets().open("test.ini");
                    ReadableConfig config = Vitamin.getInstance().getReadableConfig(ConfigType.INI, inputStream);
                    List<String> list = config.getList("Keyboard.ROWS");
                    Log.e("TAG", "ini list:" + list);

                    inputStream = MainActivity.this.getAssets().open("test.yaml");
                    config = Vitamin.getInstance().getReadableConfig(ConfigType.YAML, inputStream);
                    list = config.getList("list");
                    Log.e("TAG", "yaml list:" + list);

                    inputStream = MainActivity.this.getAssets().open("test.xml");
                    config = Vitamin.getInstance().getReadableConfig(ConfigType.XML, inputStream);
                    list = config.getList("list");
                    Log.e("TAG", "xml list:" + list);

                    inputStream = MainActivity.this.getAssets().open("test.json");
                    config = Vitamin.getInstance().getReadableConfig(ConfigType.JSON, inputStream);
                    list = config.getList("list");
                    Log.e("TAG", "json list:" + list);

                    inputStream = MainActivity.this.getAssets().open("test.properties");
                    config = Vitamin.getInstance().getReadableConfig(ConfigType.PROPERTIES, inputStream);
                    list = config.getList("list");
                    Log.e("TAG", "properties list:" + list);

                    SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences("test", Context.MODE_PRIVATE);
                    Set<String> set = new HashSet<String>();
                    set.add("1");
                    set.add("2");
                    set.add("3");
                    sharedPreferences.edit().putStringSet("list", set).apply();

                    config = Vitamin.getInstance().getReadableConfig(ConfigType.PREFERENCES, "test");
                    list = config.getList("list");
                    Log.e("TAG", "sharedPreferences list:" + list);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
