package com.gk.beans;

/**
 * Created by JDRY-SJM on 2018/1/9.
 */

public class UniversityZsPlanItemBean {

    /**
     * message : 查询成功
     * status : 1
     * data : [{"majorId":"402881fb5fce47d2015fce6030ca0001","majorName":"哲学","majorRecruitPlan":[{"area":"贵州","id":"402881fb5fce47d2015fce6030ca0001","majorBasicId":"402881fb5fce47d2015fce6030ca0001","planNum":100,"subjectType":"理科","yearStr":"2017"},{"area":"贵州","id":"402881fb5fce47d2015fce6030ca0002","majorBasicId":"402881fb5fce47d2015fce6030ca0001","planNum":65,"subjectType":"文科","yearStr":"2016"}]}]
     */
    private String majorId;
    private String majorName;
    private String area;
    private String id;
    private String majorBasicId;
    private String planNum;
    private String subjectType;
    private String yearStr;

    public String getMajorId() {
        return majorId;
    }

    public void setMajorId(String majorId) {
        this.majorId = majorId;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
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

    public String getMajorBasicId() {
        return majorBasicId;
    }

    public void setMajorBasicId(String majorBasicId) {
        this.majorBasicId = majorBasicId;
    }

    public String getPlanNum() {
        return planNum;
    }

    public void setPlanNum(String planNum) {
        this.planNum = planNum;
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
