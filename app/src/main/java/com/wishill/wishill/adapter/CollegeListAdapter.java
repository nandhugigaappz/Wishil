package com.wishill.wishill.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wishill.wishill.R;
import com.wishill.wishill.api.recommendedColleges.subCatCollegeList.SubCatCollegeListData;
import com.wishill.wishill.utilities.APILinks;

import java.util.List;


public class CollegeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<SubCatCollegeListData> list;
    public ItemClickAdapterListener onClickListener;
    boolean loadMore;

    public CollegeListAdapter(boolean loadMore,List<SubCatCollegeListData> list, Context context, ItemClickAdapterListener onClickListener) {
        this.list = list;
        this.onClickListener = onClickListener;
        this.loadMore=loadMore;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        RecyclerView.ViewHolder viewHolder = null;
        if (position == 0) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.college_list_item, viewGroup, false);
            viewHolder = new ListHolder(v);
        } else if (position == 1) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(
                    R.layout.progress_item, viewGroup, false);
            viewHolder = new ProgressViewHolder(v);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof ListHolder) {
            ListHolder vh1 = (ListHolder) holder;
            Glide.with(vh1.thumbnail.getContext()).load(APILinks.IMAGE_LINK + list.get(position).getCollege_img())
                    .crossFade()
                    .thumbnail(0.5f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(vh1.thumbnail);
            vh1.tvName.setText(list.get(position).getCollegeName());
            vh1.tvLocation.setText(list.get(position).getCity());
            String establishedYear="Established Year: <font size='5' color='black'><b>"+list.get(position).getYear()+"</b></font> ";
            vh1.tvYear.setText(Html.fromHtml(establishedYear));
            vh1.ivCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.itemCall(v,position);
                }
            });
            vh1.thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.itemClick(v,position);
                }
            });
            vh1.tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.itemClick(v,position);
                }
            });
            vh1.tvSendEnq.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.itemSendEnq(view,position);
                }
            });
            vh1.baseLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.itemClick(view,position);
                }
            });
        } else if (holder instanceof ProgressViewHolder) {
            final ProgressViewHolder progressViewHolder = (ProgressViewHolder) holder;
            progressViewHolder.progressBar.setIndeterminate(true);
        }
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;
        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
        }
    }

    public int getItemViewType(int position) {
        int a = 0;
        if (position < list.size()) {
            a = 0;
        } else {
            a = 1;
        }
        return a;
    }

    @Override
    public int getItemCount() {
        int size;
        if (loadMore) {
            size = list.size() + 1;
        } else {
            size = list.size();
        }
        return size;
    }

    public static class ListHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        TextView tvName;
        ImageView ivCall;
        TextView tvLocation;
        TextView tvYear;
        TextView tvSendEnq;
        LinearLayout baseLayout;
        public ListHolder(View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            tvName=itemView.findViewById(R.id.tv_name);
            ivCall=itemView.findViewById(R.id.iv_call);
            tvLocation=itemView.findViewById(R.id.tv_location);
            tvYear=itemView.findViewById(R.id.tv_year);
            tvSendEnq=itemView.findViewById(R.id.tv_send_enq);
            baseLayout=itemView.findViewById(R.id.college_list_sr_main_layout);
        }
    }

    public interface ItemClickAdapterListener {
        void itemClick(View v, int position);
        void itemCall(View v, int position);
        void itemSendEnq(View v, int position);
    }
}
