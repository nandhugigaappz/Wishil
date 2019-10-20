package com.wishill.wishill.api.recommendedColleges.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nandhu on 9/10/2016.
 */
public class LoginDetailsData {
    @SerializedName("id") @Expose private String id;
    @SerializedName("u_email") @Expose private String u_email;
    @SerializedName("u_name") @Expose private String u_name;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getU_email() {
        return u_email;
    }

    public void setU_email(String u_email) {
        this.u_email = u_email;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }
}
