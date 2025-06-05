package kr.h.gachon.news_application.ui.Scrap;

import java.util.ArrayList;
import java.util.List;

public class ScrapHolder {
    private static final ScrapHolder instance = new ScrapHolder();
    private List<ScrapItem> scrapList = new ArrayList<>();

    public static ScrapHolder getInstance() {
        return instance;
    }

    public void setScrapList(List<ScrapItem> list) {
        scrapList = list;
    }

    public List<ScrapItem> getScrapList() {
        return scrapList;
    }
}
