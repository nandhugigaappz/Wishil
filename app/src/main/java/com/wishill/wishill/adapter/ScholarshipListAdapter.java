package com.wishill.wishill.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wishill.wishill.R;
import com.wishill.wishill.api.recommendedColleges.ScholarshipList.ScholarshipListData;

import java.util.List;


public class ScholarshipListAdapter extends RecyclerView.Adapter<ScholarshipListAdapter.ViewHolder> {
    List<ScholarshipListData> list;
    public ItemClickAdapterListener onClickListener;

    public ScholarshipListAdapter(List<ScholarshipListData> list, Context context, ItemClickAdapterListener onClickListener) {
        this.list = list;
        this.onClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.scholarship_list_item_all, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvLocation.setText(list.get(position).getOpen()+" to "+list.get(position).getClose());
        holder.tvName.setText(list.get(position).getScholarshipTitle());
        holder.tvAward.setText(Html.fromHtml(list.get(position).getAward()));
        holder.rlMain.setOnClickListener(new View.OnClickListener() {
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

        TextView tvName;
        TextView tvLocation;
        TextView tvAward;
        RelativeLayout rlMain;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tv_name);
            tvLocation=itemView.findViewById(R.id.tv_location);

            tvAward=itemView.findViewById(R.id.tv_award);
            rlMain=itemView.findViewById(R.id.rl_main);
        }

    }

    public interface ItemClickAdapterListener {
        void itemClick(View v, int position);
    }
}
