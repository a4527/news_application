package kr.h.gachon.news_application.ui;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
    private NavController navController;
    BottomNavigationView bottomNavigationView;
    View reload_icon;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        reload_icon =findViewById(R.id.reload);
        if (item.getItemId() == R.id.setting) {
            // 환경설정으로 이동
            return true;
        }
        if (item.getItemId() == R.id.reload) {
            showLoading();
            //vm.getHeadlines();
            //hideLoading();
            return true;
        } if (item.getItemId() == R.drawable.left) {
            return true;
        }

        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.left);
        toolbar.showOverflowMenu();

        loadingAnim = AnimationUtils.loadAnimation(this, R.anim.rotate);

        // frame_layout 세팅
        //adapter = new ArticleAdapter(new MyDiffUtil());
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        //transaction.replace(R.id.nav_host_fragment, fragment_main).commitAllowingStateLoss();

        // ViewModel 초기화
        //vm = new ViewModelProvider(this).get(NewsViewModel.class);

        // vm.getHeadlines().observe(this, this::onNewsReceived);
        // vm.getError().observe(this, err -> {
        // hideLoading();
        //binding.textBar.setText("Error: " + err);
        //});
        // vm.loadHeadlines();

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


    private void onNewsReceived(List<News> newsList) {
        //hideLoading();
        adapter.submitList(newsList);
        //binding.textBar.setText("총 " + newsList.size() + "개의 기사를 가져왔습니다.");
    }
    private void showLoading() {
        reload_icon.startAnimation(loadingAnim);
    }

    private void hideLoading() {
        //reload_icon.clearAnimation();
    }


}
