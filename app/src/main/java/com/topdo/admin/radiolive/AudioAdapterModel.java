package com.topdo.admin.radiolive;

public class AudioAdapterModel {

    private String audioDescription;
    private String audioId;
    private String userPhoto;

    public AudioAdapterModel(String desc, String id, String userPhoto) {
        this.audioDescription = desc;
        this.audioId = id;
        this.userPhoto = userPhoto;
    }

    public AudioAdapterModel() {
    }

    public String getAudioDescription() {
        return audioDescription;
    }

    public void setAudioDescription(String audioDescription) {
        this.audioDescription = audioDescription;
    }

    public String getAudioId() {
        return audioId;
    }

    public void setAudioId(String audioId) {
        this.audioId = audioId;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }
}
