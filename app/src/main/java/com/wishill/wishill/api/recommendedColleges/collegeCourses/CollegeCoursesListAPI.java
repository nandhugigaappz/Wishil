package com.wishill.wishill.api.recommendedColleges.collegeCourses;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by nandhu on 9/26/2016.
 */
public interface CollegeCoursesListAPI {

  @FormUrlEncoded
  @POST("enquiry_course_list.php")
  Call<CollegeCoursesListResponse> post(
          @Field("collegeID") String collegeID

  );
}

