package com.wishill.wishill.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wishill.wishill.R;
import com.wishill.wishill.api.recommendedColleges.collegeDetails.CollegeCoursesDetailsData;

import java.util.List;


public class CollegeCourseListAdapter extends RecyclerView.Adapter<CollegeCourseListAdapter.ViewHolder> {
    List<CollegeCoursesDetailsData> list;
    public ItemClickAdapterListener onClickListener;

    public CollegeCourseListAdapter(List<CollegeCoursesDetailsData> list, Context context, ItemClickAdapterListener onClickListener) {
        this.list = list;
        this.onClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.college_course_item_style, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvHeading.setText(list.get(position).getCourseName());
        holder.tvDuration.setText(list.get(position).getDuration());
        holder.tvMode.setText(list.get(position).getMode());
        holder.tvEligibility.setText(android.text.Html.fromHtml(list.get(position).getEligibility()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvHeading;
        TextView tvDuration;
        TextView tvMode;
        TextView tvEligibility;


        public ViewHolder(View itemView) {
            super(itemView);
            tvHeading=itemView.findViewById(R.id.tv_heading);
            tvDuration=itemView.findViewById(R.id.tv_duration);
            tvMode=itemView.findViewById(R.id.tv_mode);
            tvEligibility=itemView.findViewById(R.id.tv_eligibility);
        }

    }

    public interface ItemClickAdapterListener {
        void itemClick(View v, int position);
    }
}
