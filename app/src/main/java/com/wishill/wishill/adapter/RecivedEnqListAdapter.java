package com.wishill.wishill.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wishill.wishill.R;
import com.wishill.wishill.api.recommendedColleges.recivedenqlist.RecivedEnqListData;
import com.wishill.wishill.utilities.APILinks;
import com.wishill.wishill.utilities.CircleTransform;

import java.util.List;


public class RecivedEnqListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<RecivedEnqListData> list;
    public ItemClickAdapterListener onClickListener;
    boolean loadMore;

    public RecivedEnqListAdapter(boolean loadMore, List<RecivedEnqListData> list, Context context, ItemClickAdapterListener onClickListener) {
        this.list = list;
        this.onClickListener = onClickListener;
        this.loadMore=loadMore;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        RecyclerView.ViewHolder viewHolder = null;
        if (position == 0) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recived_enq_list_item, viewGroup, false);
            viewHolder = new ListHolder(v);
        } else if (position == 1) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(
                    R.layout.progress_item, viewGroup, false);
            viewHolder = new ProgressViewHolder(v);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof ListHolder) {
            ListHolder vh1 = (ListHolder) holder;
            vh1.tvDes.setText(list.get(position).getEnqContent());
            Glide.with(vh1.ivProfileImage.getContext()).load(APILinks.IMAGE_LINK+list.get(position).getUserImage())
                    .crossFade()
                    .thumbnail(0.5f)
                    .bitmapTransform(new CircleTransform(vh1.ivProfileImage.getContext()))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.ic_avatar)
                    .into(vh1.ivProfileImage);
            vh1.tvName.setText(list.get(position).getEnqUserName());
            vh1.tvView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.itemClick(v,position);
                }
            });
        } else if (holder instanceof ProgressViewHolder) {
            final ProgressViewHolder progressViewHolder = (ProgressViewHolder) holder;
            progressViewHolder.progressBar.setIndeterminate(true);
        }
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;
        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
        }
    }

    public int getItemViewType(int position) {
        int a = 0;
        if (position < list.size()) {
            a = 0;
        } else {
            a = 1;
        }
        return a;
    }

    @Override
    public int getItemCount() {
        int size;
        if (loadMore) {
            size = list.size() + 1;
        } else {
            size = list.size();
        }
        return size;
    }

    public static class ListHolder extends RecyclerView.ViewHolder {
        TextView tvDes;
        TextView tvView;
        ImageView ivProfileImage;
        TextView tvName;

        public ListHolder(View itemView) {
            super(itemView);
            tvDes=itemView.findViewById(R.id.tv_des);
            tvView=itemView.findViewById(R.id.tv_view);
            ivProfileImage=itemView.findViewById(R.id.iv_profile_image);
            tvName=itemView.findViewById(R.id.tv_name);

        }
    }

    public interface ItemClickAdapterListener {
        void itemClick(View v, int position);
        void itemCall(View v, int position);
        void itemSendEnq(View v, int position);
    }
}
