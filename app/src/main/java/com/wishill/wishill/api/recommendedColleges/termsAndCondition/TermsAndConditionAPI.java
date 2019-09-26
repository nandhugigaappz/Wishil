package com.wishill.wishill.api.recommendedColleges.termsAndCondition;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by altoopa on 9/26/2016.
 */
public interface TermsAndConditionAPI {

  @FormUrlEncoded
  @POST("terms_condition.php")
  Call<TermsAndConditionResponse> post(
          @Field("value") String value

  );
}

