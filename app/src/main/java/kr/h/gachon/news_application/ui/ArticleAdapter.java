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

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import kr.h.gachon.news_application.R;
import kr.h.gachon.news_application.databinding.ItemArticleBinding;
import kr.h.gachon.news_application.network.model.News;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.VH> {

    private List<News> items = new ArrayList<>();

    /** 외부에서 리스트 갱신할 때 호출 */
    public void submitList(List<News> list) {
        items.clear();
        if (list != null) {
            items.addAll(list);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
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
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class VH extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ItemArticleBinding binding;
        private ToggleButton toggle_Button_scrap;
        private ToggleButton toggle_Button_url;
        private TextView textView_url;
        private TextView textView_category;
        private TextView textView_keyword;

        VH(ItemArticleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(News article) {
            binding.tvTitle.setText(article.getTitle());
            binding.tvDesc.setText(article.getSummary());

            toggle_Button_scrap = binding.toggleScrap;
            toggle_Button_url = binding.toggleUrl;
            textView_url = binding.textviewUrl;
            textView_category = binding.textviewCategory;
            textView_keyword = binding.textviewKeyword;

            toggle_Button_scrap.setOnClickListener(this);
            toggle_Button_url.setOnClickListener(this);
            textView_url.setOnClickListener(this);

            Glide.with(binding.tvImage.getContext())
                    .load(article.getUrlimg())
                    .placeholder(R.drawable.reload)
                    .error(R.drawable.error)
                    .into(binding.tvImage);

            textView_url.setText(article.getLink());
            textView_category.setText(article.getCategory());
            textView_keyword.setText(article.getKeyword());
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.toggle_scrap) {
                Toast.makeText(view.getContext(),
                        toggle_Button_scrap.isChecked() ? "This article has been scraped" : "This article has not been scraped",
                        Toast.LENGTH_SHORT).show();
            }

            if (view.getId() == R.id.toggle_url) {
                textView_url.setVisibility(toggle_Button_url.isChecked() ? View.VISIBLE : View.INVISIBLE);
            }
        }
    }
}
