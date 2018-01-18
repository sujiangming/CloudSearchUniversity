package com.gk.beans;

/**
 * Created by JDRY-SJM on 2017/12/20.
 */

public class VIPPriceBean {

    private String id;
    private String vipLevel1Amount;//银卡
    private String vipLevel2Amount; //金卡
    private String vipLevel3Amount; //钻石卡
    private String heartTestAmount; //心理测试
    private String admissionRiskAmount; //风险评估
    private String sameScoreToAmount;   //同分去向

    public String getHeartTestAmount() {
        return heartTestAmount;
    }

    public void setHeartTestAmount(String heartTestAmount) {
        this.heartTestAmount = heartTestAmount;
    }

    public String getAdmissionRiskAmount() {
        return admissionRiskAmount;
    }

    public void setAdmissionRiskAmount(String admissionRiskAmount) {
        this.admissionRiskAmount = admissionRiskAmount;
    }

    public String getSameScoreToAmount() {
        return sameScoreToAmount;
    }

    public void setSameScoreToAmount(String sameScoreToAmount) {
        this.sameScoreToAmount = sameScoreToAmount;
    }

    public String getVipLevel3Amount() {
        return vipLevel3Amount;
    }

    public void setVipLevel3Amount(String vipLevel3Amount) {
        this.vipLevel3Amount = vipLevel3Amount;
    }

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
