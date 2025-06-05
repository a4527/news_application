package kr.h.gachon.news_application.ui.Scrap;

import android.content.SharedPreferences;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import kr.h.gachon.news_application.R;

public class ScrapDetailPagerAdapter extends RecyclerView.Adapter<ScrapDetailPagerAdapter.DetailViewHolder> {
    private List<ScrapItem> scrapList;

    public ScrapDetailPagerAdapter(List<ScrapItem> scrapList) {
        this.scrapList = scrapList;
    }

    public static class DetailViewHolder extends RecyclerView.ViewHolder {
        TextView title, summary;
        ImageView image;

        public DetailViewHolder(View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.textFullTitle);
            summary = itemView.findViewById(R.id.textFullSummary);
            image = itemView.findViewById(R.id.imageFull);
        }
    }

    @NonNull
    @Override
    public DetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_scrap_page, parent, false);
        return new DetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailViewHolder holder, int position){
        ScrapItem item = scrapList.get(position);
        holder.title.setText(item.getTitle());
        holder.summary.setText(item.getContent());
        if(item.getImageUrl() != null){
            Glide.with(holder.image.getContext()).load(item.getImageUrl()).into(holder.image);
        }

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(holder.itemView.getContext());
        int titleSize = Integer.parseInt(prefs.getString("title_font_size", "18"));
        int contentSize = Integer.parseInt(prefs.getString("content_font_size", "14"));

        holder.title.setTextSize(TypedValue.COMPLEX_UNIT_SP, titleSize);
        holder.summary.setTextSize(TypedValue.COMPLEX_UNIT_SP, contentSize);
    }

    @Override
    public int getItemCount(){
        return scrapList.size();
    }
}
