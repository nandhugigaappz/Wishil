package com.wishill.wishill.activity.resumebuilder.adapters;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.wishill.wishill.R;


public class ResumeEventAdapterViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {
    ResumeEventAdapter.ResumeEventOnClickListener mResumeEventOnClickListener;
    TextView title;
    TextView detail;
    TextView subtitle;
    TextView description;
    TextView fromDate;
    TextView toDate;

    public ResumeEventAdapterViewHolder(View itemView, ResumeEventAdapter.ResumeEventOnClickListener resumeEventOnClickListener) {
        super(itemView);
        title = itemView.findViewById(R.id.title);
        detail = itemView.findViewById(R.id.detail);
        subtitle = itemView.findViewById(R.id.subtitle);
        description = itemView.findViewById(R.id.description);
        mResumeEventOnClickListener = resumeEventOnClickListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mResumeEventOnClickListener.onClick(getAdapterPosition());
    }
}