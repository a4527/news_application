package kr.h.gachon.news_application.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import kr.h.gachon.news_application.R;
import kr.h.gachon.news_application.data.LoginDataSource;
import kr.h.gachon.news_application.data.LoginRepository;
import kr.h.gachon.news_application.data.model.LoggedInUser;
import kr.h.gachon.news_application.network.model.LoginRequest;
import kr.h.gachon.news_application.ui.Keyword.KeywordManageFragment;
import kr.h.gachon.news_application.ui.Setting.SettingsActivity;
import kr.h.gachon.news_application.ui.Setting.SettingsFragment;
import kr.h.gachon.news_application.util.TokenManager;

public class Fragment_profile extends Fragment {
    private LoggedInUser loggedInUser;
    private LoginRepository loginRepository;
    private LoginDataSource loginDataSource=null;
    private LoginRequest loginRequest;
    private boolean logged=false;

    public Fragment_profile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = (ViewGroup) inflater.inflate(R.layout.fragment_profile, container, false);

        ImageView imageView=view.findViewById(R.id.img_profile);
        imageView.setClipToOutline(true);

        TextView name=view.findViewById(R.id.textview_name);
        FrameLayout frameLayout=view.findViewById(R.id.frame_profile);
        ImageButton log_out=view.findViewById(R.id.img_botton_logout);
        TextView log_out_text=view.findViewById(R.id.textview_logout);

        FragmentManager childFragment = getChildFragmentManager();
        FragmentTransaction transaction = childFragment.beginTransaction();

        //TokenManager.getInstance(this.getActivity()).clearToken();

        boolean loggedIn = TokenManager.getInstance(getActivity()).getToken() != null;


        if (loggedIn==true) {
            //name.setText(loggedInUser.getUserId());
            //Log.d("tag", String.valueOf(loggedInUser.getUserId()));

        }

        else {name.setText("Name");}

        log_out.setOnClickListener(v -> {
            if (log_out_text.getText().toString().equals("Log Out")) {
                log_out_text.setText("Log In");
                TokenManager.getInstance(this.getActivity()).clearToken();
                Toast myToast = Toast.makeText(this.getActivity(),"Log Out", Toast.LENGTH_SHORT);
                myToast.show();
            } else {
                log_out_text.setText("Log Out");
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        ImageButton keywordButton = view.findViewById(R.id.img_botton1);
        keywordButton.setOnClickListener(v -> {
            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, new KeywordManageFragment())
                    .addToBackStack(null)
                    .commit();
        });

        ImageButton settingButton = view.findViewById(R.id.img_botton_setting);
        settingButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SettingsActivity.class);
            startActivity(intent);
        });

        return view;
    }

}