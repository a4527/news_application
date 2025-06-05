package kr.h.gachon.news_application.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import kr.h.gachon.news_application.R;
import kr.h.gachon.news_application.databinding.ActivityRegisterBinding;
import kr.h.gachon.news_application.network.model.RegisterRequest;
import kr.h.gachon.news_application.viewmodel.AuthViewModel;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private AuthViewModel vm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layout_register), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        vm = new ViewModelProvider(this).get(AuthViewModel.class);

        binding.btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        binding.btnRegister.setOnClickListener(view -> {
            String user = binding.enterId.getText().toString().trim();
            String email = binding.enterEmail.getText().toString().trim();
            String pass = binding.enterPassword.getText().toString().trim();
            String pass_confirm = binding.enterPasswordConfirm.getText().toString().trim();

            if(!pass.equals(pass_confirm)){
                Log.d("password", pass);
                Log.d("password_confirm", pass_confirm);
                Toast.makeText(this, "비밀번호 오류", Toast.LENGTH_SHORT).show();
                return;
            }
            vm.register(user, pass, email);
        });

        binding.btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        vm.getRegisterSuccess().observe(this, msg -> {
            Toast.makeText(this, msg != null ? msg : "회원가입 성공", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        vm.getRegisterError().observe(this, err -> {
            Toast.makeText(this, "회원가입 실패", Toast.LENGTH_SHORT).show();
        });
    }
}