package kr.h.gachon.news_application.data.model;

public class KeywordRequest {
    private String keyword;

    public KeywordRequest() { }

    public KeywordRequest(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
