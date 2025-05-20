package kr.h.gachon.news_application.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.ComponentActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import kr.h.gachon.news_application.R;
import kr.h.gachon.news_application.databinding.ActivityMainBinding;
import kr.h.gachon.news_application.network.model.News;
import kr.h.gachon.news_application.viewmodel.NewsViewModel;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private NewsViewModel vm;
    private ArticleAdapter adapter;
    private MyAdapter0 madapter;
    private Animation loadingAnim;
    private TextView textBar;
    private Button btn1, btn2, btn3, btn4, btn5, btn_textBar;
    private Spinner spinner;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private Fragment0 fragment0 = new Fragment0();
    private Fragment1 fragment1 = new Fragment1();
    private Fragment2 fragment2 = new Fragment2();
    float touchPoint_x = 0;
    float touchPoint_y = 0;
    int count=0;


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        int userAction = ev.getAction();
        switch (userAction) {
            case MotionEvent.ACTION_DOWN:
                touchPoint_x = ev.getX();
                touchPoint_y = ev.getY();
                break;

            case MotionEvent.ACTION_UP:

                touchPoint_x = touchPoint_x - ev.getX();
                touchPoint_y = touchPoint_y - ev.getY();

                if (touchPoint_y<50) {
                    if (touchPoint_x > 0) {
                        // 손가락을 우->좌로 움직였을때 오른쪽 화면 생성
                        if (count == 0) {
                            transaction.replace(R.id.nav_host_fragment, fragment1).commitAllowingStateLoss();
                            count++;
                            break;
                        }
                        if (count == 1) {
                            transaction.replace(R.id.nav_host_fragment, fragment2).commitAllowingStateLoss();
                            count++;
                            break;
                        }

                    } else {
                        // 손가락을 좌->우로 움직였을때 왼쪽 화면 생성
                        if (count == 1) {
                            transaction.replace(R.id.nav_host_fragment, fragment0).commitAllowingStateLoss();
                            count--;
                            break;
                        }
                        if (count == 2) {
                            transaction.replace(R.id.nav_host_fragment, fragment1).commitAllowingStateLoss();
                            count--;
                            break;
                        }
                    }
                    break;
                }

            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadingAnim = AnimationUtils.loadAnimation(this, R.anim.rotate);

        textBar = findViewById(R.id.textBar);
        btn1 = findViewById(R.id.button1);
        btn2 = findViewById(R.id.button2);
        btn3 = findViewById(R.id.button3);
        btn4 = findViewById(R.id.button4);
        btn5 = findViewById(R.id.button5);
        btn_textBar = findViewById(R.id.btn_textBar);
        spinner = findViewById(R.id.spinner);

        // RecyclerView 세팅
        //adapter = new ArticleAdapter();
        //binding.rvArticles.setLayoutManager(new LinearLayoutManager(this));
        //binding.rvArticles.setAdapter(adapter);



        // fragment_container_view 세팅
        adapter = new ArticleAdapter();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment0).commitAllowingStateLoss();


        // ViewModel 초기화

    }

}
