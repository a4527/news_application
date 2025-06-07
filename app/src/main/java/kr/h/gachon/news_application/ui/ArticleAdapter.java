package kr.h.gachon.news_application.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kr.h.gachon.news_application.R;
import kr.h.gachon.news_application.databinding.ItemArticleBinding;
import kr.h.gachon.news_application.network.model.News;
import kr.h.gachon.news_application.viewmodel.ScrapViewModel;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.VH> {

    ScrapViewModel scrapViewModel;
    private List<News> items = new ArrayList<>();
    private static Context context;

    public ArticleAdapter(ScrapViewModel scrapViewModel) {
        this.scrapViewModel = scrapViewModel;
    }

    /** 외부에서 리스트 갱신할 때 호출 */
    public void submitList(List<News> list) {
        items.clear();
        if (list != null) {
            items.addAll(list);
        }
        notifyDataSetChanged();
    }

    public void addList(List<News> list) {
        Collections.shuffle(list);
        if (list != null) items.addAll(list);

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
        context = parent.getContext();
        return new VH(binding, scrapViewModel);
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
        private ScrapViewModel vm;
        private Long newsId;


        VH(ItemArticleBinding binding, ScrapViewModel vm) {
            super(binding.getRoot());
            this.binding = binding;
            this.vm = vm;
        }

        void bind(News article) {
            binding.tvTitle.setText(article.getTitle());
            binding.tvDesc.setText(article.getSummary());

            toggle_Button_scrap = binding.toggleScrap;
            toggle_Button_url = binding.toggleUrl;
            textView_url = binding.textviewUrl;

            toggle_Button_scrap.setOnClickListener(this);
            toggle_Button_url.setOnClickListener(this);
            textView_url.setOnClickListener(this);

            newsId = article.getId();

            Glide.with(binding.tvImage.getContext())
                    .load(article.getUrlimg())
                    .placeholder(R.drawable.reload)
                    .error(R.drawable.error)
                    .into(binding.tvImage);

            textView_url.setText(article.getLink());
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.toggle_scrap) {
                Toast.makeText(view.getContext(),
                        toggle_Button_scrap.isChecked() ? "This article has been scraped" : "This article has not been scraped",
                        Toast.LENGTH_SHORT).show();
                if(toggle_Button_scrap.isChecked())
                {
                    vm.addScrap(newsId, success -> {
                        ((Activity)itemView.getContext()).runOnUiThread(() -> {
                            if(!success) {
                                Log.d("ArticleAdapter", "스크랩 추가 실패");
                            } else {
                                vm.fetchScraps();
                            }
                        });
                    });
                }
                else {
                    vm.deleteScrap(newsId, success -> {
                        ((Activity)itemView.getContext()).runOnUiThread(() -> {
                            if(!success) {
                                Log.d("ArticleAdapter", "스크랩 추가 실패");
                            } else {
                                vm.fetchScraps();
                            }
                        });
                    });
                }
            }

            if (view.getId() == R.id.toggle_url) {
                textView_url.setVisibility(toggle_Button_url.isChecked() ? View.VISIBLE : View.INVISIBLE);
            }
            if (view.getId()== R.id.textview_url) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(textView_url.getText().toString()));

                Snackbar snackBar = Snackbar.make(view,textView_url.getText(),Snackbar.LENGTH_SHORT);
                snackBar.setAction("Ok", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        context.startActivity(intent);
                    }
                });
                snackBar.show();
            }
        }
    }
}
