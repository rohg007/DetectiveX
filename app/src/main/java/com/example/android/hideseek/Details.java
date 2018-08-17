package com.example.android.hideseek;
/*
Details Model Class for Custom List View
 */
public class Details {
    private String LostFound;
    private String mName;
    private String mContactNumber;
    private String mObjectType;
    private String mDescription;
    private String mEmail;
    private String mId;

    //Empty because defining a custom constructor
    public Details(){

    }

    //custom constructor
    public Details(String lostFound,String name,String contactNumber,String objectType,String description,String email){
        this.LostFound=lostFound;
        this.mName=name;
        this.mContactNumber=contactNumber;
        this.mObjectType=objectType;
        this.mDescription=description;
        this.mEmail=email;
    }

    public Details(String Id,String lostFound,String name,String contactNumber,String objectType,String description,String email){
        this.mId = Id;
        this.LostFound=lostFound;
        this.mName=name;
        this.mContactNumber=contactNumber;
        this.mObjectType=objectType;
        this.mDescription=description;
        this.mEmail=email;
    }


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
}
