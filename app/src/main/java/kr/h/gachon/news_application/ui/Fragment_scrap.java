package kr.h.gachon.news_application.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import kr.h.gachon.news_application.R;


public class Fragment_scrap extends Fragment {
    private RecyclerView recyclerView;
    private ArticleAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public Fragment_scrap() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = (ViewGroup) inflater.inflate(R.layout.fragment_scrap, container, false);

        recyclerView=(RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter=new ArticleAdapter();
        recyclerView.setAdapter(adapter);

        return view;
    }
}