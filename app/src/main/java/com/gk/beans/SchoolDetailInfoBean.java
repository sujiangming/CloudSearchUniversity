package com.gk.beans;

import java.util.List;

/**
 * Created by JDRY-SJM on 2017/12/26.
 */

public class SchoolDetailInfoBean {

    /**
     * isDoubleTop : 1
     * isNef : 1
     * isToo : 1
     * schoolAffiliate : 贵州教育局
     * schoolArea : 贵州省贵阳市
     * schoolBatch : 2
     * schoolCategory : 1
     * schoolCode : gz001
     * schoolId : 2c948a825fb579f7015fb57fc1bf0001
     * schoolLogo : http://localhost:8080/file/university/007BF78D89C14E619A164838B0E485A4.png
     * schoolName : 贵州大学
     * schoolProfile : 该学校...
     * schoolType : 1
     * recruitPlanList : [{"advanceBatch":100,"area":"贵州","id":"2c948a825fc4e360015fc4e640ba0001","schoolLogo":"http://localhost:8080/file/university/007BF78D89C14E619A164838B0E485A4.png","schoolName":"贵州大学","specializedSubject":100,"undergraduate":100,"uniId":"2c948a825fb579f7015fb57fc1bf0001","yearPlan":"2017"}]
     * admissionDataList : []
     */

    private String isDoubleTop;
    private String isNef;
    private String isToo;
    private String schoolAffiliate;
    private String schoolArea;
    private String schoolBatch;
    private String schoolCategory;
    private String schoolCode;
    private String schoolId;
    private String schoolLogo;
    private String schoolName;
    private String schoolProfile;
    private String schoolType;
    private List<RecruitPlanListBean> recruitPlanList;
    private List<?> admissionDataList;

    public String getIsDoubleTop() {
        return isDoubleTop;
    }

    public void setIsDoubleTop(String isDoubleTop) {
        this.isDoubleTop = isDoubleTop;
    }

    public String getIsNef() {
        return isNef;
    }

    public void setIsNef(String isNef) {
        this.isNef = isNef;
    }

    public String getIsToo() {
        return isToo;
    }

    public void setIsToo(String isToo) {
        this.isToo = isToo;
    }

    public String getSchoolAffiliate() {
        return schoolAffiliate;
    }

    public void setSchoolAffiliate(String schoolAffiliate) {
        this.schoolAffiliate = schoolAffiliate;
    }

    public String getSchoolArea() {
        return schoolArea;
    }

    public void setSchoolArea(String schoolArea) {
        this.schoolArea = schoolArea;
    }

    public String getSchoolBatch() {
        return schoolBatch;
    }

    public void setSchoolBatch(String schoolBatch) {
        this.schoolBatch = schoolBatch;
    }

    public String getSchoolCategory() {
        return schoolCategory;
    }

    public void setSchoolCategory(String schoolCategory) {
        this.schoolCategory = schoolCategory;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

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

    public String getSchoolProfile() {
        return schoolProfile;
    }

    public void setSchoolProfile(String schoolProfile) {
        this.schoolProfile = schoolProfile;
    }

    public String getSchoolType() {
        return schoolType;
    }

    public void setSchoolType(String schoolType) {
        this.schoolType = schoolType;
    }

    public List<RecruitPlanListBean> getRecruitPlanList() {
        return recruitPlanList;
    }

    public void setRecruitPlanList(List<RecruitPlanListBean> recruitPlanList) {
        this.recruitPlanList = recruitPlanList;
    }

    public List<?> getAdmissionDataList() {
        return admissionDataList;
    }

    public void setAdmissionDataList(List<?> admissionDataList) {
        this.admissionDataList = admissionDataList;
    }

    public static class RecruitPlanListBean {
        /**
         * advanceBatch : 100
         * area : 贵州
         * id : 2c948a825fc4e360015fc4e640ba0001
         * schoolLogo : http://localhost:8080/file/university/007BF78D89C14E619A164838B0E485A4.png
         * schoolName : 贵州大学
         * specializedSubject : 100
         * undergraduate : 100
         * uniId : 2c948a825fb579f7015fb57fc1bf0001
         * yearPlan : 2017
         */

        private int advanceBatch;
        private String area;
        private String id;
        private String schoolLogo;
        private String schoolName;
        private int specializedSubject;
        private int undergraduate;
        private String uniId;
        private String yearPlan;

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

        public String getSchoolName() {
            return schoolName;
        }

        public void setSchoolName(String schoolName) {
            this.schoolName = schoolName;
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
}
