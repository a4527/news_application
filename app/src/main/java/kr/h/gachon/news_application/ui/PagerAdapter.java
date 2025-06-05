package kr.h.gachon.news_application.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class PagerAdapter extends FragmentStateAdapter {
    private  String[] categories = {
            "방송/통신", "컴퓨팅", "홈&모바일", "인터넷", "반도체/디스플레이", "게임", "과학"
    };

    public PagerAdapter(AppCompatActivity activity, String[] categories) {
        super(activity);
        this.categories = categories;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new Fragment0();
            case 1: return new Fragment1(); // 방송/통신
            case 2: return new Fragment2(); // 컴퓨팅
            case 3: return new Fragment3();
            case 4: return new Fragment4();
            case 5: return new Fragment5();
            case 6: return new Fragment6();
            case 7: return new Fragment7(); // 필요하다면
            default: throw new IllegalArgumentException("Invalid position: " + position);
        }
    }

    @Override
    public int getItemCount() {
        return categories.length;
    }
}
