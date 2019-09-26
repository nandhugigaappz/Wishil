package com.wishill.wishill.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wishill.wishill.R;
import com.wishill.wishill.api.recommendedColleges.searchList.SearchListData;
import com.wishill.wishill.utilities.APILinks;

import java.util.List;


public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.ViewHolder> {
    List<SearchListData> list;
    public ItemClickAdapterListener onClickListener;
    boolean college=false;
    boolean school=false;
    boolean scholarship=false;

    public SearchListAdapter(List<SearchListData> list, Context context, ItemClickAdapterListener onClickListener) {
        this.list = list;
        this.onClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Glide.with(holder.thumbnail.getContext()).load(APILinks.IMAGE_LINK+list.get(position).getItemImg())
                .crossFade()
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.thumbnail);
        holder.tvName.setText(list.get(position).getName());
        holder.tvLocation.setText(list.get(position).getLocation());
        holder.tvLocation.setVisibility(View.VISIBLE);
        String establishedYear="Established Year: <font size='5' color='black'><b>"+"1996"+"</b></font> ";
        holder.tvYear.setText(Html.fromHtml(establishedYear));

        if(list.get(position).getCategoryID().equals("1")){
            if(!college){
                college=true;
                holder.tvHeading.setText("Colleges");
                holder.tvHeading.setVisibility(View.VISIBLE);
            }else{
                /*college=false;*/
                holder.tvHeading.setVisibility(View.GONE);
            }
        }else if(list.get(position).getCategoryID().equals("4")){
            if(!school){
                school=true;
                holder.tvHeading.setText("Schools");
                holder.tvHeading.setVisibility(View.VISIBLE);
            }else{
             /*   school=false;*/
                holder.tvHeading.setVisibility(View.GONE);
            }
        }else if(list.get(position).getCategoryID().equals("6")){
            if(!scholarship){
                scholarship=true;
                holder.tvHeading.setText("Scholarships");
                holder.tvHeading.setVisibility(View.VISIBLE);
            }else{
              /*  scholarship=false;*/
                holder.tvHeading.setVisibility(View.GONE);
            }
        }




        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.itemClick(v,position);
            }
        });
        holder.tvName.setOnClickListener(new View.OnClickListener() {
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
        TextView tvLocation;
        TextView tvYear;
        TextView tvHeading;

        public ViewHolder(View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            tvName=itemView.findViewById(R.id.tv_name);
            tvLocation=itemView.findViewById(R.id.tv_location);
            tvYear=itemView.findViewById(R.id.tv_year);
            tvHeading=itemView.findViewById(R.id.tv_heading);
        }

    }

    public interface ItemClickAdapterListener {
        void itemClick(View v, int position);
    }
}
