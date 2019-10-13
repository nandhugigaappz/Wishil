package com.wishill.wishill.api.recommendedColleges.collegeCouseDetails;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by altoopa on 9/26/2016.
 */
public interface CollegeCourseListAPI {

  @FormUrlEncoded
  @POST("course_detailview.php")
  Call<CollegeCourseResponse> post(
          @Field("collegeID") String collegeID

  );
}

