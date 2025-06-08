package kr.h.gachon.news_application.ui.Search;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import kr.h.gachon.news_application.R;
import kr.h.gachon.news_application.data.model.SearchResult;
import kr.h.gachon.news_application.network.model.News;
import kr.h.gachon.news_application.viewmodel.SearchViewModel;

public class Fragment_search extends Fragment {
    private RecyclerView recyclerView;
    private SearchAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private SearchViewModel vm;

    public Fragment_search() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = (ViewGroup) inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView=(RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter=new SearchAdapter();
        recyclerView.setAdapter(adapter);

        Spinner spinner=view.findViewById(R.id.spinner);
        EditText editText_category=view.findViewById(R.id.edit_category);
        EditText editText_keyword=view.findViewById(R.id.edit_keyword);
        ImageButton search=view.findViewById(R.id.img_search);

        vm = new ViewModelProvider(this).get(SearchViewModel.class);

        search.setOnClickListener(v -> {
                String category = editText_category.getText().toString().trim();
                String keyword = editText_keyword.getText().toString().trim();
            if (!category.isEmpty() && !keyword.isEmpty()) {
                if (TextUtils.isEmpty(category) || TextUtils.isEmpty(keyword)) {
                    Toast.makeText(getActivity(), "카테고리와 키워드를 모두 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                vm.search(category,keyword,15);
            }
        });


        vm.getSearchResults().observe(getViewLifecycleOwner(), new Observer<List<SearchResult>>() {
            @Override
            public void onChanged(List<SearchResult> results) {
                if (results == null) return;

                List<News> onlyNews = new ArrayList<>();
                for (SearchResult sr : results) {
                    onlyNews.add(sr.getNews());
                }
                if (onlyNews.isEmpty()) {
                    Toast.makeText(getActivity(), "검색 결과가 없습니다.", Toast.LENGTH_SHORT).show();
                }
                Log.d("tag", onlyNews.toString());
                List<SearchResult> news =new ArrayList<>();
                news.addAll(results);
                Log.d("tag", "news"+news.toString());
                adapter.submitList(onlyNews);
                adapter.setData(news);
            }
        });

        String[] list = {"관련도순", "최신순", "과거순"};
        ArrayAdapter<String> Aadapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, list);
        spinner.setAdapter(Aadapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 선택된 항목의 값 얻기
                String selectedItem = (String) parent.getItemAtPosition(position);

                if (selectedItem==list[0]) {
                    adapter.sortDatahighScore();
                }

                if (selectedItem==list[1]) {
                    Log.d("tag","111");

                    adapter.sortDataDescending();
                    //adapter.notifyDataSetChanged() ;
                }

                if (selectedItem==list[2]) {
                    adapter.sortDataAscending();
                    //adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 스피너에서 아무것도 선택되지 않았을 때 처리
            }
        });

        return view;
    }

}