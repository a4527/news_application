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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import kr.h.gachon.news_application.R;
import kr.h.gachon.news_application.databinding.FragmentMainBinding;
import kr.h.gachon.news_application.viewmodel.SharedViewModel;

public class Fragment_main extends Fragment {

    private FragmentManager childFragment;
    private Fragment0 fragment0 = new Fragment0();
    private Fragment1 fragment1 = new Fragment1();
    private Fragment2 fragment2 = new Fragment2();
    private Fragment3 fragment3 = new Fragment3();
    private Fragment4 fragment4 = new Fragment4();
    BottomNavigationView bottomNavigationView;
    private SharedViewModel viewModel;
    float touchPoint_x = 0;
    float touchPoint_y = 0;
    int count = 0;

    public Fragment_main() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);
        //mainBinding= FragmentMainBinding.inflate(inflater);

        //FragmentTransaction transaction = fragmentManager.beginTransaction();
        //transaction.replace(R.id.fragment_container_view, fragment0).commitAllowingStateLoss();

        //FragmentContainerView fragmentContainerView=(FragmentContainerView) view.findViewById(R.id.fragment_container_view);

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

        childFragment = getChildFragmentManager();
        FragmentTransaction transaction = childFragment.beginTransaction();
        transaction.replace(R.id.fragment_container_view, fragment0).commitAllowingStateLoss();

        bottomNavigationView = view.findViewById(R.id.menu_top_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());

        //FragmentContainerView fragmentContainerView=(FragmentContainerView) view.findViewById(R.id.fragment_container_view);
        //fragmentContainerView.setOnTouchListener(this);
        Log.d("tag","111");

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