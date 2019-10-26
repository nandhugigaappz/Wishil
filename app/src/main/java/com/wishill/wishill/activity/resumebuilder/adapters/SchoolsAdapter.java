package com.wishill.wishill.activity.resumebuilder.adapters;

import android.support.annotation.NonNull;


import com.wishill.wishill.activity.resumebuilder.datamodel.School;

import java.util.List;

/**
 * Created by ibrahim on 1/19/18.
 */

public class SchoolsAdapter extends ResumeEventAdapter<School> {

    public SchoolsAdapter(@NonNull List<School> list,
                          ResumeEventOnClickListener resumeEventOnClickListener) {
        super(list, resumeEventOnClickListener);
    }
}