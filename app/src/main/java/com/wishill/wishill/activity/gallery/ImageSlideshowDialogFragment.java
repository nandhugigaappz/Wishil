package com.wishill.wishill.activity.gallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wishill.wishill.R;
import com.wishill.wishill.api.recommendedColleges.getCollegeGallery.CollegeGalleryListData;
import com.wishill.wishill.api.recommendedColleges.getSchoolGallery.SchoolGalleryListData;
import com.wishill.wishill.schoolFragments.SchoolGalleryFragment;
import com.wishill.wishill.utilities.APILinks;

import java.util.List;


public class ImageSlideshowDialogFragment extends DialogFragment {
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private TextView lblCount, lblTitle, lblDate;
    private int selectedPosition = 0;
    int sharePosition;
    ImageView btn_share;
    String bitmapImageURL;
    Bitmap myBitmapImage;
    ImageView imageViewPreview;


    List<SchoolGalleryListData> schoolGalleryList;
    List<CollegeGalleryListData> collegeGalleryList;
    String from;
    String imagepath;

    public static ImageSlideshowDialogFragment newInstance() {
        ImageSlideshowDialogFragment f = new ImageSlideshowDialogFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_image_slider, container, false);
        viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        lblCount = (TextView) v.findViewById(R.id.lbl_count);
        lblTitle = (TextView) v.findViewById(R.id.title);
        lblDate = (TextView) v.findViewById(R.id.date);
        btn_share= (ImageView) v.findViewById(R.id.btn_share);
        selectedPosition = getArguments().getInt("position");
        from=getArguments().getString("from");
        imagepath=getArguments().getString("imagepath");
        if(from.equals("college")){
//            collegeGalleryList= CollegeGalleryFragment.galleryList;
        } else {
            schoolGalleryList= SchoolGalleryFragment.galleryList;
        }
        sharePosition = selectedPosition;
        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        setCurrentItem(selectedPosition);

      /*  btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getActivity(), mPermissionStorage)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) getActivity(), new String[]{mPermissionStorage}, REQUEST_CODE_PERMISSION);
                }else{
                    bitmapImageURL=list_gallery_image.get(sharePosition).getImage();
                    Log.e("ImageURL",bitmapImageURL);
                    new bitmapAsync().execute(bitmapImageURL);
                }
            }
        });*/

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
        int totalSize = 0;
        if(from.equals("college")){
            totalSize = collegeGalleryList.size();
           /* lblTitle.setText(collegeGalleryList.get(position).getTitle());
            lblDate.setText(collegeGalleryList.get(position).getDescription());*/
        }else {
            totalSize = schoolGalleryList.size();
        }
        lblCount.setText((position + 1) + " of " + totalSize);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL,  R.style.FullScreenDialogStyle);
    }

    //	adapter
    public class MyViewPagerAdapter extends PagerAdapter {

        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.image_fullscreen_preview, container, false);
            imageViewPreview = view.findViewById(R.id.image_preview);
            if(from.equals("college")){
                Glide.with(getActivity()).load(APILinks.IMAGE_LINK+imagepath+ collegeGalleryList.get(position).getImage())
                        .thumbnail(0.5f)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imageViewPreview);
            }else{
                Glide.with(getActivity()).load(APILinks.IMAGE_LINK+imagepath+ schoolGalleryList.get(position).getImage())
                        .thumbnail(0.5f)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imageViewPreview);
            }

            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            int count=0;
            if(from.equals("college")){
                count=collegeGalleryList.size();
            }else{
                count=schoolGalleryList.size();
            }
            return count;
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
 /*  class bitmapAsync extends AsyncTask<String, Void, String> {
       @Override
       protected void onPreExecute() {
           super.onPreExecute();
           progressDialog.show();
       }

       protected String doInBackground(String... urls) {
           try {
               String newpath =Constants.IMAGE_FOLDER_GALLERY+""+bitmapImageURL;
               Log.e("NewPath",newpath);
               URL url = new URL(newpath);
               HttpURLConnection connection = (HttpURLConnection) url.openConnection();
               connection.setDoInput(true);
               connection.connect();
               InputStream input = connection.getInputStream();
               myBitmapImage= BitmapFactory.decodeStream(input);
               return "";
           } catch (Exception e) {
               Log.e("Exception",e+"");
               return null;
           }
       }
       protected void onPostExecute(String feed) {
           if (myBitmapImage != null) {
               Log.e("Bitmap status","bitmap  is present");
               shareImage();
               progressDialog.dismiss();
           } else {
               Log.e("Bitmap status","bitmap is empty");
               progressDialog.dismiss();

           }
       }
   }*/
 /*     public  void shareImage(){
        String bitmapPath = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), myBitmapImage, "title", null);
        Uri bitmapUri = Uri.parse(bitmapPath);
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "MyMovieReview");
        shareIntent.setType("image/jpeg");
        startActivity(Intent.createChooser(shareIntent, "text message"));
    }*/


}
