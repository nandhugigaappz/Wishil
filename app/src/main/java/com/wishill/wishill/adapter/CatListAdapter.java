package com.wishill.wishill.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wishill.wishill.R;
import com.wishill.wishill.api.recommendedColleges.categories.CategoriesListData;
import com.wishill.wishill.utilities.APILinks;

import java.util.List;


public class CatListAdapter extends RecyclerView.Adapter<CatListAdapter.ViewHolder> {
    List<CategoriesListData> list;
    public ItemClickAdapterListener onClickListener;
    String imageUrl;

    public CatListAdapter(String imageUrl,List<CategoriesListData> list, Context context, ItemClickAdapterListener onClickListener) {
        this.list = list;
        this.onClickListener = onClickListener;
        this.imageUrl=imageUrl;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.e("Image URl",APILinks.IMAGE_LINK + imageUrl+list.get(position).getCategoryImg());
        Glide.with(holder.thumbnail.getContext()).load(APILinks.IMAGE_LINK + imageUrl+list.get(position).getCategoryImg())
                .crossFade()
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.thumbnail);
        holder.tvName.setText(list.get(position).getName());
        holder.llMain.setOnClickListener(new View.OnClickListener() {
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
        LinearLayout llMain;


        public ViewHolder(View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            tvName=itemView.findViewById(R.id.tv_name);
            llMain=itemView.findViewById(R.id.ll_main);
        }

    }

    public interface ItemClickAdapterListener {
        void itemClick(View v, int position);


    }
}
