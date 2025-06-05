package kr.h.gachon.news_application.ui;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


// URL 이미지용
import com.bumptech.glide.Glide;
import kr.h.gachon.news_application.R;

import kr.h.gachon.news_application.databinding.ItemArticleBinding;
import kr.h.gachon.news_application.network.model.News;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.VH> {

    private static List<News> items = new ArrayList<>();
    private List<String> arr = Arrays.asList("Recent","방송/통신","컴퓨팅","홈&모바일","인터넷","반도체\n/디스플레이","카테크","헬스케어");
    private static String string;
    private String selectedCategory=null;
    private List<News> filteredItems = new ArrayList<>();

    /** 외부에서 리스트 갱신할 때 호출 */
    public void submitList(List<News> list) {
        items.clear();
        if (list != null) items.addAll(list);
        filterByCategory(selectedCategory); // 항상 필터 적용
        notifyDataSetChanged();
    }

    public void category(int position) {
        if (arr.get(position).equals(string)) {
            Log.d("tag","11111");
            //items.
        } else {items.remove(position);}
    }
    /** 카테고리 필터링 함수 */
    public void filterByCategory(String category) {
        selectedCategory = category;
        if (category==null|| category.equals("Recent")) {
            filteredItems = new ArrayList<>(items);
        } else {
            filteredItems = new ArrayList<>();
            for (News n : items) {
                if (n.getCategory() != null && n.getCategory().equals(category)) {
                    filteredItems.add(n);
                }
            }
        }
        notifyDataSetChanged();
    }



    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemArticleBinding binding = ItemArticleBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new VH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.bind(filteredItems.get(position));
    }

    @Override
    public int getItemCount() {
        return filteredItems.size();
    }

    static class VH extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ItemArticleBinding binding;
        private ToggleButton toggle_Button_scrap;
        private ToggleButton toggle_Button_url;
        TextView textView_url;
        TextView textView_category;
        TextView textView_keyword;

        VH(ItemArticleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(News article) {
            binding.tvTitle.setText(article.getTitle());
            //binding.tvDesc.setText(article.getDescription());

            toggle_Button_scrap=binding.toggleScrap;
            toggle_Button_url = binding.toggleUrl;
            textView_url=binding.textviewUrl;
            textView_category=binding.textviewCategory;
            textView_keyword=binding.textviewKeyword;

            toggle_Button_scrap.setOnClickListener(this);
            toggle_Button_url.setOnClickListener(this);
            textView_url.setOnClickListener(this);

            // Glide로 URL에서 이미지 로드
            Glide.with(binding.tvImage.getContext())
                    .load(article.getUrlimg())      // String URL
                    .placeholder(R.drawable.reload) // 로딩 중 대체 이미지
                    .error(R.drawable.error)           // 실패 시 대체 이미지
                    .into(binding.tvImage);

            binding.tvDesc.setText(article.getSummary());

            textView_url.setText(article.getLink());
            textView_category.setText(article.getCategory());
            textView_keyword.setText(article.getKeyword());

            //string=article.getCategory();
            //items.remove(getAdapterPosition());
        }

        @Override
        public void onClick(View view) {
            if (view.getId()== R.id.toggle_scrap) {
                if (toggle_Button_scrap.isChecked()){
                    Toast toast = Toast.makeText(view.getContext(), "This article has been scraped", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(view.getContext(), "This article has not been scraped", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
            if (view.getId()== R.id.toggle_url) {
                if (toggle_Button_url.isChecked()){
                    textView_url.setVisibility(View.VISIBLE);
                } else {
                    textView_url.setVisibility(View.INVISIBLE);
                }
            }
        }
    }
}
