package com.wishill.wishill.adapter;

import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wishill.wishill.R;
import com.wishill.wishill.api.recommendedColleges.collegeDetails.CollegeAmenitiesDetailsData;
import com.wishill.wishill.utilities.APILinks;

import java.util.List;


public class IndividualDashboardListAdapter extends RecyclerView.Adapter<IndividualDashboardListAdapter.ViewHolder> {
    List<CollegeAmenitiesDetailsData> list;
    public ItemClickAdapterListener onClickListener;
    Context context;
    String amenityPath;

    public IndividualDashboardListAdapter(String amenityPath,List<CollegeAmenitiesDetailsData> list, Context context, ItemClickAdapterListener onClickListener) {
        this.list = list;
        this.onClickListener = onClickListener;
        this.context=context;
        this.amenityPath=amenityPath;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.individual_dashboard_item_style, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //holder.ivLogo.setImageResource(list.get(position).getCategoryIcon());
        Glide.with(holder.ivLogo.getContext()).load(APILinks.IMAGE_LINK +amenityPath+ list.get(position).getImage())
                .crossFade()
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.image_default)
                .into(holder.ivLogo);
        holder.tvHeading.setText(list.get(position).getName());
        holder.ivLogo.setColorFilter(ContextCompat.getColor(context, R.color.blue));
        holder.tvHeading.setTextColor(context.getResources().getColor(R.color.blue));
        holder.lnr_dashItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.itemClick(v, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivLogo;
        TextView tvHeading;
        LinearLayout lnr_dashItem;


        public ViewHolder(View itemView) {
            super(itemView);
            ivLogo = itemView.findViewById(R.id.iv_logo);
            tvHeading = itemView.findViewById(R.id.tv_heading);
            lnr_dashItem = itemView.findViewById(R.id.lnr_dashItem);

        }

    }

    public interface ItemClickAdapterListener {
        void itemClick(View v, int position);


    }
}
