package kr.h.gachon.news_application.ui.Setting;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreferenceCompat;

import kr.h.gachon.news_application.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        /*setupSeekBar("title_font_size", "제목 폰트 크기");
        setupSeekBar("content_font_size", "본문 폰트 크기");*/
        setupDarkMode();
    }

    /*private void setupSeekBar(String key, String label) {
        Preference p = findPreference(key);
        if (p instanceof SeekBarPreference) {
            SeekBarPreference sb = (SeekBarPreference) p;
            sb.setOnBindViewHolderListener(holder -> {
                TextView labelView = holder.findViewById(R.id.label);
                SeekBar seekBar = holder.findViewById(R.id.seekbar);

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
                int value = prefs.getInt(key, key.contains("title") ? 24 : 16);

                labelView.setText(label + " : " + value);
                seekBar.setProgress(value);

                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int value, boolean fromUser) {
                        labelView.setText(label + " : " + value);
                        prefs.edit().putInt(key, value).apply();
                    }

                    public void onStartTrackingTouch(SeekBar seekBar) {}
                    public void onStopTrackingTouch(SeekBar seekBar) {}
                });
            });
        }
    }*/

    private void setupDarkMode() {
        SwitchPreferenceCompat darkModePref = findPreference("dark_mode");
        if (darkModePref != null) {
            darkModePref.setOnPreferenceChangeListener((preference, newValue) -> {
                AppCompatDelegate.setDefaultNightMode((Boolean) newValue ?
                        AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
                return true;
            });
        }
    }
}


