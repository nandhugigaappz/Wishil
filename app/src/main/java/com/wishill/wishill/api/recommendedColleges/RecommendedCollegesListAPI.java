package com.wishill.wishill.api.recommendedColleges;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by nandhu on 9/26/2016.
 */
public interface RecommendedCollegesListAPI {

  @GET("recommended.php")
  Call<RecommendedCollegesResponse> getList(
          @Query("keyword") String keyword);
}

