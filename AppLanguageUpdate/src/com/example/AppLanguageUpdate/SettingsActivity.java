package com.example.AppLanguageUpdate;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

/**
 * Created by lz on 2015/1/14.
 * <p/>
 * DoWhat:
 */

public class SettingsActivity extends Activity implements View.OnClickListener{

    private static final String LANGUAGE = "language";

    private TextView back;
    private TextView save;
    private TextView english;
    private TextView zhongwen;

    private SharedPreferences sharedPreferences;
    private String currentLanguage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        //获取当前默认语言
        sharedPreferences = getSharedPreferences(LANGUAGE, MODE_PRIVATE);
        currentLanguage = sharedPreferences.getString(LANGUAGE, "zh");

        initView();
    }

    private void initView() {
        back = (TextView) findViewById(R.id.back);
        save = (TextView) findViewById(R.id.save);
        english = (TextView) findViewById(R.id.english);
        zhongwen = (TextView) findViewById(R.id.zhongwen);

        back.setOnClickListener(this);
        save.setOnClickListener(this);
        english.setOnClickListener(this);
        zhongwen.setOnClickListener(this);

        updateLanguageView();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.back:
                finish();
                break;
            case R.id.save:
                saveSelectedLanguage();
                gotoMain();
                break;
            case R.id.english:
                currentLanguage = "en";
                updateLanguageView();
                break;
            case R.id.zhongwen:
                currentLanguage = "zh";
                updateLanguageView();
                break;
            default:
                break;
        }
    }

    //跳到主界面，相当于重新登录
    private void gotoMain() {
        updateLanguageConfig();
        Intent to = new Intent(SettingsActivity.this, MyActivity.class);
        to.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(to);

    }

    private void updateLanguageConfig() {
        Locale locale = null;
        if ("zh".equals(currentLanguage)) {
            locale = Locale.CHINESE;
        }else if ("en".equals(currentLanguage)) {
            locale = Locale.ENGLISH;
        }

        Configuration config = getResources().getConfiguration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, null);
    }

    private void saveSelectedLanguage() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LANGUAGE, currentLanguage);
        editor.apply();
    }

    private void updateLanguageView() {
        if ("zh".equals(currentLanguage)) {
            english.setBackgroundColor(getResources().getColor(R.color.white));
            zhongwen.setBackgroundColor(getResources().getColor(R.color.half_transfer));
        }else if ("en".equals(currentLanguage)) {
            english.setBackgroundColor(getResources().getColor(R.color.half_transfer));
            zhongwen.setBackgroundColor(getResources().getColor(R.color.white));
        }
    }

}
