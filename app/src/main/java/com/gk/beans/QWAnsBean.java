package com.gk.beans;

import java.io.Serializable;

/**
 * Created by JDRY-SJM on 2017/12/2.
 */

public class QWAnsBean implements Serializable {
    private String ansContent;//问题内容
    private String ansId;
    private long ansTime;
    private String ansUserId;
    private String queId;//"2c948a825fd9dc93015fd9e33dca0001",  //问题id
    private String headImg;//": "http://101.132.143.37/file/15285630464/9964F255D31E4A208F456886D63DD176.jpg",  //头像
    private String nickName;//": "tttttt",  //昵称

    public String getAnsContent() {
        return ansContent;
    }

    public void setAnsContent(String ansContent) {
        this.ansContent = ansContent;
    }

    public String getAnsId() {
        return ansId;
    }

    public void setAnsId(String ansId) {
        this.ansId = ansId;
    }

    public long getAnsTime() {
        return ansTime;
    }

    public void setAnsTime(long ansTime) {
        this.ansTime = ansTime;
    }

    public String getAnsUserId() {
        return ansUserId;
    }

    public void setAnsUserId(String ansUserId) {
        this.ansUserId = ansUserId;
    }

    public String getQueId() {
        return queId;
    }

    public void setQueId(String queId) {
        this.queId = queId;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
