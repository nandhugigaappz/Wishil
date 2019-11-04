package com.wishill.wishill.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wishill.wishill.R;
import com.wishill.wishill.api.recommendedColleges.internshipList.InternShipData;

import java.util.List;


public class IntershipListAdapter extends RecyclerView.Adapter<IntershipListAdapter.ViewHolder> {
    List<InternShipData> list;
    public ItemClickAdapterListener onClickListener;

    public IntershipListAdapter(List<InternShipData> list, Context context, ItemClickAdapterListener onClickListener) {
        this.list = list;
        this.onClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.internship_list_item_all, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
       holder.tvHeading.setText(list.get(position).getTitle());
       holder.tvDate.setText(list.get(position).getStartDate());
       holder.tyvDuration.setText(list.get(position).getDuration());
       holder.tvLocation.setText(list.get(position).getCompanyName());
       holder.rlMain.setOnClickListener(new View.OnClickListener() {
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
         TextView tvHeading;
         TextView tvDate;
         TextView tyvDuration;
         TextView tvLocation;
         RelativeLayout rlMain;


        public ViewHolder(View itemView) {
            super(itemView);
            tvHeading=itemView.findViewById(R.id.tv_heading);
            tvDate=itemView.findViewById(R.id.tv_date);
            tyvDuration=itemView.findViewById(R.id.tv_duration);
            tvLocation=itemView.findViewById(R.id.tv_location);
            rlMain=itemView.findViewById(R.id.rl_main);

        }

    }

    public interface ItemClickAdapterListener {
        void itemClick(View v, int position);
    }
}
