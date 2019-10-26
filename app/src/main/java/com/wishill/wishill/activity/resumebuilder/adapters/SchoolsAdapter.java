package com.wishill.wishill.activity.resumebuilder.adapters;

import android.support.annotation.NonNull;


import com.wishill.wishill.activity.resumebuilder.datamodel.School;

import java.util.List;

/**
 * Created by nandhu on 26/10/2019.
 */

public class SchoolsAdapter extends ResumeEventAdapter<School> {

    public SchoolsAdapter(@NonNull List<School> list,
                          ResumeEventOnClickListener resumeEventOnClickListener) {
        super(list, resumeEventOnClickListener);
    }
}