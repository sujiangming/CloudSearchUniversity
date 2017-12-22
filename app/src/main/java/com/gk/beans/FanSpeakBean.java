package com.gk.beans;

/**
 * Created by JDRY-SJM on 2017/12/22.
 */

public class FanSpeakBean {
    private String id;
    private String fansSpeak;
    private String liveRoomId;
    private long speakTime;
    private String username;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFansSpeak() {
        return fansSpeak;
    }

    public void setFansSpeak(String fansSpeak) {
        this.fansSpeak = fansSpeak;
    }

    public String getLiveRoomId() {
        return liveRoomId;
    }

    public void setLiveRoomId(String liveRoomId) {
        this.liveRoomId = liveRoomId;
    }

    public long getSpeakTime() {
        return speakTime;
    }

    public void setSpeakTime(long speakTime) {
        this.speakTime = speakTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
