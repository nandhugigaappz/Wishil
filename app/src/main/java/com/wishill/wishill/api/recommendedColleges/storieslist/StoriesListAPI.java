package com.wishill.wishill.api.recommendedColleges.storieslist;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by altoopa on 9/26/2016.
 */
public interface StoriesListAPI {

  @FormUrlEncoded
  @POST("latestBlog.php")
  Call<StoriesListResponse> post(
          @Field("value") String value

  );
}

