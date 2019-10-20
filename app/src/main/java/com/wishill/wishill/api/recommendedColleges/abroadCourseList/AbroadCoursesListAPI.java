package com.wishill.wishill.api.recommendedColleges.abroadCourseList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by nandhu on 9/26/2016.
 */
public interface AbroadCoursesListAPI {

  @FormUrlEncoded
  @POST("abroad_enquiry_course_list.php")
  Call<AbroadCoursesListResponse> post(
          @Field("collegeID") String collegeID,
          @Field("instituteType") String instituteType

  );
}

