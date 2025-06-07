package kr.h.gachon.news_application.network;

import android.util.Log;

import java.io.IOException;

import kr.h.gachon.news_application.util.TokenManager;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private final TokenManager tokenManager;

    public AuthInterceptor(TokenManager tokenManager){this.tokenManager = tokenManager;}

    @Override
    public Response intercept(Chain chain) throws IOException{
        Request original = chain.request();
        String token = tokenManager.getToken();
        if(token != null) {
            Request authRequest = original.newBuilder().addHeader("Authorization", "Bearer " + token).build();
            Log.d("TrendFragment", token);
            return chain.proceed(authRequest);
        }
        return chain.proceed(original);
    }
}
