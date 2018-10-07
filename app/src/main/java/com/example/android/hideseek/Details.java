package com.example.android.hideseek;

import android.os.Parcel;
import android.os.Parcelable;

/*
Details Model Class for Custom List View
*/

public class Details implements Parcelable {
    private String LostFound;
    private String mName;
    private String mContactNumber;
    private String mObjectType;
    private String mDescription;
    private String mEmail;
    private String mId;
    private String mImageUrl;
    private String mVisibililty;
    private String mResolved;
    private String mApproved;

    //Empty because defining a custom constructor
    public Details() {

    }

    //custom constructor
    public Details(String lostFound, String name, String contactNumber, String objectType, String description, String email) {
        this.LostFound = lostFound;
        this.mName = name;
        this.mContactNumber = contactNumber;
        this.mObjectType = objectType;
        this.mDescription = description;
        this.mEmail = email;
    }

    public Details(String Id, String lostFound, String name, String contactNumber, String objectType, String description, String email, String visibility, String resolved, String approved) {
        this.mId = Id;
        this.LostFound = lostFound;
        this.mName = name;
        this.mContactNumber = contactNumber;
        this.mObjectType = objectType;
        this.mDescription = description;
        this.mEmail = email;
        this.mVisibililty=visibility;
        this.mResolved=resolved;
        this.setmApproved(approved);
    }

    public Details(String Id, String lostFound, String name, String contactNumber, String objectType, String description, String email, String imageUrl, String visibility,String resolved,String approved) {
        this.mId = Id;
        this.LostFound = lostFound;
        this.mName = name;
        this.mContactNumber = contactNumber;
        this.mObjectType = objectType;
        this.mDescription = description;
        this.mEmail = email;
        this.mImageUrl = imageUrl;
        this.mVisibililty=visibility;
        this.mResolved=resolved;
        this.setmApproved(approved);
    }


    protected Details(Parcel in) {
        LostFound = in.readString();
        mName = in.readString();
        mContactNumber = in.readString();
        mObjectType = in.readString();
        mDescription = in.readString();
        mEmail = in.readString();
        mId = in.readString();
        mImageUrl = in.readString();
        mVisibililty=in.readString();
        mResolved=in.readString();
        setmApproved(in.readString());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(LostFound);
        dest.writeString(mName);
        dest.writeString(mContactNumber);
        dest.writeString(mObjectType);
        dest.writeString(mDescription);
        dest.writeString(mEmail);
        dest.writeString(mId);
        dest.writeString(mImageUrl);
        dest.writeString(mVisibililty);
        dest.writeString(mResolved);
        dest.writeString(getmApproved());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Details> CREATOR = new Creator<Details>() {
        @Override
        public Details createFromParcel(Parcel in) {
            return new Details(in);
        }

        @Override
        public Details[] newArray(int size) {
            return new Details[size];
        }
    };

    /*
    Encapsulating all fields
     */

    public String getLostFound() {
        return LostFound;
    }

    public void setLostFound(String lostFound) {
        LostFound = lostFound;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmContactNumber() {
        return mContactNumber;
    }

    public void setmContactNumber(String mContactNumber) {
        this.mContactNumber = mContactNumber;
    }

    public String getmObjectType() {
        return mObjectType;
    }

    public void setmObjectType(String mObjectType) {
        this.mObjectType = mObjectType;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }


    public String getmVisibililty() {
        return mVisibililty;
    }

    public void setmVisibililty(String mVisibililty) {
        this.mVisibililty = mVisibililty;
    }

    public String getmResolved() {
        return mResolved;
    }

    public void setmResolved(String mResolved) {
        this.mResolved = mResolved;
    }

    public String getmApproved() {
        return mApproved;
    }

    public void setmApproved(String mApproved) {
        this.mApproved = mApproved;
    }
}
