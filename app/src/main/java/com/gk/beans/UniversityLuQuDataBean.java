package com.gk.beans;

/**
 * Created by JDRY-SJM on 2018/1/9.
 */

public class UniversityLuQuDataBean {
    /**
     * highestScore :
     * lowestScore :
     * majorName : 中草药栽培与鉴定
     * subjectType : 理科
     * yearStr : 2017
     */

    private String highestScore;
    private String lowestScore;
    private String majorName;
    private String subjectType;
    private String yearStr;

    public String getHighestScore() {
        return highestScore;
    }

    public void setHighestScore(String highestScore) {
        this.highestScore = highestScore;
    }

    public String getLowestScore() {
        return lowestScore;
    }

    public void setLowestScore(String lowestScore) {
        this.lowestScore = lowestScore;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    public String getYearStr() {
        return yearStr;
    }

    public void setYearStr(String yearStr) {
        this.yearStr = yearStr;
    }
}
