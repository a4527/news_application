package kr.h.gachon.news_application.ui;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import kr.h.gachon.news_application.R;
import kr.h.gachon.news_application.databinding.ActivityLoginBinding;
import kr.h.gachon.news_application.network.model.News;
import kr.h.gachon.news_application.util.TokenManager;
import kr.h.gachon.news_application.viewmodel.AuthViewModel;
import kr.h.gachon.news_application.viewmodel.PopupViewModel;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private AuthViewModel vm;

    private PopupViewModel popupViewModel;

    private static final String CHANNEL_ID ="popup_news_channel";
    private static final int  NOTIFICATION_ID=1001;
    private static final int    REQ_POST_NOTIFICATIONS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layout_login), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        vm = new ViewModelProvider(this).get(AuthViewModel.class);
        popupViewModel=new ViewModelProvider(this).get(PopupViewModel.class);

        //createNotificationChannel();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        REQ_POST_NOTIFICATIONS
                );
            } else {
                // 이미 권한이 허용된 상태면 채널 생성
                createNotificationChannel();
            }
        } else {
            // Android 12 이하에서는 권한 없이 바로 채널 생성
            createNotificationChannel();
        }


        binding.btnLogin.setOnClickListener(v -> {
            vm.login(binding.enterId.getText().toString(), binding.enterPassword.getText().toString());
        });

        binding.btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });

        binding.logoImage.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        vm.getLoginSuccess().observe(this, resp ->{
            TokenManager.getInstance(this).saveToken(resp.getAccessToken());
            popupViewModel.loadPopupNews();
            Intent result = new Intent(LoginActivity.this, MainActivity.class);
            result.putExtra("username", binding.enterId.getText().toString().trim());
            setResult(RESULT_OK, result);
            startActivity(result);
            finish();
        });

        vm.getLoginError().observe(this, errMsg ->{
            Toast.makeText(this, errMsg, Toast.LENGTH_SHORT).show();
        });

        popupViewModel.getPopupNews().observe(this,newsList->{
            if(newsList!=null&&!newsList.isEmpty()){
                Log.d("LoginActivity", "팝업 뉴스 개수: " + newsList.size());
                for (News n : newsList) {
                    Log.d("LoginActivity", "  • " + n.getTitle());
                }
                showNotification(newsList);
            }
        });

        popupViewModel.getError().observe(this,err->{
            if(err!=null){
                Toast.makeText(this,"팝업 뉴스 로드 실패: "+ err, Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQ_POST_NOTIFICATIONS) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 권한을 허용했으면 NotificationChannel 생성
                createNotificationChannel();
            } else {
                // 권한 거부 시 토스트 안내(선택)
                Toast.makeText(this,
                        "알림 권한이 필요합니다. 설정에서 권한을 허용해주세요.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            CharSequence name        = "Popup News Channel";
            String description       = "로그인 이후 해당 유저의 팝업 뉴스를 표시하는 채널";
            int importance           = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            if(notificationManager!=null){
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    /** Notification으로 팝업 뉴스 제목을 띄우는 메서드 */
    /**private void showNotification(List<News> newsList) {
        Log.d("LoginActivity", "showNotification 실행됨, newsList 크기=" + newsList.size());
        // …Builder 생성, manager.notify() 호출…
        NotificationManager manager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // 최대 5건의 제목만 보여주고, 나머지는 “외 n건…” 형태로 축약
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < Math.min(5, newsList.size()); i++) {
            content.append("• ").append(newsList.get(i).getTitle()).append("\n");
        }
        if (newsList.size() > 5) {
            content.append("외 ").append(newsList.size() - 5).append("건...");
        }

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_notificaion) // 프로젝트에 있는 아이콘으로 교체
                        .setContentTitle("새로운 관련 뉴스가 있습니다")
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(content.toString()))
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setAutoCancel(true);
        Log.d("LoginActivity","알림 보내기 직전");
        manager.notify(NOTIFICATION_ID, builder.build());
        Log.d("LoginActivity","알림 메뉴에 notify() 호출 완료");
    }*/
    private void showNotification(List<News> newsList) {
        Log.d("LoginActivity", "showNotification 실행됨, newsList 크기=" + newsList.size());

        NotificationManager manager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // 알림 채널이 없을 경우 미리 생성된다 가정

        // 예시: 최대 5개 뉴스만 개별 알림으로 띄우기
        int maxToNotify = Math.min(5, newsList.size());
        for (int i = 0; i < maxToNotify; i++) {
            News newsItem = newsList.get(i);
            String title = newsItem.getTitle();
            String url   = newsItem.getLink();  // 클릭 시 열 URL

            Log.d("LoginActivity", "알림 생성: [" + i + "] " + title + " → " + url);

            // ① 브라우저로 URL 열기 위한 Intent
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            PendingIntent pendingIntent = PendingIntent.getActivity(
                    this,
                    i,  // requestCode를 인덱스로 달리 줘서 서로 다른 PendingIntent 생성
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                            | (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                            ? PendingIntent.FLAG_IMMUTABLE
                            : 0)
            );

            // ② NotificationCompat.Builder로 개별 알림 구성
            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(this, CHANNEL_ID)
                            .setSmallIcon(R.drawable.ic_notificaion)
                            .setContentTitle(title)  // 각 뉴스 제목을 알림 제목으로
                            .setContentText(newsItem.getDescription() != null
                                    ? newsItem.getDescription()
                                    : "터치하여 자세히 보기")
                            .setStyle(new NotificationCompat.BigTextStyle().bigText(
                                    newsItem.getDescription() != null
                                            ? newsItem.getDescription()
                                            : url))
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setAutoCancel(true)
                            .setContentIntent(pendingIntent);

            // ③ 서로 다른 알림 ID를 주면 개별 알림으로 표시된다
            int notifyId = NOTIFICATION_ID + i;
            Log.d("LoginActivity", "알림 보내기 직전: notifyId=" + notifyId);
            if (manager != null) {
                manager.notify(notifyId, builder.build());
                Log.d("LoginActivity", "알림 전송 완료: notifyId=" + notifyId);
            }
        }
    }

}