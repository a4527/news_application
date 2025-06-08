package kr.h.gachon.news_application.ui.Setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.preference.Preference;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceViewHolder;

import kr.h.gachon.news_application.R;


public class SeekBarPreference extends Preference {

    private int defaultValue = 16;

    public SeekBarPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayoutResource(R.layout.preference_seekbar);  // 반드시 존재해야 함
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);

        TextView label = (TextView) holder.findViewById(R.id.label);
        SeekBar seekBar = (SeekBar) holder.findViewById(R.id.seekbar);

        if (label == null || seekBar == null) return; // layout 연결 안 됐을 경우 대비

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        int value;
        try {
            value = prefs.getInt(getKey(), defaultValue); // 시도 1: int로 가져오기
        } catch (ClassCastException e) {
            // 시도 2: String으로 저장되어 있었으면 파싱
            try {
                value = Integer.parseInt(prefs.getString(getKey(), String.valueOf(defaultValue)));
                prefs.edit().remove(getKey()).putInt(getKey(), value).apply(); // 기존 String 제거하고 int로 덮어쓰기
            } catch (Exception ex) {
                value = defaultValue; // 최악의 경우 fallback
            }
        }

        label.setText(getTitle() + " : " + value);
        seekBar.setProgress(value);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int newValue, boolean fromUser) {
                label.setText(getTitle() + " : " + newValue);
                prefs.edit().putInt(getKey(), newValue).apply();
            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }
}

