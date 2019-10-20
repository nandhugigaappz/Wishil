package com.wishill.wishill.api.recommendedColleges.getGallery;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GalleryListData {
    @SerializedName("gallery_id") @Expose private String galleryId;
    @SerializedName("categoryID") @Expose private String categoryID;
    @SerializedName("itemID") @Expose private String itemID;
    @SerializedName("image") @Expose private String galleryItem;
    @SerializedName("video") @Expose private String video;

    public String getGalleryId() {
        return galleryId;
    }

    public void setGalleryId(String galleryId) {
        this.galleryId = galleryId;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getGalleryItem() {
        return galleryItem;
    }

    public void setGalleryItem(String galleryItem) {
        this.galleryItem = galleryItem;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
