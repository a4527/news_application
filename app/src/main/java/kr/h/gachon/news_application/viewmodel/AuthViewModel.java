package kr.h.gachon.news_application.viewmodel;

import android.app.Application;
import android.widget.MultiAutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;

import kr.h.gachon.news_application.network.model.LoginResponse;
import kr.h.gachon.news_application.repository.AuthRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthViewModel extends AndroidViewModel {
    private final AuthRepository repository;

    private final MutableLiveData<LoginResponse> loginSuccess = new MutableLiveData<>();
    private final MutableLiveData<String> loginError = new MutableLiveData<>();

    private final MutableLiveData<String> registerSuccess = new MutableLiveData<>();
    private final MutableLiveData<String> registerError = new MutableLiveData<>();

    public AuthViewModel(@NonNull Application application)
    {
        super(application);
        repository = new AuthRepository(application);
    }

    public LiveData<LoginResponse> getLoginSuccess() {return loginSuccess;}
    public LiveData<String> getLoginError() {return loginError;}

    public LiveData<String> getRegisterSuccess() {return registerSuccess;}
    public LiveData<String> getRegisterError() {return registerError;}

    public void login(String userName, String password){
        repository.login(userName, password, new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    loginSuccess.postValue(response.body());
                }
                else{
                    loginError.postValue("로그인 실패 : 서버 응답 코드" + response.code());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                loginError.postValue("네트워크 오류 : " + t.getMessage());
            }
        });
    }

    public void register(String userName, String password, String email) {
        repository.register(userName, password, email, new Callback<String>(){
            @Override public void onResponse(Call<String> call, Response<String> res){
                if(res.isSuccessful()&& res.body() != null){
                    registerSuccess.postValue(res.body());
                } else{
                    String errMsg = "회원가입 실패 : 코드 " + res.code();
                    try{
                        if(res.errorBody() != null) {
                            errMsg = res.errorBody().string();
                        }
                    }
                    catch (IOException e){

                    }
                    registerError.postValue(errMsg);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable){
                registerError.postValue("네트워크 오류 : " + throwable.getMessage());
            }
        });
    }
}
