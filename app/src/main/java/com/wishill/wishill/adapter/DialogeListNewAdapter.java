package com.wishill.wishill.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wishill.wishill.R;

import java.util.List;


public class DialogeListNewAdapter extends RecyclerView.Adapter<DialogeListNewAdapter.ViewHolder> {
    List<String> list;

    public DialogeListNewAdapter(List<String> list, Context context) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dloage_content_list_style, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.text.setText(list.get(position).toString());
        if (position == list.size() - 1) {
            holder.label.setVisibility(View.GONE);
        } else {
            holder.label.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        View label;
        protected TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.tvcontent);
            label = itemView.findViewById(R.id.label);
        }

    }
}
