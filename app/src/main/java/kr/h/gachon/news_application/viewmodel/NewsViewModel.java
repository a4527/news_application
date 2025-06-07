package kr.h.gachon.news_application.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import kr.h.gachon.news_application.network.model.News;
import kr.h.gachon.news_application.repository.NewsRepository;
import java.util.List;
import java.util.Map;

public class NewsViewModel extends AndroidViewModel {

    private final NewsRepository repo;
    private final LiveData<List<News>> headlines;
    private final LiveData<String>    error;
    private final LiveData<Map<String, List<News>>> categoryHeadlines;

    public NewsViewModel(@NonNull Application app) {
        super(app);
        // Application을 Context로 넘겨 줌
        repo = new NewsRepository(app);
        headlines = repo.getHeadlines();
        error     = repo.getError();
        categoryHeadlines = repo.getCategoryHeadlines();
    }

    public LiveData<List<News>> getHeadlines() {
        return headlines;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void loadHeadlines() {
        repo.fetchHeadlines();
    }

    public LiveData<Map<String, List<News>>> getCategoryHeadlines() {
        return repo.getCategoryHeadlines();
    }
    public void loadCategoryHeadlines() {
        repo.fetchCategoryHeadlines();
    }
}
