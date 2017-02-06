package com.iotalabs.physics_101.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by karangarg on 28/01/17.
 */

public class SubTopicDO implements Parcelable {

    private String id;
    private String thumbnailURL;
    private String imageURL;
    private String subTopicName;
    private String subTopicDescription;
    private String hoursRequired;


    public SubTopicDO(){

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(thumbnailURL);
        dest.writeString(imageURL);
        dest.writeString(subTopicName);
        dest.writeString(subTopicDescription);
        dest.writeString(hoursRequired);
    }

    // Creator
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public SubTopicDO createFromParcel(Parcel in) {
            return new SubTopicDO(in);
        }

        public SubTopicDO[] newArray(int size) {
            return new SubTopicDO[size];
        }
    };

    // "De-parcel object
    public SubTopicDO(Parcel in) {
        id = in.readString();
        thumbnailURL = in.readString();
        imageURL = in.readString();
        subTopicName = in.readString();
        subTopicDescription = in.readString();
        hoursRequired = in.readString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getSubTopicName() {
        return subTopicName;
    }

    public void setSubTopicName(String subTopicName) {
        this.subTopicName = subTopicName;
    }

    public String getSubTopicDescription() {
        return subTopicDescription;
    }

    public void setSubTopicDescription(String subTopicDescription) {
        this.subTopicDescription = subTopicDescription;
    }

    public String getHoursRequired() {
        return hoursRequired;
    }

    public void setHoursRequired(String hoursRequired) {
        this.hoursRequired = hoursRequired;
    }

}
