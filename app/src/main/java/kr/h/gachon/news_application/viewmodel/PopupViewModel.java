package kr.h.gachon.news_application.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import kr.h.gachon.news_application.network.model.News;
import kr.h.gachon.news_application.repository.PopupRepository;

public class PopupViewModel extends AndroidViewModel {

    private final PopupRepository repo;
    private final LiveData<List<News>> popupNews;
    private final LiveData<String>      error;

    public PopupViewModel(@NonNull Application application) {
        super(application);
        // Application을 Context로 넘겨줘서 Repository 생성
        repo = new PopupRepository(application);
        popupNews = repo.getPopupNewsLive();
        error     = repo.getError();
    }

    /** UI에서 관찰할 LiveData */
    public LiveData<List<News>> getPopupNews() {
        return popupNews;
    }

    public LiveData<String> getError() {
        return error;
    }

    /** 팝업 뉴스 로드 요청 */
    public void loadPopupNews() {
        repo.fetchPopupNews();
    }
}
