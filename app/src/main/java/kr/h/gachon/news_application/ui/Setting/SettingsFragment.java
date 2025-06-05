package kr.h.gachon.news_application.ui.Setting;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

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

    private SharedPreferences prefs;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());

        setupDarkMode();
        setupFontSize();

        Preference keywordPref = findPreference("keyword_manage");
        if (keywordPref != null) {
            keywordPref.setOnPreferenceClickListener(preference -> {
                NavController navController = NavHostFragment.findNavController(this);
                navController.navigate(R.id.fragment_keyword_manage); // nav_graph에 등록되어 있어야 함
                return true;
            });
        }
    }

    private void setupDarkMode(){
        SwitchPreferenceCompat darkModePref = findPreference("dark_mode");
        if(darkModePref != null){
            darkModePref.setOnPreferenceChangeListener((preference, newValue) -> {
                boolean enabled = (Boolean) newValue;
                AppCompatDelegate.setDefaultNightMode(
                        enabled ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
                );
                return true;
            });
        }
    }

    private void setupFontSize(){
        ListPreference fontSizePref = findPreference("font_size");
        if(fontSizePref != null){
            fontSizePref.setOnPreferenceChangeListener((preference, newValue) -> {
                Log.d("Settings", "폰트 크기 변경됨 : " + newValue);
                return true;
            });
        }
    }
}

