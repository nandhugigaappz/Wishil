package com.wishill.wishill.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wishill.wishill.R;
import com.wishill.wishill.api.recommendedColleges.examList.ExamListData;

import java.util.List;


public class ExamListAdapter extends RecyclerView.Adapter<ExamListAdapter.ViewHolder> {
    List<ExamListData> list;
    public ItemClickAdapterListener onClickListener;

    public ExamListAdapter(List<ExamListData> list, Context context, ItemClickAdapterListener onClickListener) {
        this.list = list;
        this.onClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.exam_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
         holder.tvTitle.setText(list.get(position).getExamTitle());
         holder.tvExamMode.setText(list.get(position).getExamMode());
         holder.tvRegDate.setText(list.get(position).getRegDate());
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

        TextView tvTitle;
        TextView tvExamMode;
        TextView tvRegDate;
        LinearLayout llMain;


        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvExamMode=itemView.findViewById(R.id.tv_exam_mode);
            tvRegDate=itemView.findViewById(R.id.tv_reg_date);
            llMain=itemView.findViewById(R.id.ll_main);

        }

    }

    public interface ItemClickAdapterListener {
        void itemClick(View v, int position);

    }
}
