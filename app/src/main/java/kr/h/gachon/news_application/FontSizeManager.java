package kr.h.gachon.news_application;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.preference.PreferenceManager;

public class FontSizeManager {

    private static FontSizeManager instance;
    private MutableLiveData<FontSize> fontSizeLiveData = new MutableLiveData<>();

    private FontSizeManager(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.registerOnSharedPreferenceChangeListener((sharedPreferences, key) -> {
            if (key.equals("title_font_size") || key.equals("content_font_size")) {
                fontSizeLiveData.setValue(getFontSize(sharedPreferences));
            }
        });
        fontSizeLiveData.setValue(getFontSize(prefs));
    }

    public static FontSizeManager getInstance(Context context) {
        if (instance == null) {
            instance = new FontSizeManager(context.getApplicationContext());
        }
        return instance;
    }

    public LiveData<FontSize> getFontSizeLiveData() {
        return fontSizeLiveData;
    }

    private FontSize getFontSize(SharedPreferences prefs) {
        int titleSize = Integer.parseInt(prefs.getString("title_font_size", "18"));
        int contentSize = Integer.parseInt(prefs.getString("content_font_size", "14"));
        return new FontSize(titleSize, contentSize);
    }

    public static class FontSize {
        public int titleSize;
        public int contentSize;
        public FontSize(int titleSize, int contentSize) {
            this.titleSize = titleSize;
            this.contentSize = contentSize;
        }
    }
}
