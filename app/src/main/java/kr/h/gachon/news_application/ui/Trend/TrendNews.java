package kr.h.gachon.news_application.ui.Trend;

public class TrendNews {
    private String title;
    private String url;

    public TrendNews(String title, String url){
        this.title = title;
        this.url = url;
    }

    public String getTitle() {return title;}
    public String getUrl() {return url;}
}
