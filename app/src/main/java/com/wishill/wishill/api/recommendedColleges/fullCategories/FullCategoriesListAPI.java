package com.wishill.wishill.api.recommendedColleges.fullCategories;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by altoopa on 9/26/2016.
 */
public interface FullCategoriesListAPI {

  @FormUrlEncoded
  @POST("categorylist.php")
  Call<FullCategoriesResponse> post(
          @Field("value") String value

  );
}

