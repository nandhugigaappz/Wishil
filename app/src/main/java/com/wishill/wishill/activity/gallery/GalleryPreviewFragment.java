package com.wishill.wishill.activity.gallery;

import android.content.Intent;
import androidx.fragment.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wishill.wishill.R;
import com.wishill.wishill.activity.VideoViewActivity;
import com.wishill.wishill.api.recommendedColleges.getGallery.GalleryListData;
import com.wishill.wishill.collegeFragments.CollegeGalleryFragment;
import com.wishill.wishill.studyAbroadFragments.StudyAbroadGalleryFragment;
import com.wishill.wishill.utilities.APILinks;

import java.util.List;

public class GalleryPreviewFragment extends DialogFragment {
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private TextView lblCount;
    private int selectedPosition = 0;
    int sharePosition;
    ImageView btn_share;
    ImageView imageViewPreview;
    String video = "1";

    List<GalleryListData> galleryList;
    String from = "";
    String imagepath;
    public static GalleryPreviewFragment newInstance() {
        GalleryPreviewFragment f = new GalleryPreviewFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_image_slider, container, false);
        viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        lblCount = (TextView) v.findViewById(R.id.lbl_count);
        btn_share = (ImageView) v.findViewById(R.id.btn_share);
        selectedPosition = getArguments().getInt("position");
        from = getArguments().getString("from");
        imagepath = getArguments().getString("imagepath");
        if (from.equals("abroad")) {
            galleryList = StudyAbroadGalleryFragment.galleryList;
        } else {
            galleryList = CollegeGalleryFragment.galleryList;
        }
        sharePosition = selectedPosition;
        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        setCurrentItem(selectedPosition);

        return v;
    }

    private void setCurrentItem(int position) {
        viewPager.setCurrentItem(position, false);
        displayMetaInfo(selectedPosition);
    }

    //	page change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            displayMetaInfo(position);
            sharePosition = position;
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };


    private void displayMetaInfo(int position) {
        lblCount.setText((position + 1) + " of " + galleryList.size());

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
    }

    //	adapter
    public class MyViewPagerAdapter extends PagerAdapter {

        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View view = layoutInflater.inflate(R.layout.image_fullscreen_preview, container, false);
            RelativeLayout playButton = view.findViewById(R.id.btn_play);
            imageViewPreview = view.findViewById(R.id.image_preview);
            String previewUrl = "";
                if (galleryList.get(position).getCategoryID().equals(video)) {
                    previewUrl = "https://img.youtube.com/vi/" + galleryList.get(position).getVideo() + "/0.jpg";
                    playButton.setVisibility(View.VISIBLE);
                } else {
                    previewUrl = APILinks.IMAGE_LINK + imagepath + galleryList.get(position).getGalleryItem();
                    playButton.setVisibility(View.GONE);
                }

            Glide.with(getActivity()).load(previewUrl)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageViewPreview);
            container.addView(view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (galleryList.get(position).getCategoryID().equals(video)) {
                        Intent in=new Intent(getActivity(), VideoViewActivity.class);
                        in.putExtra("videoUrl",galleryList.get(position).getVideo());
                        startActivity(in);
                    }
                }
            });
            return view;
        }

        @Override
        public int getCount() {
            return galleryList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == ((View) obj);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

}

