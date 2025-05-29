package kr.h.gachon.news_application.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import kr.h.gachon.news_application.R;
import kr.h.gachon.news_application.databinding.ActivityLoginBinding;
import kr.h.gachon.news_application.util.TokenManager;
import kr.h.gachon.news_application.viewmodel.AuthViewModel;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private AuthViewModel vm;

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
            Intent result = new Intent();
            result.putExtra("username", binding.enterId.getText().toString().trim());
            setResult(RESULT_OK, result);
            finish();
        });

        vm.getLoginError().observe(this, errMsg ->{
            Toast.makeText(this, errMsg, Toast.LENGTH_SHORT).show();
        });
    }
}