package com.gk.beans;

/**
 * Created by JDRY-SJM on 2017/12/20.
 */

public class VIPPriceBean {

    private String id;
    private String vipLevel1Amount;//银卡
    private String vipLevel2Amount; //金卡

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVipLevel1Amount() {
        return vipLevel1Amount;
    }

    public void setVipLevel1Amount(String vipLevel1Amount) {
        this.vipLevel1Amount = vipLevel1Amount;
    }

    public String getVipLevel2Amount() {
        return vipLevel2Amount;
    }

    public void setVipLevel2Amount(String vipLevel2Amount) {
        this.vipLevel2Amount = vipLevel2Amount;
    }
}
