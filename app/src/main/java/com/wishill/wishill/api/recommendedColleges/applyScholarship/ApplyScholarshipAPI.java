package com.wishill.wishill.api.recommendedColleges.applyScholarship;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApplyScholarshipAPI {
    @FormUrlEncoded
    @POST("scholarship.php")
    Call<ApplyScholarshipResponse> post(
            @Field("contactName") String contactName,
            @Field("userID") String userID,
            @Field("contactEmail") String contactEmail,
            @Field("contactPhone") String contactPhone,
            @Field("enquiryType") String enquiryType,
            @Field("itemId") String itemId,
            @Field("courseID") String courseID,
            @Field("reference") String reference
    );
}
