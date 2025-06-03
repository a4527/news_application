package kr.h.gachon.news_application.ui;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import kr.h.gachon.news_application.R;
import kr.h.gachon.news_application.databinding.ActivityMainBinding;
import kr.h.gachon.news_application.databinding.Fragment0Binding;
import kr.h.gachon.news_application.network.model.News;
import kr.h.gachon.news_application.viewmodel.NewsViewModel;
import kr.h.gachon.news_application.viewmodel.SharedViewModel;


public class Fragment0 extends Fragment {

    private RecyclerView recyclerView;
    private ArticleAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ImageView ivLoading;
    private Animation loadingAnim;
    private NewsViewModel vm;
    private SharedViewModel viewModel;
    float touchPoint_x = 0;
    float touchPoint_y = 0;

    public Fragment0() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = (ViewGroup) inflater.inflate(R.layout.fragment_0, container, false);

        recyclerView=(RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter=new ArticleAdapter();
        recyclerView.setAdapter(adapter);

        //ivLoading=view.findViewById(R.id.ivLoading);

        vm = new ViewModelProvider(this).get(NewsViewModel.class);
        vm.getHeadlines().observe(getViewLifecycleOwner(), this::onNewsReceived);
        vm.loadHeadlines();

        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        recyclerView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                FragmentManager fragmentManager=getParentFragmentManager();
                FragmentTransaction transaction1 = fragmentManager.beginTransaction();

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        touchPoint_x = motionEvent.getX();
                        touchPoint_y = motionEvent.getY();
                        break;

                    case MotionEvent.ACTION_UP:

                        touchPoint_x = touchPoint_x - motionEvent.getX();
                        touchPoint_y = touchPoint_y - motionEvent.getY();

                        if (touchPoint_y < 50 && touchPoint_y > -50) {
                            if (touchPoint_x > 200) {
                                // 손가락을 우->좌로 움직였을때 오른쪽 화면 생성

                                Fragment1 fragment1 = new Fragment1();
                                transaction1.replace(R.id.fragment_container_view, fragment1).commitAllowingStateLoss();
                                String num="1";
                                viewModel.setLiveData(num);
                                //showLoading();
                                //vm.loadHeadlines();
                                //vm.getHeadlines().observe(this, this::onNewsReceived);
                                break;
                            }

                            if (touchPoint_x < -200) {
                                // 손가락을 좌->우로 움직였을때 왼쪽 화면 생성
                                return false;
                            }
                            break;

                        } else return false;

                    default:
                        return false;
                }
                return true;
            }
        });

        /*adapter.ToggleButtonClick(new ArticleAdapter.ButtonClickListener() {
            @Override
            public void ToggleButtonClick(View v, int positon) {

            }
        });*/

        return view;
    }

    private void onNewsReceived(List<News> newsList) {
        adapter.submitList(newsList);
    }
    private void showLoading() {
        ivLoading.setVisibility(View.VISIBLE);
        //ivLoading.startAnimation(loadingAnim);
    }

    private void hideLoading() {
        //ivLoading.clearAnimation();
        ivLoading.setVisibility(View.GONE);
    }


}