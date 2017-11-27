package com.gk.beans;

import java.util.List;

/**
 * Created by JDRY-SJM on 2017/10/30.
 */

public class QuerySchoolBean {

    /**
     * data : [{"isDoubleTop":"1","isNef":"1","isToo":"1","schoolAffiliate":"教育部","schoolArea":"遵义","schoolBatch":"1","schoolCategory":"2","schoolCode":"ZY001","schoolId":"2c948a825f445e41015f447b5f400001","schoolLogo":"http://101.132.143.37/file/","schoolName":"遵义师范学院","schoolProfile":"ffff对方是否","schoolType":"1"},{"isDoubleTop":"1","isNef":"1","isToo":"1","schoolAffiliate":"贵州教育局","schoolArea":"贵州省贵阳市","schoolBatch":"2","schoolCategory":"1","schoolCode":"gz001","schoolId":"2c948a825fb579f7015fb57fc1bf0001","schoolLogo":"http://101.132.143.37/file/university/007BF78D89C14E619A164838B0E485A4.png","schoolName":"贵州大学","schoolProfile":"该学校...","schoolType":"1"}]
     * message : 查询成功
     * status : 1
     */

    private String message;
    private int status;
    private List<DataBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * isDoubleTop : 1
         * isNef : 1
         * isToo : 1
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
    }
}
