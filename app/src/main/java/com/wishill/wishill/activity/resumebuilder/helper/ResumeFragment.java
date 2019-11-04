package com.wishill.wishill.activity.resumebuilder.helper;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import com.wishill.wishill.activity.resumebuilder.datamodel.Resume;

/**
 * Created by nandhu on 26/10/2019.
 */

abstract public class ResumeFragment extends Fragment {

    public static final String ARGUMENT_RESUME = "resume";

    public void setResume(Resume resume) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGUMENT_RESUME, resume);
        setArguments(bundle);
    }

    public Resume getResume() {
        Resume resume = getArguments().getParcelable(ARGUMENT_RESUME);
        return resume;
    }
}
