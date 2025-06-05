package kr.h.gachon.news_application.ui.Scrap;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import kr.h.gachon.news_application.FontSizeManager;
import kr.h.gachon.news_application.R;


public class ScrapDetailFragment extends Fragment {

    private ViewPager2 viewPager;
    private ScrapDetailPagerAdapter adapter;
    private List<ScrapItem> scrapList;
    private int startIndex;

    public ScrapDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scrap_detail, container, false);

        viewPager = view.findViewById(R.id.scrapViewPager);
        viewPager.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        viewPager.setPageTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                page.setAlpha(1 - Math.abs(position));
                page.setTranslationY(position * page.getHeight());
            }
        });
        scrapList = ScrapHolder.getInstance().getScrapList();
        startIndex = getArguments() != null ? getArguments().getInt("index", 0) : 0;

        adapter = new ScrapDetailPagerAdapter(scrapList);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(startIndex, false);

        FontSizeManager.getInstance(requireContext()).getFontSizeLiveData()
                .observe(getViewLifecycleOwner(), fontSize -> {
                    adapter.notifyDataSetChanged(); // 리사이클러 전체 갱신
                });

        return view;
    }
}