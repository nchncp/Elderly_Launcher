package com.launcher.elderlylauncher;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nicha on 11/6/16.
 */

public class GalleryPhotoModel {
    @SerializedName("ContentID")
    private String ContentID;
    @SerializedName("Topic")
    private String Topic;
    @SerializedName("Message")
    private String Message;
    @SerializedName("PicturePath")
    private String PicturePath;
    @SerializedName("TimeSend")
    private String TimeSend;
    @SerializedName("DateSend")
    private String DateSend;

    public GalleryPhotoModel(String ContentID, String Topic, String Message, String PicturePath, String DateSend, String TimeSend) {
        this.ContentID = ContentID;
        this.Topic = Topic;
        this.Message = Message;
        this.PicturePath = PicturePath;
        this.DateSend = DateSend;
        this.TimeSend = TimeSend;
    }

    public String getPhotoContentID() {
        return ContentID;
    }
    public String getPhotoTopic() {
        return Topic;
    }
    public String getPhotoMessage() {
        return Message;
    }
    public String getPhotoPicturePath() {
        return PicturePath;
    }
    public String getPhotoDateSend() {
        return DateSend;
    }
    public String getPhotoTimeSend() {
        return TimeSend;
    }
}
