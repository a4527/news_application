package kr.h.gachon.news_application.ui;

import android.os.Bundle;
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

import java.util.List;
import java.util.Map;

import kr.h.gachon.news_application.R;
import kr.h.gachon.news_application.network.model.News;
import kr.h.gachon.news_application.viewmodel.NewsViewModel;


public class Fragment0 extends Fragment {

    private RecyclerView recyclerView;
    private ArticleAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ImageView ivLoading;
    private Animation loadingAnim;
    private NewsViewModel vm;
    int i=1;

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

        adapter=new ArticleAdapter();
        recyclerView.setAdapter(adapter);

        //ivLoading=view.findViewById(R.id.ivLoading);

        vm = new ViewModelProvider(this).get(NewsViewModel.class);
        vm.getCategoryHeadlines().observe(getViewLifecycleOwner(), this::onCategoryNewsReceived);
        vm.loadCategoryHeadlines();
        //vm.getHeadlines();
        //Log.d("tag","000");
        //adapter.category(i);







        /*adapter.ToggleButtonClick(new ArticleAdapter.ButtonClickListener() {
            @Override
            public void ToggleButtonClick(View v, int positon) {

            }
        });*/

        return view;
    }

    private void onCategoryNewsReceived(Map<String, List<News>> newsMap) {
        for (String key : newsMap.keySet()) {
            System.out.println("카테고리 키: " + key + " / 뉴스 개수: " + newsMap.get(key).size());
        }
        if (newsMap != null && newsMap.containsKey("방송/통신")) {
            List<News> list = newsMap.get("방송/통신");
            for (News news : list) {
                System.out.println("뉴스 제목: " + news.getTitle() + " / 카테고리: " + news.getCategory());
            }
            adapter.submitList(list);
        }
    }
    private void showLoading() {
        //ivLoading.setVisibility(View.VISIBLE);
        //ivLoading.startAnimation(loadingAnim);
    }

    private void hideLoading() {
        //ivLoading.clearAnimation();
        //ivLoading.setVisibility(View.GONE);
    }


}