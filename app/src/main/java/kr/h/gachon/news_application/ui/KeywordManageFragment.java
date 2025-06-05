package kr.h.gachon.news_application.ui;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

import kr.h.gachon.news_application.R;

public class KeywordManageFragment extends Fragment {
    private EditText editKeyword;
    private Button btnAdd;
    private ChipGroup chipGroup;
    private List<String> keywordList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_keyword_manage, container, false);

        editKeyword = view.findViewById(R.id.editKeyword);
        btnAdd = view.findViewById(R.id.btnAddKeyword);
        chipGroup = view.findViewById(R.id.chipGroupKeywords);

        btnAdd.setOnClickListener(v -> {
            String keyword = editKeyword.getText().toString().trim();
            if (!keyword.isEmpty() && !keywordList.contains(keyword)) {
                keywordList.add(keyword);
                addChip(keyword);
                editKeyword.setText("");
            }
        });

        return view;
    }

    private void addChip(String keyword) {
        Chip chip = new Chip(requireContext());
        chip.setText("#" + keyword);
        chip.setCloseIconVisible(true);
        chip.setChipBackgroundColorResource(R.color.chip_bg);
        chip.setTextColor(Color.WHITE);
        chip.setOnCloseIconClickListener(v -> {
            chipGroup.removeView(chip);
            keywordList.remove(keyword);
        });
        chipGroup.addView(chip);
    }
}
