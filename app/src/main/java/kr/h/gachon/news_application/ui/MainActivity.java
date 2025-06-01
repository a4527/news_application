package kr.h.gachon.news_application.ui;


import android.os.Bundle;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.List;
import kr.h.gachon.news_application.R;
import kr.h.gachon.news_application.databinding.ActivityMainBinding;
import kr.h.gachon.news_application.network.model.News;
import kr.h.gachon.news_application.viewmodel.NewsViewModel;
import kr.h.gachon.news_application.viewmodel.SharedViewModel;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private NewsViewModel vm;
    private ArticleAdapter adapter;
    private SharedViewModel viewModel;
    private Animation loadingAnim;
    private TextView textBar;
    private Button btn1, btn2, btn3, btn4, btn5, btn_textBar;
    private Spinner spinner;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private Fragment0 fragment0 = new Fragment0();
    private Fragment1 fragment1 = new Fragment1();
    private Fragment2 fragment2 = new Fragment2();
    private Fragment_main fragment_main = new Fragment_main();
    private Fragment_search fragment_search = new Fragment_search();
    private Fragment_profile fragment_profile = new Fragment_profile();
    float touchPoint_x = 0;
    float touchPoint_y = 0;
    int count = 0;

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


        // frame_layout 세팅
        adapter = new ArticleAdapter();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout, fragment_main).commitAllowingStateLoss();


        // ViewModel 초기화
        vm = new ViewModelProvider(this).get(NewsViewModel.class);

        vm.loadHeadlines();

        //bottom_navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.menu_bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());

        viewModel = new ViewModelProvider(this).get(SharedViewModel.class);
    }
    public SharedViewModel getViewModel() {
        return viewModel;
    }

    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            int itemId = menuItem.getItemId();
            if (itemId == R.id.main) {
                transaction.replace(R.id.frame_layout, fragment_main).commitAllowingStateLoss();
            } else if (itemId == R.id.search) {
                transaction.replace(R.id.frame_layout, fragment_search).commitAllowingStateLoss();
            } else if (itemId == R.id.profile) {
                transaction.replace(R.id.frame_layout, fragment_profile).commitAllowingStateLoss();
            }

            return true;
        }
    }


    private void onNewsReceived(List<News> newsList) {
        hideLoading();
        //ArticleAdapter adapter=new ArticleAdapter();
        adapter.submitList(newsList);
        //binding.textBar.setText("총 " + newsList.size() + "개의 기사를 가져왔습니다.");
    }
    private void showLoading() {
        //binding.ivLoading.setVisibility(View.VISIBLE);
        //binding.ivLoading.startAnimation(loadingAnim);
    }

    private void hideLoading() {
        //binding.ivLoading.clearAnimation();
        //binding.ivLoading.setVisibility(View.GONE);
    }


}
