package com.wishill.wishill.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wishill.wishill.R;
import com.wishill.wishill.api.recommendedColleges.SchoolFess.SchoolFeesListData;

import java.util.List;


public class SchoolFeesListAdapter extends RecyclerView.Adapter<SchoolFeesListAdapter.ViewHolder> {
    List<SchoolFeesListData> list;
    public ItemClickAdapterListener onClickListener;

    public SchoolFeesListAdapter(List<SchoolFeesListData> list, Context context, ItemClickAdapterListener onClickListener) {
        this.list = list;
        this.onClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.schol_fees_item_all, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvTitle.setText(list.get(position).getGroup());
        holder.tvTotal.setText(list.get(position).getTotalFees());
        holder.tvOne.setText(list.get(position).getTermOne());
        holder.tvTwo.setText(list.get(position).getTermTwo());
        holder.tvThree.setText(list.get(position).getTermThree());
        holder.tvFour.setText(list.get(position).getTermFour());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvTotal;
        TextView tvOne;
        TextView tvTwo;
        TextView tvThree;
        TextView tvFour;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle=itemView.findViewById(R.id.tv_heading);
            tvTotal=itemView.findViewById(R.id.tv_total);
            tvOne=itemView.findViewById(R.id.tv_one);
            tvTwo=itemView.findViewById(R.id.tv_two);
            tvThree=itemView.findViewById(R.id.tv_three);
            tvFour=itemView.findViewById(R.id.tv_four);

        }

    }

    public interface ItemClickAdapterListener {
        void itemClick(View v, int position);
    }
}
