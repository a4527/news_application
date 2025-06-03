package kr.h.gachon.news_application.ui;


import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.List;
import kr.h.gachon.news_application.R;
import kr.h.gachon.news_application.network.model.News;
import kr.h.gachon.news_application.viewmodel.NewsViewModel;
import kr.h.gachon.news_application.viewmodel.SharedViewModel;

public class MainActivity extends AppCompatActivity {
    private NewsViewModel vm;
    private ArticleAdapter adapter;
    private SharedViewModel viewModel;
    private Animation loadingAnim;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private Fragment_main fragment_main = new Fragment_main();
    private Fragment_search fragment_search = new Fragment_search();
    private Fragment_trend fragment_trend = new Fragment_trend();
    private Fragment_profile fragment_profile = new Fragment_profile();
    private NavController navController;
    BottomNavigationView bottomNavigationView;
    float touchPoint_x = 0;
    float touchPoint_y = 0;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadingAnim = AnimationUtils.loadAnimation(this, R.anim.rotate);

        // frame_layout 세팅
        adapter = new ArticleAdapter();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //transaction.replace(R.id.nav_host_fragment, fragment_main).commitAllowingStateLoss();

        // ViewModel 초기화
        vm = new ViewModelProvider(this).get(NewsViewModel.class);

        vm.loadHeadlines();

        //bottom_navigation
        bottomNavigationView = findViewById(R.id.menu_bottom_navigation);

        viewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        setupJetpackNavigation();
    }
    public SharedViewModel getViewModel() {
        return viewModel;
    }

    private void setupJetpackNavigation() {
        NavHostFragment host = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        navController = host.getNavController();
        NavigationUI.setupWithNavController(bottomNavigationView,navController);
    }

    /*class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener {
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
    }*/


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
