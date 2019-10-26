package com.wishill.wishill.activity.resumebuilder.adapters;

import android.support.annotation.NonNull;


import com.wishill.wishill.activity.resumebuilder.datamodel.Experience;

import java.util.List;

/**
 * Created by ibrahim on 1/19/18.
 */

public class ExperienceAdapter extends ResumeEventAdapter<Experience> {

    public ExperienceAdapter(@NonNull List<Experience> list,
                             ResumeEventOnClickListener resumeEventOnClickListener) {
        super(list, resumeEventOnClickListener);
    }
}
