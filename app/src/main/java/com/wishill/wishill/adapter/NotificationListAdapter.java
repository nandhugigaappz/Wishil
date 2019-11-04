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
import com.wishill.wishill.api.recommendedColleges.getNotifications.NotificationListData;
import com.wishill.wishill.utilities.APILinks;

import java.util.List;


public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.ViewHolder> {
    List<NotificationListData> list;
    public ItemClickAdapterListener onClickListener;

    public NotificationListAdapter(List<NotificationListData> list, Context context, ItemClickAdapterListener onClickListener) {
        this.list = list;
        this.onClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvHeading.setText(android.text.Html.fromHtml(list.get(position).getItemName()));
        holder.tvDes.setText(android.text.Html.fromHtml(list.get(position).getContent()));
        holder.tvDate.setText(list.get(position).getPostedDate());
        Glide.with(holder.thumbnail.getContext()).load(APILinks.IMAGE_LINK + list.get(position).getImage())
                .crossFade()
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvHeading;
        TextView tvDes;
        TextView tvDate;
        ImageView thumbnail;
        public ViewHolder(View itemView) {
            super(itemView);
            tvHeading=itemView.findViewById(R.id.tv_heading);
            tvDes=itemView.findViewById(R.id.tv_des);
            tvDate=itemView.findViewById(R.id.tv_date);
            thumbnail=itemView.findViewById(R.id.thumbnail);

        }

    }

    public interface ItemClickAdapterListener {
        void itemClick(View v, int position);
    }
}
