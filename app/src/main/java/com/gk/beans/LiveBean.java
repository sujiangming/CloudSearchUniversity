package com.gk.beans;

import java.io.Serializable;

/**
 * Created by JDRY-SJM on 2017/11/18.
 */

public class LiveBean implements Serializable{

    /**
     * attentionNum : 0
     * id : 2c948a825fb5e05d015fb5efff270001
     * speaker : 卜小超
     * uploadTime : 1510585925000
     * videoLogo : http://192.168.1.123:8080/file/video/FFB62F36BF32404CAE560424F9C67FC9.png
     * videoName : 论上班的重要性
     * videoSummary : 这是一个弱肉强食的时代...
     * videoUrl : www.baidu.com
     * zanNum : 0
     */

    private int attentionNum;
    private String id;
    private String speaker;
    private long uploadTime;
    private String videoLogo;
    private String videoName;
    private String videoSummary;
    private String videoUrl;
    private int zanNum;

    public int getAttentionNum() {
        return attentionNum;
    }

    public void setAttentionNum(int attentionNum) {
        this.attentionNum = attentionNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public long getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(long uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getVideoLogo() {
        return videoLogo;
    }

    public void setVideoLogo(String videoLogo) {
        this.videoLogo = videoLogo;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getVideoSummary() {
        return videoSummary;
    }

    public void setVideoSummary(String videoSummary) {
        this.videoSummary = videoSummary;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public int getZanNum() {
        return zanNum;
    }

    public void setZanNum(int zanNum) {
        this.zanNum = zanNum;
    }
}
