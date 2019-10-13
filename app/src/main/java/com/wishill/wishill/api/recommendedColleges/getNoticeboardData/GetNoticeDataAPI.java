package com.wishill.wishill.api.recommendedColleges.getNoticeboardData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface GetNoticeDataAPI {
    @FormUrlEncoded
    @POST("notice_data.php")
    Call<NoticeResponse> post(
            @Field("collegeID") String collegeID,
            @Field("instituteType") String instituteType

    );

}
