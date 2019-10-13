package com.wishill.wishill.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wishill.wishill.R;
import com.wishill.wishill.api.recommendedColleges.toprankingColleges.TopRankingCollegesListData;
import com.wishill.wishill.utilities.APILinks;

import java.util.List;


public class TopRankingCollegeListAdapter extends RecyclerView.Adapter<TopRankingCollegeListAdapter.ViewHolder> {
    List<TopRankingCollegesListData> list;
    public ItemClickAdapterListener onClickListener;
    String from;

    public TopRankingCollegeListAdapter(String from,List<TopRankingCollegesListData> list, Context context, ItemClickAdapterListener onClickListener) {
        this.list = list;
        this.onClickListener = onClickListener;
        this.from=from;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_item_recommended_colleges, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Glide.with(holder.thumbnail.getContext()).load(APILinks.IMAGE_LINK + list.get(position).getCollegeImg())
                .crossFade()
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.thumbnail);
        String instituteName = list.get(position).getCollegeName();
        holder.tvName.setText(instituteName.substring(0,1).toUpperCase() + instituteName.substring(1));
        holder.tvLocation.setText(list.get(position).getCity());
        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.itemClick(v,position);
            }
        });
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.itemClick(v,position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        TextView tvName;
        TextView tvLocation;
        public ViewHolder(View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            tvName=itemView.findViewById(R.id.tv_college_name);
            tvLocation=itemView.findViewById(R.id.tv_college_location);
        }
    }

    public interface ItemClickAdapterListener {
        void itemClick(View v, int position);


    }
}
