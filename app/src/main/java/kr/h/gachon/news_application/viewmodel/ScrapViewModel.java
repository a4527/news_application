package kr.h.gachon.news_application.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.Objects;

import kr.h.gachon.news_application.network.model.News;
import kr.h.gachon.news_application.repository.ScrapRepository;

public class ScrapViewModel extends AndroidViewModel {

    private final ScrapRepository repository;

    private final LiveData<List<News>> scrapList;
    private final LiveData<String> errorMsg;

    private final MutableLiveData<String> internalError;

    public ScrapViewModel(@NonNull Application application) {
        super(application);
        repository = new ScrapRepository(application.getApplicationContext());

        scrapList = repository.getScrapList();
        errorMsg = repository.getError();

        internalError = new MutableLiveData<>();

        repository.fetchScraps();
    }

    public LiveData<List<News>> getScrapList() {return scrapList;}

    public LiveData<String> getErrorMsg() {return errorMsg;}

    public void fetchScraps() {repository.fetchScraps();}

    public void addScrap(Long newsId, final OnRequestCompleteListener listener) {
        //Log.d("ScrapViewModel", String.valueOf(scrapList.getValue().size()));
        repository.addScrap(newsId, new ScrapRepository.OnRequestCompleteListener() {
            @Override
            public void onComplete(boolean success) {listener.onComplete(success);}
        });
        //Log.d("ScrapViewModel", String.valueOf(scrapList.getValue().size()));
    }

    public void deleteScrap(Long newsId, final OnRequestCompleteListener listener) {
        repository.deleteScrap(newsId, new ScrapRepository.OnRequestCompleteListener() {
            @Override
            public void onComplete(boolean success) {listener.onComplete(success);}
        });
    }

    public boolean isScrapped(Long newsId) {
        List<News> scrapedList = scrapList.getValue();

        if(scrapedList == null) {return false;}

        Log.d("Scrap", String.valueOf(scrapedList.size()));
        for (News news: scrapedList) {
            if(Objects.equals(news.getId(), newsId)) {
                return true;
            }
        }
        return false;
    }
    public interface OnRequestCompleteListener {
        void onComplete(boolean success);
    }
}
