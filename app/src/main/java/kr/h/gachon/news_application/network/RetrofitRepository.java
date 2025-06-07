package kr.h.gachon.news_application.network;


import java.util.List;
import java.util.Map;

import kr.h.gachon.news_application.data.model.KeywordRequest;
import kr.h.gachon.news_application.data.model.SearchResult;
import kr.h.gachon.news_application.data.model.TrendSearchResult;
import kr.h.gachon.news_application.network.model.LoginRequest;
import kr.h.gachon.news_application.network.model.LoginResponse;
import kr.h.gachon.news_application.network.model.News;
import kr.h.gachon.news_application.network.model.RegisterRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.POST;

public interface RetrofitRepository{

    // 최신 기사 리스트 (예: /api/news/headline)
    @GET("/api/news/headline")
    Call<List<News>> getLatestHeadlines();

    @GET("/api/news/categoryHeadlinesAll")
    Call<Map<String, List<News>>> getAllCategoryHeadlines();

    // 검색 기반 크롤링 (예: /api/news/search?lstcode=0020&start=2025-05-10&end=2025-05-12)
    @GET("/api/news/search")
    Call<List<News>> searchNews(
            @Query("lstcode") String lstcode,
            @Query("start") String start,
            @Query("end") String end
    );

    // 전체 DB 기사 (테스트용)
    @GET("/api/news/all")
    Call<List<News>> getAllArticles();


    /** 로그인 */
    @POST("/api/login")
    Call<LoginResponse> login(@Body LoginRequest body);

    /** 회원가입 */
    @POST("/api/register")
    Call<String> register(@Body RegisterRequest body);


    /**===============프로필 관련 코드=============*/

    /** 등록된 키워드 전체 가져오기 */
    @GET("/api/profile/keywords")
    Call<List<String>> getKeywords();

    /** 키워드 추가 */
    @POST("/api/profile/keywordAdd")
    Call<Void> addKeyword(@Body KeywordRequest body);

    /** 키워드 삭제 */
    @DELETE("/api/profile/keywordDelete")
    Call<Void> deleteKeyword(@Query("keyword") String keyword);

    @GET("/api/scrap")
    Call<List<News>> getScraps();

    @POST("/api/scrap/{newsId}")
    Call<Void> addScrap(@Path("newsId") Long newsId);

    @DELETE("/api/scrap/{newsId}")
    Call<Void> deleteScrap(@Path("newsId") Long newsId);
    /**===============프로필 관련 코드=============*/

    //키워드 검색 15개 반환해주고 여기서 Date 기준으로 정렬하면 과거순/최신순 정렬 가능
    @GET("/api/news/search-by-keyword")
    Call<List<SearchResult>> searchByKeyword(
            @Query("category") String category,
            @Query("keyword") String keyword,
            @Query("topK") int topK
    );

    @GET("api/keyword/trend_search")
    Call<TrendSearchResult> getTrendSearch(
            @Query("start") String startDate,
            @Query("end") String endDate,
            @Query("topK") int topK
    );

    @GET("api/keyword/popup")
    Call<List<News>> getPopupNews();
}
