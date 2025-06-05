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
    private Fragment0 fragment0;
    private Fragment1 fragment1;
    private Fragment2 fragment2;
    private Fragment3 fragment3;
    private Fragment4 fragment4;
    private Fragment5 fragment5;
    private Fragment6 fragment6;
    private Fragment7 fragment7;
    private TabLayout.Tab tab1;
    private TabLayout.Tab tab2;
    private TabLayout.Tab tab3;
    private TabLayout.Tab tab4;
    private TabLayout.Tab tab5;
    private TabLayout.Tab tab6;
    private TabLayout.Tab tab7;
    private TabLayout.Tab tab8;
    private NavController navController;
    BottomNavigationView bottomNavigationView;
    private SharedViewModel viewModel;
    private ViewPager2 viewPager;
    private PagerAdapter pagerAdapter;

    public Fragment_main() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);


        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fragment0 = new Fragment0();
        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();
        fragment4 = new Fragment4();
        fragment5 = new Fragment5();
        fragment6 = new Fragment6();
        fragment7 = new Fragment7();



        tabs = (TabLayout) view.findViewById(R.id.tabs);





        viewPager = view.findViewById(R.id.view_pager);

        setViewPager();
        setTabLayout();

        tabs.setTabGravity(TabLayout.GRAVITY_START);



        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab){
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab){
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab){
            }

        });

    }


    private void showLoading() {
        //binding.ivLoading.setVisibility(View.VISIBLE);
        //binding.ivLoading.startAnimation(loadingAnim);
    }

    private void hideLoading() {
        //binding.ivLoading.clearAnimation();
        //binding.ivLoading.setVisibility(View.GONE);
    }
    private void setViewPager(){

        pagerAdapter = new PagerAdapter((AppCompatActivity) getActivity());
        pagerAdapter.createFragment(0);
        pagerAdapter.createFragment(1);
        pagerAdapter.createFragment(2);
        pagerAdapter.createFragment(3);
        pagerAdapter.createFragment(4);
        pagerAdapter.createFragment(5);
        pagerAdapter.createFragment(6);
        pagerAdapter.createFragment(7);

        viewPager.setAdapter(pagerAdapter);
    }

    private void setTabLayout(){
        final List<String> tabElement = Arrays.asList("Recent","방송/통신","컴퓨팅","홈&모바일","인터넷","반도체\n/디스플레이","카테크","헬스케어");
        new TabLayoutMediator(tabs, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                TextView textView = new TextView(getActivity());
                textView.setText(tabElement.get(position));
                textView.setGravity(Gravity.CENTER);
                textView.setTextAppearance(R.style.tabTextSizeBold);
                tab.setCustomView(textView);

            }
        }).attach();

    }

}