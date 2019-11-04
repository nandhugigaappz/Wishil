package com.wishill.wishill.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wishill.wishill.R;
import com.wishill.wishill.api.recommendedColleges.searchpartnercolleges.SearchListPartnerCollegeData;
import com.wishill.wishill.utilities.APILinks;

import java.util.List;


public class SearchPaertnerCollegeListAdapter extends RecyclerView.Adapter<SearchPaertnerCollegeListAdapter.ViewHolder> {
    List<SearchListPartnerCollegeData> list;
    public ItemClickAdapterListener onClickListener;
    boolean college=false;
    boolean school=false;
    boolean scholarship=false;

    public SearchPaertnerCollegeListAdapter(List<SearchListPartnerCollegeData> list, Context context, ItemClickAdapterListener onClickListener) {
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
        holder.tvHeading.setVisibility(View.GONE);

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
