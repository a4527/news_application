package kr.h.gachon.news_application.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import kr.h.gachon.news_application.R;

public class Fragment_profile extends Fragment {

    public Fragment_profile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = (ViewGroup) inflater.inflate(R.layout.fragment_profile, container, false);

        ImageView imageView=view.findViewById(R.id.img_profile);
        imageView.setClipToOutline(true);

        FrameLayout frameLayout=view.findViewById(R.id.frame_profile);
        ImageButton imageButton_scrap=view.findViewById(R.id.img_botton1);

        FragmentManager childFragment = getChildFragmentManager();
        FragmentTransaction transaction = childFragment.beginTransaction();

       /* imageButton_scrap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transaction.replace(R.id.frame_profile, fragment_scrap).commitAllowingStateLoss();
            }
        });*/

        //transaction.replace(R.id.fragment_container_view, fragment0).commitAllowingStateLoss();

        return view;
    }

}