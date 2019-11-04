package com.wishill.wishill.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wishill.wishill.R;
import com.wishill.wishill.api.recommendedColleges.getSchoolGallery.SchoolGalleryListData;
import com.wishill.wishill.utilities.APILinks;

import java.util.List;


public class SchoolGalleryListAdapter extends RecyclerView.Adapter<SchoolGalleryListAdapter.ViewHolder> {
    List<SchoolGalleryListData> list;
    public ItemClickAdapterListener onClickListener;
    String ImagePath;

    public SchoolGalleryListAdapter(String ImagePath,List<SchoolGalleryListData> list, Context context, ItemClickAdapterListener onClickListener) {
        this.list = list;
        this.onClickListener = onClickListener;
        this.ImagePath=ImagePath;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.e("Image",APILinks.IMAGE_LINK+ImagePath+ list.get(position).getImage());
        Glide.with(holder.thumbnail.getContext()).load(APILinks.IMAGE_LINK+ImagePath+ list.get(position).getImage())
                .crossFade()
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.thumbnail);
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
        LinearLayout llMain;


        public ViewHolder(View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            llMain=itemView.findViewById(R.id.ll_main);
        }

    }

    public interface ItemClickAdapterListener {
        void itemClick(View v, int position);
    }
}
