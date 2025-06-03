package kr.h.gachon.news_application.ui;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import kr.h.gachon.news_application.R;
import kr.h.gachon.news_application.databinding.FragmentMainBinding;
import kr.h.gachon.news_application.viewmodel.SharedViewModel;

public class Fragment_main extends Fragment {

    private Fragment0 fragment0;
    private Fragment1 fragment1;
    private Fragment2 fragment2;
    private Fragment3 fragment3;
    private Fragment4 fragment4;
    private Fragment5 fragment5;
    private Fragment6 fragment6;
    private Fragment7 fragment7;
    BottomNavigationView bottomNavigationView;
    private SharedViewModel viewModel;

    public Fragment_main() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        viewModel.getLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                int number=Integer.valueOf(s).intValue();
                Menu menu = bottomNavigationView.getMenu();
                for (int i = 0; i < menu.size(); i++) {
                    menu.getItem(i).setChecked(false);
                }
                menu.getItem(number).setChecked(true);
            }
        });

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

        FragmentManager childFragment = getChildFragmentManager();
        FragmentTransaction transaction = childFragment.beginTransaction();
        transaction.replace(R.id.fragment_container_view, fragment0).commitAllowingStateLoss();

        TabLayout tabs = (TabLayout) view.findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("all"));
        tabs.addTab(tabs.newTab().setText("key1"));
        tabs.addTab(tabs.newTab().setText("key2"));
        tabs.addTab(tabs.newTab().setText("key3"));
        tabs.addTab(tabs.newTab().setText("key4"));
        tabs.addTab(tabs.newTab().setText("key5"));
        tabs.addTab(tabs.newTab().setText("key6"));
        tabs.addTab(tabs.newTab().setText("key7"));

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                FragmentManager childFragment = getChildFragmentManager();
                FragmentTransaction transaction = childFragment.beginTransaction();

                int position = tab.getPosition();

                Fragment selected = null;
                if(position == 0){
                    selected = fragment0;
                } else if (position == 1){
                    selected = fragment1;
                } else if (position == 2){
                    selected = fragment2;
                } else if (position == 3){
                    selected = fragment3;
                } else if (position == 4){
                    selected = fragment4;
                } else if (position == 5){
                    selected = fragment5;
                } else if (position == 6){
                    selected = fragment6;
                } else if (position == 7){
                    selected = fragment7;
                }

                transaction.replace(R.id.fragment_container_view, selected).commitAllowingStateLoss();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

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
    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentManager fragmentManager= getChildFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            //Menu menu = bottomNavigationView.getMenu();

            int itemId = menuItem.getItemId();
            if (itemId == R.id.all) {
                transaction.replace(R.id.fragment_container_view, fragment0).commitAllowingStateLoss();
            } else if (itemId == R.id.keyword1) {
                transaction.replace(R.id.fragment_container_view, fragment1).commitAllowingStateLoss();
            } else if (itemId == R.id.keyword2) {
                transaction.replace(R.id.fragment_container_view, fragment2).commitAllowingStateLoss();
            } else if (itemId == R.id.keyword3) {
                transaction.replace(R.id.fragment_container_view, fragment3).commitAllowingStateLoss();
            } else if (itemId == R.id.keyword4) {
                transaction.replace(R.id.fragment_container_view, fragment4).commitAllowingStateLoss();
            }

            return true;
        }
    }
}