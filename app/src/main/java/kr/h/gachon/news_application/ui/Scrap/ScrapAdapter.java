package kr.h.gachon.news_application.ui.Scrap;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import kr.h.gachon.news_application.R;


public class ScrapAdapter extends RecyclerView.Adapter<ScrapAdapter.ScrapViewHolder> {

    private List<ScrapItem> scrapList;

    public ScrapAdapter(List<ScrapItem> scrapList) {
        this.scrapList = scrapList;
    }

    public static class ScrapViewHolder extends RecyclerView.ViewHolder {
        TextView title, content;
        ImageView thumhnail;

        public ScrapViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textTitle);
            content = itemView.findViewById(R.id.textSummary);
            thumhnail = itemView.findViewById(R.id.imageThumbnail);
        }
    }

    @NonNull
    @Override
    public ScrapViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_scrap, parent, false);
        return new ScrapViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScrapViewHolder holder, int position){
        ScrapItem item = scrapList.get(position);
        holder.title.setText(item.getTitle());
        holder.content.setText(item.getContent());

        if(item.getImageUrl() != null && !item.getImageUrl().isEmpty()) {
            Glide.with(holder.thumhnail.getContext())
                    .load(item.getImageUrl())
                    .into(holder.thumhnail);
        }
        else{
            holder.thumhnail.setImageResource(android.R.color.darker_gray);
        }

        holder.itemView.setOnClickListener(v -> {
            ScrapHolder.getInstance().setScrapList(scrapList);

            Bundle bundle = new Bundle();
            bundle.putInt("index", position);
            Navigation.findNavController(v).navigate(R.id.scrapDetailFragment, bundle);
        });
    }

    @Override
    public int getItemCount(){
        return scrapList.size();
    }
}
