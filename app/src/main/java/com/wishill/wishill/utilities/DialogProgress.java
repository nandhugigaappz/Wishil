package com.wishill.wishill.utilities;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.ViewGroup;
import android.view.Window;

import com.wishill.wishill.R;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;


/**
 * Created by anu on 8/9/2017.
 */

public class DialogProgress extends Dialog {
    private String text;
    private GifImageView gifImageView;
    private Context context;

    public DialogProgress(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public DialogProgress(@NonNull Context context, String text) {
        super(context, R.style.full_screen_dialog);
        this.text = text;
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.prograss_dialog);
        Window window = getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }
        gifImageView = findViewById(R.id.gifLoader);
        try {
            GifDrawable gifFromAssets = new GifDrawable(context.getAssets(), "loader.gif");
            gifImageView.setImageDrawable(gifFromAssets);
        } catch (IOException e) {
        }

        setCancelable(false);
    }
}
