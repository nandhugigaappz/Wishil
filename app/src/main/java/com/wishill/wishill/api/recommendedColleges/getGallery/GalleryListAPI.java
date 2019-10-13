package com.wishill.wishill.api.recommendedColleges.getGallery;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface GalleryListAPI {
    @FormUrlEncoded
    @POST("gallery.php")
    Call<GalleryResponse> post(
            @Field("instituteID") String collegeID,
            @Field("instituteType") String instituteType

    );
}
