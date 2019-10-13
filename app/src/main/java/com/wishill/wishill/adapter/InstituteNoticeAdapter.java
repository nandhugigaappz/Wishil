package com.wishill.wishill.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wishill.wishill.R;
import com.wishill.wishill.api.recommendedColleges.getNoticeboardData.NoticeData;
import com.wishill.wishill.utilities.APILinks;

import java.util.List;

public class InstituteNoticeAdapter extends RecyclerView.Adapter<InstituteNoticeAdapter.ViewHolder> {

    private List<NoticeData> noticeDataList;
    private Context context;
    private ItemClickAdapterListener clickListener;
    private String imagePath;
    public InstituteNoticeAdapter(Context context, String imagePath, List<NoticeData> noticeDataList, ItemClickAdapterListener clickListener) {
        this.context = context;
        this.noticeDataList = noticeDataList;
        this.clickListener  = clickListener;
        this.imagePath      = imagePath;
    }

    @NonNull
    @Override
    public InstituteNoticeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_notice_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NoticeData data = noticeDataList.get(position);
        holder.noticeTitle.setText(data.getNoticeTitle());
        holder.date.setText(data.getDateOfIssue());
        if (data.getNoticeImage() == null){
            holder.noticeImage.setVisibility(View.GONE);
        } else {
            Glide.with(holder.noticeImage.getContext()).load(APILinks.IMAGE_LINK + imagePath + data.getNoticeImage())
                    .crossFade()
                    .thumbnail(0.5f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.noticeImage);
        }
    }

    @Override
    public int getItemCount() {
        return noticeDataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView noticeImage;
        TextView noticeTitle, date;
        public ViewHolder(View itemView) {
            super(itemView);

            noticeImage = itemView.findViewById(R.id.notice_image);
            noticeTitle = itemView.findViewById(R.id.notice_title);
            date        = itemView.findViewById(R.id.date_text_notice_list);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.itemClick(view, getAdapterPosition());
                }
            });
        }
    }

    public interface ItemClickAdapterListener {
        void itemClick(View v, int position);
    }
}
