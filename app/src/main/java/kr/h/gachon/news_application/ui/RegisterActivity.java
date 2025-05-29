package kr.h.gachon.news_application.ui;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
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
        EdgeToEdge.enable(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layout_register), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        vm = new ViewModelProvider(this).get(AuthViewModel.class);

        binding.btnRegister.setOnClickListener(view -> {
            String user = binding.enterId.getText().toString().trim();
            String email = binding.enterEmail.getText().toString().trim();
            String pass = binding.enterPassword.getText().toString().trim();
            String pass_confirm = binding.enterPasswordConfirm.toString().trim();

            if(pass != pass_confirm){
                // 경고문구
                return;
            }
            vm.register(user, pass, email);
        });

        vm.getRegisterSuccess().observe(this, msg -> {
            Toast.makeText(this, msg != null ? msg : "회원가입 성공", Toast.LENGTH_SHORT).show();
            finish();
        });

        vm.getRegisterError().observe(this, err -> {
            Toast.makeText(this, err, Toast.LENGTH_SHORT).show();
        });
    }
}