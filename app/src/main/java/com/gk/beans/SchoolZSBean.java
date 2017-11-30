package com.gk.beans;

/**
 * Created by JDRY-SJM on 2017/10/30.
 */

public class SchoolZSBean {
    /**
     * advanceBatch : 100
     * area : 贵州
     * id : 2c948a825fc4e360015fc4e640ba0001
     * schoolLogo : http://101.132.143.37/file/university/007BF78D89C14E619A164838B0E485A4.png
     * specializedSubject : 100
     * undergraduate : 100
     * uniId : 2c948a825fb579f7015fb57fc1bf0001
     * yearPlan : 2017
     */

    private int advanceBatch;
    private String area;
    private String id;
    private String schoolLogo;
    private int specializedSubject;
    private int undergraduate;
    private String uniId;
    private String yearPlan;
    private String schoolName;

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public int getAdvanceBatch() {
        return advanceBatch;
    }

    public void setAdvanceBatch(int advanceBatch) {
        this.advanceBatch = advanceBatch;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSchoolLogo() {
        return schoolLogo;
    }

    public void setSchoolLogo(String schoolLogo) {
        this.schoolLogo = schoolLogo;
    }

    public int getSpecializedSubject() {
        return specializedSubject;
    }

    public void setSpecializedSubject(int specializedSubject) {
        this.specializedSubject = specializedSubject;
    }

    public int getUndergraduate() {
        return undergraduate;
    }

    public void setUndergraduate(int undergraduate) {
        this.undergraduate = undergraduate;
    }

    public String getUniId() {
        return uniId;
    }

    public void setUniId(String uniId) {
        this.uniId = uniId;
    }

    public String getYearPlan() {
        return yearPlan;
    }

    public void setYearPlan(String yearPlan) {
        this.yearPlan = yearPlan;
    }
}
