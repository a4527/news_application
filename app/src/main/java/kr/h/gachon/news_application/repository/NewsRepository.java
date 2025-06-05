package kr.h.gachon.news_application.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.List;
import java.util.Map;

import kr.h.gachon.news_application.network.RetrofitClient;
import kr.h.gachon.news_application.network.RetrofitRepository;
import kr.h.gachon.news_application.network.model.News;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {

    private final MutableLiveData<List<News>> headlines = new MutableLiveData<>();

    private final MutableLiveData<Map<String, List<News>>> categoryHeadlines = new MutableLiveData<>();
    private final MutableLiveData<String> error     = new MutableLiveData<>();
    private final RetrofitRepository api;

    // ★ Context를 받아서 RetrofitService를 생성
    public NewsRepository(Context context) {
        this.api = RetrofitClient.getApiService(context);
    }

    public LiveData<List<News>> getHeadlines() {
        return headlines;
    }

    public LiveData<Map<String, List<News>>> getCategoryHeadlines() {
        return categoryHeadlines;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void fetchHeadlines() {
        api.getLatestHeadlines()
                .enqueue(new Callback<List<News>>() {
                    @Override
                    public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            headlines.postValue(response.body());
                        } else {
                            error.postValue("Error: " + response.code());
                        }
                    }
                    @Override
                    public void onFailure(Call<List<News>> call, Throwable t) {
                        error.postValue(t.getMessage());
                    }
                });
    }

    public void fetchCategoryHeadlines() {
        api.getAllCategoryHeadlines()
                .enqueue(new Callback<Map<String, List<News>>>() {
                    @Override
                    public void onResponse(Call<Map<String, List<News>>> call, Response<Map<String, List<News>>> response) {
                        Log.d("NewsRepo", "response: " + response.body());
                        if (response.isSuccessful() && response.body() != null) {
                            categoryHeadlines.postValue(response.body());
                        } else {
                            error.postValue("Error: " + response.code());
                        }
                    }
                    @Override
                    public void onFailure(Call<Map<String, List<News>>> call, Throwable t) {
                        error.postValue(t.getMessage());
                    }
                });
    }


}
