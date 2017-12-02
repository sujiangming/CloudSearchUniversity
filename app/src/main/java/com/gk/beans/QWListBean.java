package com.gk.beans;

import java.io.Serializable;

/**
 * Created by JDRY-SJM on 2017/12/2.
 */

public class QWListBean implements Serializable{

    /**
     * attentionTimes : 0
     * headImg : http://101.132.143.37/file/15285630464/9964F255D31E4A208F456886D63DD176.jpg
     * nickName : tttttt
     * queContent : 第一个问题的内容
     * queId : 2c948a825fd9dc93015fd9e33dca0001
     * queTime : 1511189069000
     * queTitle : 第一个问题
     * queUserId : 15285630464
     * viewTimes : 0
     */

    private int attentionTimes;
    private String headImg;
    private String nickName;
    private String queContent;
    private String queId;
    private long queTime;
    private String queTitle;
    private String queUserId;
    private int viewTimes;
    private int commentTimes;

    public int getAttentionTimes() {
        return attentionTimes;
    }

    public void setAttentionTimes(int attentionTimes) {
        this.attentionTimes = attentionTimes;
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

    public String getQueContent() {
        return queContent;
    }

    public void setQueContent(String queContent) {
        this.queContent = queContent;
    }

    public String getQueId() {
        return queId;
    }

    public void setQueId(String queId) {
        this.queId = queId;
    }

    public long getQueTime() {
        return queTime;
    }

    public void setQueTime(long queTime) {
        this.queTime = queTime;
    }

    public String getQueTitle() {
        return queTitle;
    }

    public void setQueTitle(String queTitle) {
        this.queTitle = queTitle;
    }

    public String getQueUserId() {
        return queUserId;
    }

    public void setQueUserId(String queUserId) {
        this.queUserId = queUserId;
    }

    public int getViewTimes() {
        return viewTimes;
    }

    public void setViewTimes(int viewTimes) {
        this.viewTimes = viewTimes;
    }

    public int getCommentTimes() {
        return commentTimes;
    }

    public void setCommentTimes(int commentTimes) {
        this.commentTimes = commentTimes;
    }
}
