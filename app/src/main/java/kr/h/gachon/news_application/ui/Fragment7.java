package kr.h.gachon.news_application.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kr.h.gachon.news_application.R;
import kr.h.gachon.news_application.network.model.News;
import kr.h.gachon.news_application.viewmodel.NewsViewModel;
import kr.h.gachon.news_application.viewmodel.SharedViewModel;


public class Fragment7 extends Fragment {

    private RecyclerView recyclerView;
    private ArticleAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private NewsViewModel vm;
    private SharedViewModel viewModel;
    float touchPoint_x = 0;
    float touchPoint_y = 0;

    public Fragment7() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = (ViewGroup) inflater.inflate(R.layout.fragment_7, container, false);


        recyclerView=(RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter=new ArticleAdapter();
        recyclerView.setAdapter(adapter);

        vm = new ViewModelProvider(this).get(NewsViewModel.class);
        vm.getHeadlines().observe(getViewLifecycleOwner(), this::onNewsReceived);
        vm.loadHeadlines();

        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        recyclerView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int m;
                String n;
                FragmentManager fragmentManager=getParentFragmentManager();
                FragmentTransaction transaction1 = fragmentManager.beginTransaction();

                //touchEvent=new Fragment_main();
                Log.d("tag","333");
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        touchPoint_x = motionEvent.getX();
                        touchPoint_y = motionEvent.getY();
                        Log.d("tag","333");
                        break;

                    case MotionEvent.ACTION_UP:

                        touchPoint_x = touchPoint_x - motionEvent.getX();
                        touchPoint_y = touchPoint_y - motionEvent.getY();

                        if (touchPoint_y < 50 && touchPoint_y > -50) {
                            //Log.d("tag", String.valueOf(touchPoint_y));
                            if (touchPoint_x > 200) {
                                // 손가락을 우->좌로 움직였을때 오른쪽 화면 생성

                               return false;
                            }
                            if (touchPoint_x < -200) {
                                // 손가락을 좌->우로 움직였을때 왼쪽 화면 생성
                                Fragment3 fragment3 = new Fragment3();
                                transaction1.replace(R.id.fragment_container_view, fragment3).commitAllowingStateLoss();
                                String num="3";
                                viewModel.setLiveData(num);

                                break;
                            }
                            break;
                        } else return false;

                    default:
                        return false;
                }
                return true;
            }
        });

        return view;
    }
    private void onNewsReceived(List<News> newsList) {
        adapter.submitList(newsList);
    }

}