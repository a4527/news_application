package kr.h.gachon.news_application.ui.Scrap;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import kr.h.gachon.news_application.R;

public class ScrapFragment extends Fragment {

    private RecyclerView recyclerView;
    private ScrapAdapter adapter;
    private List<ScrapItem> scrapList;

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

        loadScrapData();

        return view;
    }

    private void loadScrapData() {
        scrapList.add(new ScrapItem("스크랩 뉴스 1", "요약 1", null));
        scrapList.add(new ScrapItem("스크랩 뉴스 2", "요약 2", null));
        adapter.notifyDataSetChanged();
    }
}