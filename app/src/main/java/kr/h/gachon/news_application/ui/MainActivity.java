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
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import kr.h.gachon.news_application.R;

public class MainActivity extends AppCompatActivity {
    private Animation loadingAnim;
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
           //showLoading();
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

        toolbar.showOverflowMenu();
        toolbar.setNavigationIcon(R.drawable.left);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.popBackStack();
            }
        });

        loadingAnim = AnimationUtils.loadAnimation(this, R.anim.rotate);

        bottomNavigationView = findViewById(R.id.menu_bottom_navigation);

        setupJetpackNavigation();

    }
    private void setupJetpackNavigation() {
        NavHostFragment host = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        navController = host.getNavController();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            NavOptions navOptions = new NavOptions.Builder()
                    .setPopUpTo(R.id.nav_graph_main, true)
                    .setLaunchSingleTop(true)
                    .build();

            if (itemId == R.id.main) {
                navController.navigate(R.id.main, null, navOptions);
                return true;
            } else if (itemId == R.id.fragment_scrap) {
                navController.navigate(R.id.fragment_scrap, null, navOptions);
                return true;
            } else if (itemId == R.id.trend) {
                navController.navigate(R.id.trend, null, navOptions);
                return true;
            } else if (itemId == R.id.profile) {
                navController.navigate(R.id.profile, null, navOptions);
                return true;
            }

            return false;
        });
    }
    private void showLoading() {
        reload_icon.startAnimation(loadingAnim);
    }

    private void hideLoading() {
        //reload_icon.clearAnimation();
    }

}
