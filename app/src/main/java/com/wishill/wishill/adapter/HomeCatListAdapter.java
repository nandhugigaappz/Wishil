package com.wishill.wishill.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.wishill.wishill.R;
import com.wishill.wishill.datamodel.HomeItemsModel;

import java.util.List;


public class HomeCatListAdapter extends RecyclerView.Adapter<HomeCatListAdapter.ViewHolder> {
    List<HomeItemsModel> list;
    public ItemClickAdapterListener onClickListener;

    public HomeCatListAdapter(List<HomeItemsModel> list, Context context, ItemClickAdapterListener onClickListener) {
        this.list = list;
        this.onClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_item_style, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.thumbnail.setImageResource(list.get(position).getCategoryIcon());
        holder.tvCat.setText(list.get(position).getCategoryName());
        if(list.get(position).getStatus().equals("1")){
            holder.tvComingSoon.setVisibility(View.GONE);
        }else{
            holder.tvComingSoon.setVisibility(View.VISIBLE);
        }

        holder.rlMain.setOnClickListener(new View.OnClickListener() {
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
        TextView tvCat;
        RelativeLayout rlMain;
        TextView tvComingSoon;


        public ViewHolder(View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            tvCat=itemView.findViewById(R.id.tv_cat);
            rlMain=itemView.findViewById(R.id.rl_main);
            tvComingSoon=itemView.findViewById(R.id.tv_coming_soon);
        }

    }

    public interface ItemClickAdapterListener {
        void itemClick(View v, int position);


    }
}
