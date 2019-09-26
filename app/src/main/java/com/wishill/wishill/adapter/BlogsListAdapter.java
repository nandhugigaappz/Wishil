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
import com.wishill.wishill.api.recommendedColleges.attractiveStudyTour.AttractiveStudyTourListData;
import com.wishill.wishill.api.recommendedColleges.storieslist.StoriesListData;
import com.wishill.wishill.utilities.APILinks;

import java.util.List;


public class BlogsListAdapter extends RecyclerView.Adapter<BlogsListAdapter.ViewHolder> {
    List<StoriesListData> list;
    public ItemClickAdapterListener onClickListener;

    public BlogsListAdapter(List<StoriesListData> list, Context context, ItemClickAdapterListener onClickListener) {
        this.list = list;
        this.onClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_item_attractive_tour, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Glide.with(holder.thumbnail.getContext()).load(APILinks.IMAGE_LINK + list.get(position).getBlogImg())
                .crossFade()
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.thumbnail);
        holder.tvName.setText(list.get(position).getBlogTitle());
        holder.tvLocation.setVisibility(View.GONE);
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.itemClick(view,position);
            }
        });
        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.itemClick(view,position);
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
            tvName=itemView.findViewById(R.id.tv_name);
            tvLocation=itemView.findViewById(R.id.tv_location);
        }

    }

    public interface ItemClickAdapterListener {
        void itemClick(View v, int position);
        
    }
}
