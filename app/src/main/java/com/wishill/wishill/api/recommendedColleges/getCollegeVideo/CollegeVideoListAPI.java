package com.wishill.wishill.api.recommendedColleges.getCollegeVideo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by altoopa on 9/26/2016.
 */
public interface CollegeVideoListAPI {

  @FormUrlEncoded
  @POST("video_gallery.php")
  Call<CollegeVideoResponse> post(
          @Field("collegeID") String collegeID

  );
}

