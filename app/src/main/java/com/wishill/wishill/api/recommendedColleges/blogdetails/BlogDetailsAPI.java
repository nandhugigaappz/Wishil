package com.wishill.wishill.api.recommendedColleges.blogdetails;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by altoopa on 9/26/2016.
 */
public interface BlogDetailsAPI {

  @FormUrlEncoded
  @POST("blogDetail.php")
  Call<BlogDetailsResponse> post(
          @Field("blogID") String enquiryID
  );
}

