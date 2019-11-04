package com.wishill.wishill.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wishill.wishill.R;
import com.wishill.wishill.api.recommendedColleges.partnerEnquiriesList.partnerEnquiriesListData;

import java.util.List;


public class PartnerEnqListAdapter extends RecyclerView.Adapter<PartnerEnqListAdapter.ViewHolder> {
    List<partnerEnquiriesListData> list;
    public ItemClickAdapterListener onClickListener;

    public PartnerEnqListAdapter(List<partnerEnquiriesListData> list, Context context, ItemClickAdapterListener onClickListener) {
        this.list = list;
        this.onClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.partner_enq_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvName.setText(list.get(position).getEnUserName());
        holder.tvEmail.setText(list.get(position).getEnEmail());
        holder.tvMobile.setText(list.get(position).getEnMobile());
        holder.tvDate.setText(list.get(position).getEnDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvEmail;
        TextView tvMobile;
        TextView tvDate;
        public ViewHolder(View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tv_name);
            tvEmail=itemView.findViewById(R.id.tv_email);
            tvMobile=itemView.findViewById(R.id.tv_mobile);
            tvDate=itemView.findViewById(R.id.tv_date);
        }

    }

    public interface ItemClickAdapterListener {
        void itemClick(View v, int position);
    }
}
