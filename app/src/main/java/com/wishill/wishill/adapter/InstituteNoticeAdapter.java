package com.wishill.wishill.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wishill.wishill.R;
import com.wishill.wishill.api.recommendedColleges.getNoticeboardData.NoticeData;
import com.wishill.wishill.utilities.APILinks;

import java.util.List;

import static com.wishill.wishill.utilities.Variables.DEFAULT_IMAGE_PATH;

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
        String title = data.getNoticeTitle().substring(0,1).toUpperCase().concat(data.getNoticeTitle().substring(1));
        holder.noticeTitle.setText(title);
        holder.date.setText(data.getDateOfIssue());

        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
// generate random color
        int color1 = generator.getRandomColor();
// generate color based on a key (same key returns the same color), useful for list/grid views
//        int color2 = generator.getColor("user@gmail.com");

// declare the builder object once.
        TextDrawable.IBuilder builder = TextDrawable.builder()
                .beginConfig()
                .withBorder(4)
                .endConfig()
                .rect();

// reuse the builder specs to create multiple drawables
        TextDrawable ic1 = builder.build(data.getNoticeTitle().substring(0,1).toUpperCase(), color1);
//        TextDrawable ic2 = builder.build("B", color2);

        holder.noticeImage.setImageDrawable(ic1);

       /* if (data.getNoticeImage() == null){
//            holder.noticeImage.setVisibility(View.GONE);
            Glide.with(holder.noticeImage.getContext()).load(DEFAULT_IMAGE_PATH)
                    .crossFade()
                    .thumbnail(0.5f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.noticeImage);
        } else {
            Glide.with(holder.noticeImage.getContext()).load(APILinks.IMAGE_LINK + imagePath + data.getNoticeImage())
                    .crossFade()
                    .thumbnail(0.5f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.noticeImage);
        }*/
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
