package com.gk.beans;

import java.io.Serializable;

/**
 * Created by JDRY-SJM on 2017/12/13.
 */

public class MBTIResultBean implements Serializable {

    /**
     * id : 2c948a82603a601301603a638e060000
     * username : 15285630464
     * careerType : E,S,T,P
     * careerFeature : 灵活忍耐
     * careerSummary : 灵活忍耐  注重实际  天真率直  富有魅力
     * careerE : 3
     * careerI : 2
     * careerS : 5
     * careerN : 1
     * careerT : 4
     * careerF : 1
     * careerJ : 0
     * careerP : 3
     * myFeature : 灵活忍耐  注重实际  天真率直  富有魅力
     * careerTypeFeature : 1.擅长现场实时解决问题忍度、务实或组合的真实事务。
     * recommendOccupation : 项目经理,开发工程师,设计师
     */
    private String id;
    private String username;
    private String careerType;
    private String careerFeature;
    private String careerSummary;
    private int careerE;
    private int careerI;
    private int careerS;
    private int careerN;
    private int careerT;
    private int careerF;
    private int careerJ;
    private int careerP;
    private String myFeature;
    private String careerTypeFeature;
    private String recommendOccupation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCareerType() {
        return careerType;
    }

    public void setCareerType(String careerType) {
        this.careerType = careerType;
    }

    public String getCareerFeature() {
        return careerFeature;
    }

    public void setCareerFeature(String careerFeature) {
        this.careerFeature = careerFeature;
    }

    public String getCareerSummary() {
        return careerSummary;
    }

    public void setCareerSummary(String careerSummary) {
        this.careerSummary = careerSummary;
    }

    public int getCareerE() {
        return careerE;
    }

    public void setCareerE(int careerE) {
        this.careerE = careerE;
    }

    public int getCareerI() {
        return careerI;
    }

    public void setCareerI(int careerI) {
        this.careerI = careerI;
    }

    public int getCareerS() {
        return careerS;
    }

    public void setCareerS(int careerS) {
        this.careerS = careerS;
    }

    public int getCareerN() {
        return careerN;
    }

    public void setCareerN(int careerN) {
        this.careerN = careerN;
    }

    public int getCareerT() {
        return careerT;
    }

    public void setCareerT(int careerT) {
        this.careerT = careerT;
    }

    public int getCareerF() {
        return careerF;
    }

    public void setCareerF(int careerF) {
        this.careerF = careerF;
    }

    public int getCareerJ() {
        return careerJ;
    }

    public void setCareerJ(int careerJ) {
        this.careerJ = careerJ;
    }

    public int getCareerP() {
        return careerP;
    }

    public void setCareerP(int careerP) {
        this.careerP = careerP;
    }

    public String getMyFeature() {
        return myFeature;
    }

    public void setMyFeature(String myFeature) {
        this.myFeature = myFeature;
    }

    public String getCareerTypeFeature() {
        return careerTypeFeature;
    }

    public void setCareerTypeFeature(String careerTypeFeature) {
        this.careerTypeFeature = careerTypeFeature;
    }

    public String getRecommendOccupation() {
        return recommendOccupation;
    }

    public void setRecommendOccupation(String recommendOccupation) {
        this.recommendOccupation = recommendOccupation;
    }
}
