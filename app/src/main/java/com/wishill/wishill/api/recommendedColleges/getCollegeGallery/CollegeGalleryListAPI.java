package com.wishill.wishill.api.recommendedColleges.getCollegeGallery;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by nandhu on 9/26/2016.
 */
public interface CollegeGalleryListAPI {

  @FormUrlEncoded
  @POST("college_gallery.php")
  Call<CollegeGalleryResponse> post(
          @Field("collegeID") String collegeID

  );
}

