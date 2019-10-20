package com.wishill.wishill.api.recommendedColleges.collegeCourseList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by nandhu on 9/26/2016.
 */
public interface CourseListAPI {
  @FormUrlEncoded
  @POST("courselist.php")
  Call<CourseListResponse> post(
          @Field("subcategoryID") String subcategoryID
  );
}

