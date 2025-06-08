package kr.h.gachon.news_application.ui.Trend;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import kr.h.gachon.news_application.R;
import kr.h.gachon.news_application.data.model.TrendSearchDTO;
import kr.h.gachon.news_application.ui.ArticleAdapter;
import kr.h.gachon.news_application.viewmodel.ScrapViewModel;
import kr.h.gachon.news_application.viewmodel.TrendSearchViewModel;

public class TrendFragment extends Fragment {

    private Button btnSearch;
    private TextView textStartDate, textEndDate;
    private ChipGroup chipGroupKeywords;
    private View startDateBox, endDateBox;
    private RecyclerView recyclerView;
    private TrendNewsAdapter adapter;
    private Calendar startCalendar = Calendar.getInstance();
    private Calendar endCalendar = Calendar.getInstance();
    private TrendSearchViewModel vm;

    public TrendFragment() {
        // Required empty public constructor
    }


    public static TrendFragment newInstance(String param1, String param2) {
        TrendFragment fragment = new TrendFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trend, container, false);

        startDateBox = view.findViewById(R.id.startDateBox);
        endDateBox = view.findViewById(R.id.endDateBox);
        textStartDate = view.findViewById(R.id.textStartDate);
        textEndDate = view.findViewById(R.id.textEndDate);
        btnSearch = view.findViewById(R.id.btnSearch);
        recyclerView = view.findViewById(R.id.recyclerTrend);
        chipGroupKeywords = view.findViewById(R.id.chipGroupKeywords);

        adapter = new TrendNewsAdapter();//수정본

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        startDateBox.setOnClickListener(v -> showDatePicker(true));
        endDateBox.setOnClickListener(v -> showDatePicker(false));

        btnSearch.setOnClickListener(v -> fetchTrendNews());

        vm = new ViewModelProvider(this).get(TrendSearchViewModel.class);

        vm.getFullResponse().observe(
                getViewLifecycleOwner(),
                response -> {

                    if (response == null) {
                        Toast.makeText(getContext(), "데이터 로드 실패", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // 4-1) 뉴스 리스트 바인딩
                    List<TrendSearchDTO> news = response.getNews();
                    Log.d("tag", "trend"+news.toString());
                    if (news != null && !news.isEmpty()) {
                        adapter.setData(news);
                    } else {
                        Toast.makeText(getContext(), "조건에 맞는 뉴스가 없습니다.", Toast.LENGTH_SHORT).show();
                    }

                    // 4-2) 키워드 바인딩
                    List<String> kws = response.getKeywords();
                    chipGroupKeywords.removeAllViews();
                    if (kws != null && !kws.isEmpty()) {
                        chipGroupKeywords.setVisibility(View.VISIBLE);
                        for (String kw : kws) {
                            Chip chip = new Chip(requireContext());
                            chip.setText("#" + kw);
                            chip.setTextColor(Color.WHITE);
                            chip.setChipBackgroundColorResource(R.color.chip_bg);
                            chip.setClickable(false);
                            chip.setCheckable(false);
                            chipGroupKeywords.addView(chip);
                        }
                    } else {
                        chipGroupKeywords.setVisibility(View.GONE);
                    }
                }
        );

        vm.getErrorMsg().observe(
                getViewLifecycleOwner(),
                err -> {
                    if (err != null) {
                        Toast.makeText(getContext(), err, Toast.LENGTH_SHORT).show();
                    }
                }
        );

        textStartDate.setText("날짜를 설정해주세요");
        textStartDate.setTextColor(Color.GRAY);
        textEndDate.setText("날짜를 설정해주세요");
        textEndDate.setTextColor(Color.GRAY);
        return view;
    }

    private void showDatePicker(boolean isStart) {
        Calendar calendar = isStart ? startCalendar : endCalendar;
        Context context = getContext();

        DatePickerDialog dialog = new DatePickerDialog(context, (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            String formatted = formatDate(calendar);
            if (isStart) {
                textStartDate.setText(formatted);
                textStartDate.setTextColor(Color.BLACK);
            } else {
                textEndDate.setText(formatted);
                textEndDate.setTextColor(Color.BLACK);
            }

        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        dialog.getDatePicker().setMaxDate(System.currentTimeMillis()); // 미래 날짜 제한
        dialog.show();
    }

    private void fetchTrendNews() {
        if (textStartDate.getText().toString().equals("시작 날짜") ||
                textEndDate.getText().toString().equals("종료 날짜")) {
            Toast.makeText(getContext(), "시작일과 종료일을 모두 선택해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (startCalendar.after(endCalendar)) {
            Toast.makeText(getContext(), "시작일은 종료일보다 이전이어야 합니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        String startDate = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(startCalendar.getTime());
        String endDate = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(endCalendar.getTime());

        // 여기서 네트워크 요청 또는 로컬 필터링 수행
        // viewModel.loadTrendNews(startDate, endDate);

        Log.d("TrendFragment", startDate);
        Log.d("TrendFragment", endDate);
        vm.fetchTrendSearch(startDate, endDate, 10);

        chipGroupKeywords.removeAllViews(); // 이전 키워드 제거
        chipGroupKeywords.setVisibility(View.VISIBLE);

        if (vm.getFullResponse().getValue() == null || vm.getFullResponse().getValue().getKeywords() == null) {
            //Log.d("TrendFragment", vm.getFullResponse().getValue())
            Toast.makeText(getContext(), "트렌드 데이터를 불러오지 못했습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        List<String> keywords = vm.getFullResponse().getValue().getKeywords();


        for (String kw : keywords) {
            Chip chip = new Chip(requireContext());
            chip.setText("#" + kw);
            chip.setTextColor(Color.WHITE);
            chip.setChipBackgroundColorResource(R.color.chip_bg); // 예시 색상
            chip.setClickable(false);
            chip.setCheckable(false);
            chipGroupKeywords.addView(chip);
        }
    }

    private String formatDate(Calendar cal) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA);
        return sdf.format(cal.getTime());
    }
}