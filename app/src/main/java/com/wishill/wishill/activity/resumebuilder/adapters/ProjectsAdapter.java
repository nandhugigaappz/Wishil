package com.wishill.wishill.activity.resumebuilder.adapters;

import androidx.annotation.NonNull;
import android.view.View;

import com.wishill.wishill.activity.resumebuilder.datamodel.Project;

import java.util.List;

/**
 * Created by nandhu on 26/10/2019.
 */

public class ProjectsAdapter extends ResumeEventAdapter<Project> {

    public ProjectsAdapter(@NonNull List<Project> list,
                           ResumeEventOnClickListener resumeEventOnClickListener) {
        super(list, resumeEventOnClickListener);
    }

    @Override
    protected void updateViewHolder(ResumeEventAdapterViewHolder viewHolder) {
        viewHolder.subtitle.setVisibility(View.GONE);
    }
}