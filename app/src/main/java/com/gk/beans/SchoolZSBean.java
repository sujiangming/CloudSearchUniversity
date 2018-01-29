package com.gk.beans;

import java.util.List;

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
    private String schoolId;
    private String schoolLogo;
    private String schoolName;
    private List<RecruitPlan> recruitPlan;

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolLogo() {
        return schoolLogo;
    }

    public void setSchoolLogo(String schoolLogo) {
        this.schoolLogo = schoolLogo;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public List<RecruitPlan> getRecruitPlan() {
        return recruitPlan;
    }

    public void setRecruitPlan(List<RecruitPlan> recruitPlan) {
        this.recruitPlan = recruitPlan;
    }

    public static class RecruitPlan{
        private String yearStr;
        private String planNum;

        public String getYearStr() {
            return yearStr;
        }

        public void setYearStr(String yearStr) {
            this.yearStr = yearStr;
        }

        public String getPlanNum() {
            return planNum;
        }

        public void setPlanNum(String planNum) {
            this.planNum = planNum;
        }
    }
}
