package com.iotalabs.physics_101.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by karangarg on 28/01/17.
 */

public class TopicDO implements Parcelable{

    private int topicId;
    private String topicName;
    private int numberOfSubTopics;
    private int hoursRequired;
    private String imageURL;

    public TopicDO() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(topicId);
        dest.writeString(topicName);
        dest.writeInt(numberOfSubTopics);
        dest.writeInt(hoursRequired);
        dest.writeString(imageURL);
    }

    // Creator
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public TopicDO createFromParcel(Parcel in) {
            return new TopicDO(in);
        }

        public TopicDO[] newArray(int size) {
            return new TopicDO[size];
        }
    };

    // "De-parcel object
    public TopicDO(Parcel in) {
        topicId = in.readInt();
        topicName = in.readString();
        numberOfSubTopics = in.readInt();
        hoursRequired = in.readInt();
        imageURL = in.readString();
    }


    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public int getNumberOfSubTopics() {
        return numberOfSubTopics;
    }

    public void setNumberOfSubTopics(int numberOfSubTopics) {
        this.numberOfSubTopics = numberOfSubTopics;
    }

    public int getHoursRequired() {
        return hoursRequired;
    }

    public void setHoursRequired(int hoursRequired) {
        this.hoursRequired = hoursRequired;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

}
