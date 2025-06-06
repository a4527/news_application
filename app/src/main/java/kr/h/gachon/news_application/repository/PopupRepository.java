package kr.h.gachon.news_application.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import kr.h.gachon.news_application.network.RetrofitClient;
import kr.h.gachon.news_application.network.RetrofitRepository;
import kr.h.gachon.news_application.network.model.News;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopupRepository {

    private final MutableLiveData<List<News>> popupNews = new MutableLiveData<>();
    private final MutableLiveData<String> error      = new MutableLiveData<>();
    private final RetrofitRepository api;

    // Context를 받아 RetrofitService 생성 (AuthInterceptor가 포함되어 있다고 가정)
    public PopupRepository(Context context) {
        this.api = RetrofitClient.getApiService(context);
    }

    /** LiveData 구독용 Getter */
    public LiveData<List<News>> getPopupNewsLive() {
        return popupNews;
    }

    public LiveData<String> getError() {
        return error;
    }

    /** 서버에서 팝업 뉴스를 가져오는 메서드 */
    public void fetchPopupNews() {
        Log.d("PopupRepo", "fetchPopupNews() 호출됨");  // ①
        api.getPopupNews()
                .enqueue(new Callback<List<News>>() {
                    @Override
                    public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                        Log.d("PopupRepo", "onResponse(): code=" + response.code());  // ②
                        if (response.isSuccessful() && response.body() != null) {
                            List<News> list = response.body();
                            Log.d("PopupRepo", "받아온 뉴스 개수=" + list.size());
                            popupNews.postValue(response.body());

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
}
