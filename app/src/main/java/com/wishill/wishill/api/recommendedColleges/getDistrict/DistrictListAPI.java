package com.wishill.wishill.api.recommendedColleges.getDistrict;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by altoopa on 9/26/2016.
 */
public interface DistrictListAPI {

  @FormUrlEncoded
  @POST("citylist.php")
  Call<DistrictListResponse> post(
          @Field("stateID") String stateID

  );
}

