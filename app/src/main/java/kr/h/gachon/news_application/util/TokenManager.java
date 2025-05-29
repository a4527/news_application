package kr.h.gachon.news_application.util;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenManager {
    private static final String PREF = "jwt_prefs";

    private static final String KEY_TOKEN = "access_token";

    private static TokenManager instance;
    private final SharedPreferences prefs;

    private TokenManager(Context context){
        prefs = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
    }

    public static synchronized TokenManager getInstance(Context context){
        if(instance == null){
            instance = new TokenManager(context.getApplicationContext());
        }
        return instance;
    }

    public void saveToken(String token) {prefs.edit().putString(KEY_TOKEN, token).apply();}

    public String getToken() {return prefs.getString(KEY_TOKEN, null);}

    public void clearToken() {prefs.edit().remove(KEY_TOKEN).apply();}
}
