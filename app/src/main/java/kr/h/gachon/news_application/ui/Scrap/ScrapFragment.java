package kr.h.gachon.news_application.ui.Scrap;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import kr.h.gachon.news_application.R;
import kr.h.gachon.news_application.network.model.News;
import kr.h.gachon.news_application.viewmodel.NewsViewModel;
import kr.h.gachon.news_application.viewmodel.ScrapViewModel;

public class ScrapFragment extends Fragment {

    private RecyclerView recyclerView;
    private ScrapAdapter adapter;
    private List<ScrapItem> scrapList;

    ScrapViewModel vm;

    public ScrapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_scrap, container, false);

        recyclerView = view.findViewById(R.id.recyclerScrap);
        scrapList = new ArrayList<>();

        adapter = new ScrapAdapter(scrapList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        vm = new ViewModelProvider(this).get(ScrapViewModel.class);

        vm.getScrapList().observe(getViewLifecycleOwner(), resp -> {
            scrapList.clear();
            List<ScrapItem> itemList = new ArrayList<>();
            for(News news : vm.getScrapList().getValue())
            {
                ScrapItem newItem = new ScrapItem(news.getTitle(), news.getSummary(), news.getUrlimg());
                scrapList.add(newItem);
            }
            adapter.notifyDataSetChanged();
        });

        vm.fetchScraps();
        return view;
    }

   /* private void loadScrapData() {
        scrapList.add(new ScrapItem("스크랩 뉴스 1", "요약 1", null));
        scrapList.add(new ScrapItem("스크랩 뉴스 2", "요약 2", null));

        vm.fetchScraps();
        adapter.notifyDataSetChanged();
    }*/
}