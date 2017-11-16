package com.example.android.bakingapp.RecipesData;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Represents a step with its associated data
 */

public class Step implements Parcelable {

    /*
     * Fields
     */

    private int mId;
    private String mShortDescription;
    private String mDescription;
    private String mVideoUrl;
    private String mThumbnailUrl;


    /*
     * Constructors
     */


    public Step(int id, String shortDescription, String description, String videoUrl, String thumbnailUrl) {
        mId = id;
        mShortDescription = shortDescription;
        mDescription = description;
        mVideoUrl = videoUrl;
        mThumbnailUrl = thumbnailUrl;
    }

    private Step(Parcel in) {
        mId = in.readInt();
        mShortDescription = in.readString();
        mDescription = in.readString();
        mVideoUrl = in.readString();
        mThumbnailUrl = in.readString();
    }


    /*
     * Implementing Parcelable
     */


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mId);
        parcel.writeString(mShortDescription);
        parcel.writeString(mDescription);
        parcel.writeString(mVideoUrl);
        parcel.writeString(mThumbnailUrl);
    }

    public static final Parcelable.Creator<Step> CREATOR = new Parcelable.Creator<Step>() {

        @Override
        public Step createFromParcel(Parcel parcel) {
            return new Step(parcel);
        }

        @Override
        public Step[] newArray(int i) {
            return new Step[i];
        }
    };


    /*
     * Getters
     */


    public int getStepId() { return mId; }
    public String getStepShortDescription() { return mShortDescription; }
    public String getStepDescription() { return mDescription; }
    public String getStepVideoUrl() { return mVideoUrl; }
    public String getThumbnailUrl() { return mThumbnailUrl; }


    /*
     * Setters
     */


    public void setStepId(int id) { mId = id; }
    public void setStepShortDescription(String shortDescription) { mShortDescription = shortDescription; }
    public void setStepDescription(String description) { mDescription = description; }
    public void setStepVideoUrl(String videoUrl) { mVideoUrl = videoUrl; }
    public void setThumbnailUrl(String thumbnailUrl) { mThumbnailUrl = thumbnailUrl; }
}
