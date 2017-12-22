package com.gk.beans;

import java.io.Serializable;

/**
 * Created by JDRY-SJM on 2017/12/22.
 */

public class OnLiveBean implements Serializable {
    private String id;
    private String liveCrossLogo;
    private String liveName;
    private String livePullUrl;
    private String livePushUrl;
    private long liveStartTime;
    private int liveStatus;
    private String liveUser;
    private String liveVerticalLogo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLiveCrossLogo() {
        return liveCrossLogo;
    }

    public void setLiveCrossLogo(String liveCrossLogo) {
        this.liveCrossLogo = liveCrossLogo;
    }

    public String getLiveName() {
        return liveName;
    }

    public void setLiveName(String liveName) {
        this.liveName = liveName;
    }

    public String getLivePullUrl() {
        return livePullUrl;
    }

    public void setLivePullUrl(String livePullUrl) {
        this.livePullUrl = livePullUrl;
    }

    public String getLivePushUrl() {
        return livePushUrl;
    }

    public void setLivePushUrl(String livePushUrl) {
        this.livePushUrl = livePushUrl;
    }

    public long getLiveStartTime() {
        return liveStartTime;
    }

    public void setLiveStartTime(long liveStartTime) {
        this.liveStartTime = liveStartTime;
    }

    public int getLiveStatus() {
        return liveStatus;
    }

    public void setLiveStatus(int liveStatus) {
        this.liveStatus = liveStatus;
    }

    public String getLiveUser() {
        return liveUser;
    }

    public void setLiveUser(String liveUser) {
        this.liveUser = liveUser;
    }

    public String getLiveVerticalLogo() {
        return liveVerticalLogo;
    }

    public void setLiveVerticalLogo(String liveVerticalLogo) {
        this.liveVerticalLogo = liveVerticalLogo;
    }
}
