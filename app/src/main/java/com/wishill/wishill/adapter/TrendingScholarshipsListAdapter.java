package com.wishill.wishill.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wishill.wishill.R;
import com.wishill.wishill.api.recommendedColleges.trendingScholerships.TrendingScholarshipsListData;

import java.util.List;


public class TrendingScholarshipsListAdapter extends RecyclerView.Adapter<TrendingScholarshipsListAdapter.ViewHolder> {
    List<TrendingScholarshipsListData> list;
    public ItemClickAdapterListener onClickListener;

    public TrendingScholarshipsListAdapter(List<TrendingScholarshipsListData> list, Context context, ItemClickAdapterListener onClickListener) {
        this.list = list;
        this.onClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_item_trending_scholarships, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
    /*    Glide.with(holder.thumbnail.getContext()).load(APILinks.IMAGE_LINK + list.get(position).getLogoimage())
                .crossFade()
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.thumbnail);
        holder.tvName.setText(list.get(position).getPackagename());
        holder.tvLocation.setText(list.get(position).getLocation());*/
        holder.tvName.setText(list.get(position).getScholarshipname());
        holder.tvLocation.setText(list.get(position).getLocation());
        holder.tvAward.setText(list.get(position).getAward());
        holder.tvCat.setText(list.get(position).getScholarshipcategory());
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

       /* ImageView thumbnail;*/
        TextView tvName;
        TextView tvLocation;
        TextView tvCat;
        TextView tvAward;
        LinearLayout llMain;


        public ViewHolder(View itemView) {
            super(itemView);
          /*  thumbnail = itemView.findViewById(R.id.thumbnail);*/
            tvName=itemView.findViewById(R.id.tv_name);
            tvLocation=itemView.findViewById(R.id.tv_location);
            tvCat=itemView.findViewById(R.id.tv_cat);
            tvAward=itemView.findViewById(R.id.tv_award);
            llMain=itemView.findViewById(R.id.ll_main);
        }

    }

    public interface ItemClickAdapterListener {
        void itemClick(View v, int position);


    }
}
