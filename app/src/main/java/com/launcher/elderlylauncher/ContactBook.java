package com.launcher.elderlylauncher;

import android.graphics.Bitmap;

/**
 * Created by nicha on 9/26/16.
 */
public class ContactBook {

    private Bitmap mAvatar;
    private String mName;
    private String mLName;
    private String mUsername;
    private String mPhone;
    private String mEmail;

    public ContactBook(Bitmap mAvatar, String mUsername, String mName, String mLName, String mPhone, String mEmail) {
        this.mAvatar = mAvatar;
        this.mUsername = mUsername;
        this.mName = mName;
        this.mLName = mLName;
        this.mPhone = mPhone;
        this.mEmail = mEmail;
    }

    public Bitmap getmAvatar() {
        return mAvatar;
    }
    public String getmName() {
        return mName;
    }
    public String getmLName() {
        return mLName;
    }
    public String getmUsername() {
        return mUsername;
    }
    public String getmPhone() {
        return mPhone;
    }
    public String getmEmail() {
        return mEmail;
    }
    public void setmAvatar(Bitmap mAvatar) {
        this.mAvatar = mAvatar;
    }
    public void setmName(String mName) {
        this.mName = mName;
    }
    public void setmUsername(String mUsername) {
        this.mUsername = mUsername;
    }
    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }
    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

}
