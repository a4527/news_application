package kr.h.gachon.news_application.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Arrays;
import java.util.List;

import kr.h.gachon.news_application.R;
import kr.h.gachon.news_application.network.model.News;
import kr.h.gachon.news_application.viewmodel.SharedViewModel;

public class Fragment_main extends Fragment {
    private TabLayout tabs;
    private ViewPager2 viewPager;
    private PagerAdapter pagerAdapter;

    // 카테고리명 배열
    private final String[] categories = {
            "방송/통신", "컴퓨팅", "홈&모바일", "인터넷", "반도체/디스플레이", "게임", "과학"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        tabs = view.findViewById(R.id.tabs);
        viewPager = view.findViewById(R.id.view_pager);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setViewPager();
        setTabLayout();
    }

    private void setViewPager() {
        pagerAdapter = new PagerAdapter((AppCompatActivity) getActivity(), categories);
        viewPager.setAdapter(pagerAdapter);
    }

    private void setTabLayout() {
        new TabLayoutMediator(tabs, viewPager, (tab, position) -> {
            TextView textView = new TextView(getActivity());
            textView.setText(categories[position]);
            textView.setGravity(Gravity.CENTER);
            textView.setTextAppearance(getContext(),R.style.tabTextSizeBold);
            tab.setCustomView(textView);
        }).attach();
    }
}
