package com.gk.beans;

import java.util.List;

/**
 * Created by JDRY-SJM on 2017/10/30.
 */

public class SchoolZSBean {

    /**
     * recruitPlan1 : [{"planNum":"17","yearStr":2017},{"planNum":"16","yearStr":2016},{"planNum":"14(含增加计划1)","yearStr":2015}]
     * recruitPlan2 : [{"planNum":"15","yearStr":2017},{"planNum":"14","yearStr":2016},{"planNum":"15(含增加计划2)","yearStr":2015}]
     * schoolId : 2c948a8360f9c5d40160f9cd55cf0006
     * schoolLogo : http://101.132.143.37/file/university/841214c5-22af-447c-bfa5-af5f06a0f1a9.png
     * schoolName : 北京大学
     */

    private String schoolId;
    private String schoolLogo;
    private String schoolName;
    private List<RecruitPlan1Bean> recruitPlan1;
    private List<RecruitPlan2Bean> recruitPlan2;

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

    public List<RecruitPlan1Bean> getRecruitPlan1() {
        return recruitPlan1;
    }

    public void setRecruitPlan1(List<RecruitPlan1Bean> recruitPlan1) {
        this.recruitPlan1 = recruitPlan1;
    }

    public List<RecruitPlan2Bean> getRecruitPlan2() {
        return recruitPlan2;
    }

    public void setRecruitPlan2(List<RecruitPlan2Bean> recruitPlan2) {
        this.recruitPlan2 = recruitPlan2;
    }

    public static class RecruitPlan1Bean {
        /**
         * planNum : 17
         * yearStr : 2017
         */

        private String planNum;
        private String yearStr;

        public String getPlanNum() {
            return planNum;
        }

        public void setPlanNum(String planNum) {
            this.planNum = planNum;
        }

        public String getYearStr() {
            return yearStr;
        }

        public void setYearStr(String yearStr) {
            this.yearStr = yearStr;
        }
    }

    public static class RecruitPlan2Bean {
        /**
         * planNum : 15
         * yearStr : 2017
         */

        private String planNum;
        private String yearStr;

        public String getPlanNum() {
            return planNum;
        }

        public void setPlanNum(String planNum) {
            this.planNum = planNum;
        }

        public String getYearStr() {
            return yearStr;
        }

        public void setYearStr(String yearStr) {
            this.yearStr = yearStr;
        }
    }
}
