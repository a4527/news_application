package kr.h.gachon.news_application.ui.Search;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import kr.h.gachon.news_application.R;
import kr.h.gachon.news_application.data.model.SearchResult;
import kr.h.gachon.news_application.network.model.News;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>{
    private final List<News> items = new ArrayList<>();
    private List<SearchResult> items1 = new ArrayList<>();
    private List<Search> list1 = new ArrayList<>();
    private SearchResult item1;

    public void submitList(List<News> list) {
        items.clear();
        if (list != null) {
            items.addAll(list);
        }
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_search, parent, false);
        return new SearchAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder holder, int position) {
        final News item = items.get(position);

        item1 = items1.get(position);
        //SearchDTO result=searchResults.get(position);
        holder.tvTitle.setText(item.getTitle());
        holder.tvScore.setText(String.format("유사도: %.3f", item1.getScore()));

        holder.tvLink.setText(item.getLink());
        holder.tvDate.setText(item.getDate());
        holder.tvLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = item.getLink();
                if (url != null && !url.isEmpty()) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    view.getContext().startActivity(i);
                } else {
                    Toast.makeText(view.getContext(), "유효하지 않은 링크입니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        // items가 null이면 0, 아니면 크기 반환
        return (items == null) ? 0 : items.size();
    }

    public void setData(List<SearchResult> list) {
        this.items1 = list;
        notifyDataSetChanged();
    }

    public void sortDatahighScore() {
        initData();
        Collections.sort(items1, new Comparator<SearchResult>() {
            @Override
            public int compare(SearchResult a, SearchResult b) {

                return  (int) (b.getScore()-a.getScore());
            }
        });
        notifyDataSetChanged();
    }


    public void sortDataDescending() {
        initData();
        Collections.sort(items, new Comparator<News>() {
            @Override
            public int compare(News a, News b) {
                if(b.getDate().compareTo(a.getDate())==0){
                    return b.getId().compareTo(a.getId());
                }
                return b.getDate().compareTo(a.getDate());
            }

        });
        notifyDataSetChanged();
    }

    public void sortDataAscending() {
        initData();
        Collections.sort(items, new Comparator<News>() {
            @Override
            public int compare(News a, News b) {
                if(a.getDate().compareTo(b.getDate())==0){
                   return a.getId().compareTo(b.getId());
                }

                return a.getDate().compareTo(b.getDate());
            }

        });
        notifyDataSetChanged();
    }

    public void initData() {
        for (int i=0;i<items.size();i++) {
            list1.add(new Search(items.get(i).getId(),items.get(i).getDate()));
        }

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvLink, tvScore, tvDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvItemTitle);
            tvLink  = itemView.findViewById(R.id.tvItemLink);
            tvScore = itemView.findViewById(R.id.tvItemScore);
            tvDate = itemView.findViewById(R.id.tvItemDate);
        }
    }
}
class Search {
    private Long id;
    private String value;

    public Search(Long id, String value) {
        this.id = id;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public String getValue() {
        return value;
    }
}