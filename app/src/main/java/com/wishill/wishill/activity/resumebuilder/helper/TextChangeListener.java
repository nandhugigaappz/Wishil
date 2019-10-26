package com.wishill.wishill.activity.resumebuilder.helper;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by nandhu on 26/10/2019.
 */


public abstract class TextChangeListener implements TextWatcher {

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public abstract void onTextChanged(CharSequence s, int start, int before, int count);

    @Override
    public void afterTextChanged(Editable s) {

    }
}