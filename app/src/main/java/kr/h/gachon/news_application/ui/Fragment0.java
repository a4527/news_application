package kr.h.gachon.news_application.ui;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Map;

import kr.h.gachon.news_application.R;
import kr.h.gachon.news_application.network.model.News;
import kr.h.gachon.news_application.viewmodel.NewsViewModel;
import kr.h.gachon.news_application.viewmodel.ScrapViewModel;
import okhttp3.internal.http2.Http2Reader;


public class Fragment0 extends Fragment {
    private  String[] categories = {
            "방송/통신", "컴퓨팅", "홈&모바일", "인터넷", "반도체/디스플레이", "게임", "과학"
    };
    private RecyclerView recyclerView;
    private ArticleAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ImageView ivLoading;
    private Animation loadingAnim;
    private NewsViewModel vm;

    public Fragment0() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = (ViewGroup) inflater.inflate(R.layout.fragment_0, container, false);

        recyclerView=(RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter=new ArticleAdapter(new ViewModelProvider(this).get(ScrapViewModel.class));
        recyclerView.setAdapter(adapter);

        vm = new ViewModelProvider(this).get(NewsViewModel.class);
        vm.getCategoryHeadlines().observe(getViewLifecycleOwner(), this::onCategoryNewsReceived);
        vm.loadCategoryHeadlines();

        FloatingActionButton fab_down = view.findViewById(R.id.fab_down);
        FloatingActionButton fab_up = view.findViewById(R.id.fab_up);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private boolean isScrolledUp = false;
            private boolean isScrolledDown = false;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_DRAGGING && isScrolledDown) {
                    fab_up.setVisibility(View.INVISIBLE);
                    fab_down.setVisibility(View.VISIBLE);

                }
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING && isScrolledUp) {
                    fab_down.setVisibility(View.INVISIBLE);
                    fab_up.setVisibility(View.VISIBLE);

                }
                if (newState==RecyclerView.SCROLL_STATE_IDLE) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fab_up.setVisibility(View.INVISIBLE);
                            fab_down.setVisibility(View.INVISIBLE);
                        }
                    }, 5000);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                isScrolledUp = dy < 0;
                isScrolledDown = dy > 0;
            }
        });

        fab_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.smoothScrollToPosition(0);
            }
        });

        fab_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.smoothScrollToPosition(adapter.getItemCount());
            }
        });

        return view;
    }

    private void onCategoryNewsReceived(Map<String, List<News>> newsMap) {
        for (String key : newsMap.keySet()) {
            System.out.println("카테고리 키: " + key + " / 뉴스 개수: " + newsMap.get(key).size());
        }
        for (String key:categories) {
            if (newsMap != null && newsMap.containsKey(key)) {
                List<News> list = newsMap.get(key);
                for (News news : list) {
                    System.out.println("뉴스 제목: " + news.getTitle() + " / 카테고리: " + news.getCategory());
                }
                adapter.addList(list);
            }
        }
    }
}