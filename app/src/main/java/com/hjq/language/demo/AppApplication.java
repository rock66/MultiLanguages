package com.hjq.language.demo;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;

import androidx.annotation.NonNull;

import com.hjq.language.MultiLanguages;
import com.hjq.language.OnLanguageListener;
import com.hjq.toast.ToastUtils;

import java.util.Locale;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/MultiLanguages
 *    time   : 2019/08/10
 *    desc   : 应用入口
 */
public final class AppApplication extends Application {
    private Context mOriginalContext;
    private static final String TAG = "AppApplication";
    @Override
    public void onCreate() {
        super.onCreate();
        ToastUtils.init(this);

        // 初始化多语种框架（自动适配第三方库中 Activity 语种）
        MultiLanguages.init(this);
        // 设置语种变化监听器
        MultiLanguages.setOnLanguageListener(new OnLanguageListener() {

            @Override
            public void onAppLocaleChange(Locale oldLocale, Locale newLocale) {
                Log.d("MultiLanguages", "监听到应用切换了语种，旧语种：" + oldLocale + "，新语种：" + newLocale);
            }

            @Override
            public void onSystemLocaleChange(Locale oldLocale, Locale newLocale) {
                Log.d("MultiLanguages", "监听到系统切换了语种，旧语种：" + oldLocale + "，新语种：" + newLocale + "，是否跟随系统：" + MultiLanguages.isSystemLanguage());
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        mOriginalContext = newBase;
        // 绑定语种
        super.attachBaseContext(MultiLanguages.attach(newBase));
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {

        //fix 夜间模式不生效、横竖屏切换宽高获取错误
        getResources().updateConfiguration(newConfig, mOriginalContext.getResources().getDisplayMetrics());

        super.onConfigurationChanged(newConfig);

        int w = getResources().getDisplayMetrics().widthPixels;
        int h = getResources().getDisplayMetrics().heightPixels;

        Log.e(TAG,"onConfigurationChanged======== w : " + w + " h : " + h);

        w = getApplicationContext().getResources().getDisplayMetrics().widthPixels;
        h = getApplicationContext().getResources().getDisplayMetrics().heightPixels;

        Log.e(TAG,"onConfigurationChanged w : " + w + " h : " + h);
    }
}