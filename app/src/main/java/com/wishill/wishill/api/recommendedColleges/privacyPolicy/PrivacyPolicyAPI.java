package com.wishill.wishill.api.recommendedColleges.privacyPolicy;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by altoopa on 9/26/2016.
 */
public interface PrivacyPolicyAPI {

  @FormUrlEncoded
  @POST("privacy.php")
  Call<PrivacyPolicyResponse> post(
          @Field("value") String value

  );
}

