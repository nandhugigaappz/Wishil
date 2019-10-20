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
import com.wishill.wishill.api.recommendedColleges.RecommendedCollegesListData;
import com.wishill.wishill.utilities.APILinks;

import java.util.List;


public class RecommendedCollegeListAdapter extends RecyclerView.Adapter<RecommendedCollegeListAdapter.ViewHolder> {
    List<RecommendedCollegesListData> list;
    public ItemClickAdapterListener onClickListener;
    String from;

    public RecommendedCollegeListAdapter(String from,List<RecommendedCollegesListData> list, Context context, ItemClickAdapterListener onClickListener) {
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
        Glide.with(holder.thumbnail.getContext()).load(APILinks.IMAGE_LINK + list.get(position).getCollegeImage())
                .crossFade()
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.thumbnail);
        String instituteName = list.get(position).getCollegeName();
        holder.tvCollegeName.setText(instituteName.substring(0,1).toUpperCase() + instituteName.substring(1));
        holder.tvCollegeLocation.setText(list.get(position).getCity());
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.itemClick(v,position);
            }
        });
        holder.tvCollegeName.setOnClickListener(new View.OnClickListener() {
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
        TextView tvCollegeName;
        TextView tvCollegeLocation;


        public ViewHolder(View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            tvCollegeName=itemView.findViewById(R.id.tv_college_name);
            tvCollegeLocation=itemView.findViewById(R.id.tv_college_location);
        }

    }

    public interface ItemClickAdapterListener {
        void itemClick(View v, int position);


    }
}
