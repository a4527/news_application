package kr.h.gachon.news_application.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import kr.h.gachon.news_application.network.RetrofitClient;
import kr.h.gachon.news_application.network.RetrofitRepository;
import kr.h.gachon.news_application.network.model.News;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;

public class ScrapRepository {
    private final MutableLiveData<List<News>> scrapList = new MutableLiveData<>();

    private final MutableLiveData<String> error = new MutableLiveData<>();

    private final RetrofitRepository api;

    public ScrapRepository(Context context) {this.api = RetrofitClient.getApiService(context);}

    public LiveData<List<News>> getScrapList() {return scrapList;}

    public LiveData<String> getError() {return error;}

    public void fetchScraps() {
        api.getScraps().enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    scrapList.postValue(response.body());
                } else {
                    error.postValue("스크랩 목록 조회 실패 : HTTP" + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {
                error.postValue("네트워크 오류 : " + t.getMessage());
            }
        });
    }

    public void addScrap(Long newsId, final OnRequestCompleteListener listner){
        api.addScrap(newsId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()) {
                    listner.onComplete(true);
                    Log.d("ScrapRepository", "스크랩 성공");
                } else {
                    error.postValue("스크랩 추가 실패 : HTTP " + response.code());
                    Log.d("ArticleAdapter", String.valueOf(response.code()));
                    listner.onComplete(false);
                    Log.d("ScrapRepository", "스크랩 실패");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                error.postValue("네트워크 오류 : " + t.getMessage());
                listner.onComplete(false);
            }
        });
    }

    public void deleteScrap(Long newsId, final OnRequestCompleteListener listener) {
        api.deleteScrap(newsId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()) {
                    listener.onComplete(true);
                } else {
                    error.postValue("스크랩 취소 실패 : HTTP " + response.code());
                    listener.onComplete(false);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                error.postValue("네트워크 오류 : " + t.getMessage());
                listener.onComplete(false);
            }
        });
    }

    public interface OnRequestCompleteListener {
        void onComplete(boolean success);
    }
}
