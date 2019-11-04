package com.wishill.wishill.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
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


public class RecommendedCollegeListAdapterSub extends RecyclerView.Adapter<RecommendedCollegeListAdapterSub.ViewHolder> {
    List<RecommendedCollegesListData> list;
    public ItemClickAdapterListener onClickListener;
    String from;

    public RecommendedCollegeListAdapterSub(String from, List<RecommendedCollegesListData> list, Context context, ItemClickAdapterListener onClickListener) {
        this.list = list;
        this.onClickListener = onClickListener;
        this.from=from;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_item_recommended_colleges_sub, parent, false);
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
        holder.tvCollegeName.setText(list.get(position).getCollegeName());
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
        holder.ivCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.itemCall(view,position);
            }
        });
        holder.tvSendEnq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.sendEnq(view,position);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
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
        TextView tvCollegeName;
        TextView tvCollegeLocation;
        ImageView ivCall;
        TextView tvSendEnq;


        public ViewHolder(View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            tvCollegeName=itemView.findViewById(R.id.tv_college_name);
            tvCollegeLocation=itemView.findViewById(R.id.tv_college_location);
            ivCall=itemView.findViewById(R.id.iv_call);
            tvSendEnq=itemView.findViewById(R.id.tv_send_enq);
        }

    }

    public interface ItemClickAdapterListener {
        void itemClick(View v, int position);
        void itemCall(View v, int position);
        void sendEnq(View v, int position);


    }
}
