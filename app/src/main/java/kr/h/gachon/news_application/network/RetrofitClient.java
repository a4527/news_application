package kr.h.gachon.news_application.network;

import android.content.Context;

import kr.h.gachon.news_application.util.TokenManager;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

//retrofit2 json 파일 형식을 받아오기 위해서 network 연결을 retrofit2 library를 사용함
public class RetrofitClient {
    private static final String BASE_URL = "http://newsnap.onrender.com/";

    private static Retrofit retrofit;

    private static Retrofit getRetrofit(Context context){
        if(retrofit == null){
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            AuthInterceptor authInterceptor = new AuthInterceptor(TokenManager.getInstance(context));

            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(authInterceptor).addInterceptor(logging).build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client) // ← 생성한 client 사용
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    public static RetrofitRepository getApiService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(RetrofitRepository.class);
    }

    public static RetrofitRepository getApiService(Context context){
        return getRetrofit(context).create(RetrofitRepository.class);
    }
}
