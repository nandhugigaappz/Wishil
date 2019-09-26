package com.wishill.wishill.api.recommendedColleges.searchpartnercolleges;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by altoopa on 9/26/2016.
 */
public interface SearchPartnerCollegeListAPI {

  @FormUrlEncoded
  @POST("freeListingCollege.php")
  Call<SearchListPartnerCollegeResponse> post(
          @Field("searchval") String searchval,
          @Field("userTypeID") String userTypeID

  );
}

