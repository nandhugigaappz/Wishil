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
import com.wishill.wishill.api.recommendedColleges.studyTourList.StudyTourListData;
import com.wishill.wishill.utilities.APILinks;

import java.util.List;


public class StudyTourListAdapter extends RecyclerView.Adapter<StudyTourListAdapter.ViewHolder> {
    List<StudyTourListData> list;
    public ItemClickAdapterListener onClickListener;

    public StudyTourListAdapter(List<StudyTourListData> list, Context context, ItemClickAdapterListener onClickListener) {
        this.list = list;
        this.onClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.study_tour_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvHeading.setText(list.get(position).getPkgname());
        holder.tvPlan.setText(list.get(position).getPkgrate());
        holder.tvLocation.setText(list.get(position).getDestin()+", "+list.get(position).getCountry());
        Glide.with(holder.imageView.getContext()).load(APILinks.IMAGE_LINK_TWO + list.get(position).getPkgimg())
                .crossFade()
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);
        holder.tvEnq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.studyEnq(view,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvHeading;
        TextView tvPlan;
        TextView tvLocation;
        ImageView imageView;
        TextView tvEnq;
        public ViewHolder(View itemView) {
            super(itemView);
            tvHeading=itemView.findViewById(R.id.tv_heading);
            tvPlan=itemView.findViewById(R.id.tv_plan);
            tvLocation=itemView.findViewById(R.id.tv_location);
            imageView =itemView.findViewById(R.id.thumbnail);
            tvEnq=itemView.findViewById(R.id.tv_send_enq);
        }

    }

    public interface ItemClickAdapterListener {
        void itemClick(View v, int position);
        void studyEnq(View v, int position);
    }
}
