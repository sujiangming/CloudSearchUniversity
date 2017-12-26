package com.gk.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by JDRY-SJM on 2017/11/24.
 */

public class SchoolRankBean implements Serializable{

    /**
     * isDoubleTop : 1
     * isNef : 1
     * isToo : 1
     * rankings : [{"id":"2c948a825fc9d631015fc9e37f740004","uniId":"2c948a825f445e41015f447b5f400001","uniRanking":"1","uniYear":"2017"}]
     * schoolAffiliate : 教育部
     * schoolArea : 遵义
     * schoolBatch : 1
     * schoolCategory : 2
     * schoolCode : ZY001
     * schoolId : 2c948a825f445e41015f447b5f400001
     * schoolLogo : http://101.132.143.37/file/
     * schoolName : 遵义师范学院
     * schoolProfile : ffff对方是否
     * schoolType : 1
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
    private List<RankingsBean> rankings;

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

    public List<RankingsBean> getRankings() {
        return rankings;
    }

    public void setRankings(List<RankingsBean> rankings) {
        this.rankings = rankings;
    }

    public static class RankingsBean implements Serializable{
        /**
         * id : 2c948a825fc9d631015fc9e37f740004
         * uniId : 2c948a825f445e41015f447b5f400001
         * uniRanking : 1
         * uniYear : 2017
         */

        private String id;
        private String uniId;
        private String uniRanking;
        private String uniYear;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUniId() {
            return uniId;
        }

        public void setUniId(String uniId) {
            this.uniId = uniId;
        }

        public String getUniRanking() {
            return uniRanking;
        }

        public void setUniRanking(String uniRanking) {
            this.uniRanking = uniRanking;
        }

        public String getUniYear() {
            return uniYear;
        }

        public void setUniYear(String uniYear) {
            this.uniYear = uniYear;
        }
    }
}
