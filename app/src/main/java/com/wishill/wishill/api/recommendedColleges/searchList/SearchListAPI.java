package com.wishill.wishill.api.recommendedColleges.searchList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by nandhu on 9/26/2016.
 */
public interface SearchListAPI {

  @FormUrlEncoded
  @POST("search.php")
  Call<SearchListResponse> post(
          @Field("searchval") String searchval

  );
}

