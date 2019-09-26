package com.wishill.wishill.api.recommendedColleges.getCountry;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by altoopa on 9/26/2016.
 */
public interface CountryListAPI {

  @FormUrlEncoded
  @POST("country.php")
  Call<CountryListResponse> post(
          @Field("value") String value

  );
}

