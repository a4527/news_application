package kr.h.gachon.news_application.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class PagerAdapter extends FragmentStateAdapter {
    private final ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private Fragment0 fragment0;
    private Fragment1 fragment1;
    private Fragment2 fragment2;
    private Fragment3 fragment3;
    private Fragment4 fragment4;
    private Fragment5 fragment5;
    private Fragment6 fragment6;
    private Fragment7 fragment7;
    public PagerAdapter(AppCompatActivity activity) {
        super(activity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        if (position == 0) {
            fragment0 = new Fragment0();
            addFragment(fragment0);
        } else if (position == 1) {
            fragment1 = new Fragment1();
            addFragment(fragment1);
        } else if (position == 2) {
            fragment2 = new Fragment2();
            addFragment(fragment2);
        } else if (position == 3) {
            fragment3 = new Fragment3();
            addFragment(fragment3);
        } else if (position == 4) {
            fragment4 = new Fragment4();
            addFragment(fragment4);
        } else if (position == 5) {
            fragment5 = new Fragment5();
            addFragment(fragment5);
        } else if (position == 6) {
            fragment6 = new Fragment6();
            addFragment(fragment6);
        } else if (position == 7) {
            fragment7 = new Fragment7();
            addFragment(fragment7);
        } else {
            return null;
        }

        return mFragmentList.get(position);

    }

    public void addFragment(Fragment fragment){

        mFragmentList.add(fragment);

    }

    @Override
    public int getItemCount() {
        return mFragmentList.size();
    }
}
