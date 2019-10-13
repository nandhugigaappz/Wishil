package com.wishill.wishill.api.recommendedColleges.collegeDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by altoopa on 9/10/2016.
 */
public class CollegeDetailsData {
    @SerializedName("c_logo") @Expose private String logo;
    @SerializedName("c_image") @Expose private String coverImage;
    @SerializedName("collegeName") @Expose private String collegeName;
    @SerializedName("street") @Expose private String street;
    @SerializedName("descrip") @Expose private String descrip;
    @SerializedName("e_year") @Expose private String e_year;
    @SerializedName("a_to") @Expose private String a_to;
    @SerializedName("a_by") @Expose private String a_by;
    @SerializedName("ac_by") @Expose private String ac_by;
    @SerializedName("mob_no") @Expose private String mob_no;
    @SerializedName("weburl") @Expose private String weburl;


    @SerializedName("wifi") @Expose private String wifi;
    @SerializedName("library") @Expose private String library;
    @SerializedName("hostel") @Expose private String hostel;
    @SerializedName("shuttile") @Expose private String shuttile;
    @SerializedName("canteen") @Expose private String canteen;
    @SerializedName("auditorium") @Expose private String auditorium;
    @SerializedName("seminarhall") @Expose private String seminarhall;
    @SerializedName("h_care") @Expose private String healthCare;
    @SerializedName("c_cell") @Expose private String counsellingSell;
    @SerializedName("sickroom") @Expose private String sickroom;
    @SerializedName("store") @Expose private String store;
    @SerializedName("caffteria") @Expose private String caffteria;
    @SerializedName("workshop") @Expose private String workshop;

    public String getWifi() {
        return wifi;
    }

    public void setWifi(String wifi) {
        this.wifi = wifi;
    }

    public String getLibrary() {
        return library;
    }

    public void setLibrary(String library) {
        this.library = library;
    }

    public String getHostel() {
        return hostel;
    }

    public void setHostel(String hostel) {
        this.hostel = hostel;
    }

    public String getShuttile() {
        return shuttile;
    }

    public void setShuttile(String shuttile) {
        this.shuttile = shuttile;
    }

    public String getCanteen() {
        return canteen;
    }

    public void setCanteen(String canteen) {
        this.canteen = canteen;
    }

    public String getAuditorium() {
        return auditorium;
    }

    public void setAuditorium(String auditorium) {
        this.auditorium = auditorium;
    }

    public String getSeminarhall() {
        return seminarhall;
    }

    public void setSeminarhall(String seminarhall) {
        this.seminarhall = seminarhall;
    }

    public String getHealthCare() {
        return healthCare;
    }

    public void setHealthCare(String healthCare) {
        this.healthCare = healthCare;
    }

    public String getCounsellingSell() {
        return counsellingSell;
    }

    public void setCounsellingSell(String counsellingSell) {
        this.counsellingSell = counsellingSell;
    }

    public String getSickroom() {
        return sickroom;
    }

    public void setSickroom(String sickroom) {
        this.sickroom = sickroom;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getCaffteria() {
        return caffteria;
    }

    public void setCaffteria(String caffteria) {
        this.caffteria = caffteria;
    }

    public String getWorkshop() {
        return workshop;
    }

    public void setWorkshop(String workshop) {
        this.workshop = workshop;
    }

    public String getMob_no() {
        return mob_no;
    }

    public void setMob_no(String mob_no) {
        this.mob_no = mob_no;
    }

    public String getWeburl() {
        return weburl;
    }

    public void setWeburl(String weburl) {
        this.weburl = weburl;
    }

    public String getAc_by() {
        return ac_by;
    }

    public void setAc_by(String ac_by) {
        this.ac_by = ac_by;
    }

    public String getA_by() {
        return a_by;
    }

    public void setA_by(String a_by) {
        this.a_by = a_by;
    }

    public String getA_to() {
        return a_to;
    }

    public void setA_to(String a_to) {
        this.a_to = a_to;
    }

    public String getE_year() {
        return e_year;
    }

    public void setE_year(String e_year) {
        this.e_year = e_year;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }
}
