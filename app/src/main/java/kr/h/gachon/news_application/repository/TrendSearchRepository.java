package kr.h.gachon.news_application.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import kr.h.gachon.news_application.data.model.TrendSearchDTO;
import kr.h.gachon.news_application.data.model.TrendSearchResult;
import kr.h.gachon.news_application.network.RetrofitClient;
import kr.h.gachon.news_application.network.RetrofitRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrendSearchRepository {

    private final MutableLiveData<TrendSearchResult> fullResponse = new MutableLiveData<>();

    private final MutableLiveData<String> error = new MutableLiveData<>();

    private final RetrofitRepository api;

    public TrendSearchRepository(Context context) {
        this.api = RetrofitClient.getApiService(context);
    }

    public LiveData<TrendSearchResult> getFullResponse() {return fullResponse;}

    public LiveData<String> getError() { return error;}

    public void fetchTrendSearch(String startDate, String nedDate, int topK) {
        api.getTrendSearch(startDate, nedDate, topK).enqueue(new Callback<TrendSearchResult>() {
            @Override
            public void onResponse(Call<TrendSearchResult> call, Response<TrendSearchResult> response) {
                if(response.isSuccessful() && response.body() != null) {
                    Log.d("TrendFragment", "성공");
                    fullResponse.postValue(response.body());
                } else {
                    Log.d("TrendFragment", "반응했지만 실패");
                    Log.d("TrendFragment", "응답 실패! code: " + response.code() + ", errorBody: " + response.errorBody());
                    error.postValue("트렌트 검색 실패 : HTTP" + response.code());
                }
            }

            @Override
            public void onFailure(Call<TrendSearchResult> call, Throwable t) {
                error.postValue("네트워크 오류 : " + t.getMessage());
            }
        });
    }
}
