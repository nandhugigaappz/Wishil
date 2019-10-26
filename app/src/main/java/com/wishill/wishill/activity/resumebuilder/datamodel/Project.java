package com.wishill.wishill.activity.resumebuilder.datamodel;

import android.os.Parcel;

/**
 * Created by nandhu on 26/10/2019.
 */

public class Project extends ResumeEvent {

    public Project() {
    }

    public static final Creator<Project> CREATOR = new Creator<Project>() {
        @Override
        public Project createFromParcel(Parcel in) {
            return new Project(new ResumeEvent(in));
        }

        @Override
        public Project[] newArray(int size) {
            return new Project[size];
        }
    };

    public Project(ResumeEvent event) {
        super(event);
    }

    public String getName() {
        return getTitle();
    }

    public void setName(String name) {
        setTitle(name);
    }
}
