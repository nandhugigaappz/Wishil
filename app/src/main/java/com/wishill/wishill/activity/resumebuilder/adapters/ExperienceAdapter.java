package com.wishill.wishill.activity.resumebuilder.adapters;

import androidx.annotation.NonNull;


import com.wishill.wishill.activity.resumebuilder.datamodel.Experience;

import java.util.List;

/**
 * Created by nandhu on 26/10/2019.
 */

public class ExperienceAdapter extends ResumeEventAdapter<Experience> {

    public ExperienceAdapter(@NonNull List<Experience> list,
                             ResumeEventOnClickListener resumeEventOnClickListener) {
        super(list, resumeEventOnClickListener);
    }
}
