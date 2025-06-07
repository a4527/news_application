package kr.h.gachon.news_application.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

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
    }

    public LiveData<List<News>> getScrapList() {return scrapList;}

    public LiveData<String> getErrorMsg() {return errorMsg;}

    public void fetchScraps() {repository.fetchScraps();}

    public void addScrap(Long newsId, final OnRequestCompleteListener listener) {
        repository.addScrap(newsId, new ScrapRepository.OnRequestCompleteListener() {
            @Override
            public void onComplete(boolean success) {listener.onComplete(success);}
        });
    }

    public void deleteScrap(Long newsId, final OnRequestCompleteListener listener) {
        repository.deleteScrap(newsId, new ScrapRepository.OnRequestCompleteListener() {
            @Override
            public void onComplete(boolean success) {listener.onComplete(success);}
        });
    }

    public interface OnRequestCompleteListener {
        void onComplete(boolean success);
    }
}
