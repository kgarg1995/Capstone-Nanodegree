package com.iotalabs.physics_101.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by karangarg on 28/01/17.
 */

public class SubTopicDO implements Parcelable {

    private int subTopicId;
    private int topicId;
    private String thumbnailURL;
    private String imageURL;
    private String subTopicName;
    private String subTopicDescription;
    private int hoursRequired;


    public SubTopicDO(){

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(subTopicId);
        dest.writeInt(topicId);
        dest.writeString(thumbnailURL);
        dest.writeString(imageURL);
        dest.writeString(subTopicName);
        dest.writeString(subTopicDescription);
        dest.writeInt(hoursRequired);
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
        subTopicId = in.readInt();
        topicId = in.readInt();
        thumbnailURL = in.readString();
        imageURL = in.readString();
        subTopicName = in.readString();
        subTopicDescription = in.readString();
        hoursRequired = in.readInt();
    }

    public int getSubTopicId() {
        return subTopicId;
    }

    public void setSubTopicId(int subTopicId) {
        this.subTopicId = subTopicId;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
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

    public int getHoursRequired() {
        return hoursRequired;
    }

    public void setHoursRequired(int hoursRequired) {
        this.hoursRequired = hoursRequired;
    }

}
