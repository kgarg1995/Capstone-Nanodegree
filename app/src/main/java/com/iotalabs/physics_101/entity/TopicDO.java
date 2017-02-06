package com.iotalabs.physics_101.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by karangarg on 28/01/17.
 */

public class TopicDO implements Parcelable{

    private String id;
    private String topicName;
    private String numberOfSubTopics;
    private String hoursRequired;
    private String imageURL;

    public TopicDO() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(topicName);
        dest.writeString(numberOfSubTopics);
        dest.writeString(hoursRequired);
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
        id = in.readString();
        topicName = in.readString();
        numberOfSubTopics = in.readString();
        hoursRequired = in.readString();
        imageURL = in.readString();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getNumberOfSubTopics() {
        return numberOfSubTopics;
    }

    public void setNumberOfSubTopics(String numberOfSubTopics) {
        this.numberOfSubTopics = numberOfSubTopics;
    }

    public String getHoursRequired() {
        return hoursRequired;
    }

    public void setHoursRequired(String hoursRequired) {
        this.hoursRequired = hoursRequired;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

}
