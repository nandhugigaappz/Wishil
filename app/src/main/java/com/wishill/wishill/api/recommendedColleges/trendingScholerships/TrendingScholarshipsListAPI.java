package com.wishill.wishill.api.recommendedColleges.trendingScholerships;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by nandhu on 9/26/2016.
 */
public interface TrendingScholarshipsListAPI {

  @GET("attractivescholarships.php")
  Call<TrendingScholarshipsResponse> getList(
          @Query("keyword") String keyword);
}

