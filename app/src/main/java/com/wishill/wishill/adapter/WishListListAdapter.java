package com.wishill.wishill.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wishill.wishill.R;
import com.wishill.wishill.api.recommendedColleges.mywishlist.MyWishListData;
import com.wishill.wishill.utilities.APILinks;

import java.util.List;


public class WishListListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<MyWishListData> list;
    public ItemClickAdapterListener onClickListener;
    boolean loadMore;

    public WishListListAdapter(boolean loadMore, List<MyWishListData> list, Context context, ItemClickAdapterListener onClickListener) {
        this.list = list;
        this.onClickListener = onClickListener;
        this.loadMore=loadMore;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        RecyclerView.ViewHolder viewHolder = null;
        if (position == 0) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.wish_list_item, viewGroup, false);
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

            Glide.with(vh1.thumbnail.getContext()).load(APILinks.IMAGE_LINK + list.get(position).getImage())
                    .crossFade()
                    .thumbnail(0.5f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.image_default)
                    .into(vh1.thumbnail);
            if(list.get(position).getTypeID().equals("1")){
                vh1.tvType.setText("COLLEGE");
            }else if(list.get(position).getTypeID().equals("2")){
                vh1.tvType.setText("SCHOOL");
            }else if(list.get(position).getTypeID().equals("3")){
                vh1.tvType.setText("STUDY TOUR");
            }

            vh1.tvName.setText(list.get(position).getName());
            vh1.tvLocation.setText(list.get(position).getCity());
            vh1.thumbnail.setOnClickListener(new View.OnClickListener() {
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
        ImageView thumbnail;
        TextView tvName;
        TextView tvType;
        TextView tvLocation;


        public ListHolder(View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            tvName=itemView.findViewById(R.id.tv_name);
            tvType=itemView.findViewById(R.id.tv_type);
            tvLocation=itemView.findViewById(R.id.tv_sub_title);
        }
    }

    public interface ItemClickAdapterListener {
        void itemClick(View v, int position);
        void itemCall(View v, int position);
        void itemSendEnq(View v, int position);
    }
}
