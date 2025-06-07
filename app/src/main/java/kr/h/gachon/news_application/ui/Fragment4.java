package kr.h.gachon.news_application.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

import kr.h.gachon.news_application.R;
import kr.h.gachon.news_application.network.model.News;
import kr.h.gachon.news_application.viewmodel.NewsViewModel;
import kr.h.gachon.news_application.viewmodel.ScrapViewModel;


public class Fragment4 extends Fragment {

    private RecyclerView recyclerView;
    private ArticleAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private NewsViewModel vm;

    public Fragment4() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = (ViewGroup) inflater.inflate(R.layout.fragment_4, container, false);

        recyclerView=(RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter=new ArticleAdapter(new ViewModelProvider(this).get(ScrapViewModel.class));
        recyclerView.setAdapter(adapter);

        vm = new ViewModelProvider(this).get(NewsViewModel.class);
        vm.getCategoryHeadlines().observe(getViewLifecycleOwner(), this::onCategoryNewsReceived);
        vm.loadCategoryHeadlines();

        return view;
    }
    private void onCategoryNewsReceived(Map<String, List<News>> newsMap) {
        if (newsMap != null && newsMap.containsKey("인터넷")) {
            List<News> list = newsMap.get("인터넷");
            adapter.submitList(list);
        }
    }

}