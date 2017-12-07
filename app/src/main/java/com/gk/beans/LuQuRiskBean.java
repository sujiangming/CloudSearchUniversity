package com.gk.beans;

import java.util.List;

/**
 * Created by JDRY-SJM on 2017/12/8.
 */

public class LuQuRiskBean {

    /**
     * admissionProbability : 83%
     * recommendSchs : [{"schoolId":"2c948a825f445e41015f447b5f400001","schoolLogo":"http://localhost:8080/file/","schoolMajor":"计算机科学与技术","schoolName":"遵义师范学院"},{"schoolId":"2c948a825fb579f7015fb57fc1bf0001","schoolLogo":"http://localhost:8080/file/university/007BF78D89C14E619A164838B0E485A4.png","schoolMajor":"计算机科学与技术","schoolName":"贵州大学"},{"schoolId":"4028819d600142840160014426f60000","schoolLogo":"http://localhost:8080/file/","schoolMajor":"计算机科学与技术","schoolName":"浙江大学"}]
     */

    private String admissionProbability;
    private List<RecommendSchsBean> recommendSchs;

    public String getAdmissionProbability() {
        return admissionProbability;
    }

    public void setAdmissionProbability(String admissionProbability) {
        this.admissionProbability = admissionProbability;
    }

    public List<RecommendSchsBean> getRecommendSchs() {
        return recommendSchs;
    }

    public void setRecommendSchs(List<RecommendSchsBean> recommendSchs) {
        this.recommendSchs = recommendSchs;
    }

    public static class RecommendSchsBean {
        /**
         * schoolId : 2c948a825f445e41015f447b5f400001
         * schoolLogo : http://localhost:8080/file/
         * schoolMajor : 计算机科学与技术
         * schoolName : 遵义师范学院
         */

        private String schoolId;
        private String schoolLogo;
        private String schoolMajor;
        private String schoolName;

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

        public String getSchoolMajor() {
            return schoolMajor;
        }

        public void setSchoolMajor(String schoolMajor) {
            this.schoolMajor = schoolMajor;
        }

        public String getSchoolName() {
            return schoolName;
        }

        public void setSchoolName(String schoolName) {
            this.schoolName = schoolName;
        }
    }
}
