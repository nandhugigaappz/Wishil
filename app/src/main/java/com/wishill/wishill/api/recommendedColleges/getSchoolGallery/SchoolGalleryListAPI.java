package com.wishill.wishill.api.recommendedColleges.getSchoolGallery;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by nandhu on 9/26/2016.
 */
public interface SchoolGalleryListAPI {

  @FormUrlEncoded
  @POST("school_gallery.php")
  Call<SchoolGalleryResponse> post(
          @Field("schoolID") String schoolID

  );
}

