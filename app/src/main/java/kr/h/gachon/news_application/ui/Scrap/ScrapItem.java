package kr.h.gachon.news_application.ui.Scrap;

public class ScrapItem {
    private String title;
    private String content;
    private String imageUrl;

    public ScrapItem(String title, String content, String imageUrl){
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {return title;}
    public String getContent() {return content;}
    public String getImageUrl() {return imageUrl;}

}
