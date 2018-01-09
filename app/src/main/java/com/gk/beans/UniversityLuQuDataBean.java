package com.gk.beans;

import java.util.List;

/**
 * Created by JDRY-SJM on 2018/1/9.
 */

public class UniversityLuQuDataBean {

    /**
     * message : 查询成功
     * status : 1
     * mData : [{"majorAdmissionsData":[{"area":"贵州","highestScore":"650","id":"402881fb5fce61ac015fcec85ccb0001","lowestScore":"498","majorBasicId":"402881fb5fce61ac015fcec85ccb0009","subjectType":"理科","yearStr":"2017"},{"area":"贵州","highestScore":"688","id":"402881fb5fce61ac015fcec85ccb0002","lowestScore":"480","majorBasicId":"402881fb5fce61ac015fcec85ccb0009","subjectType":"理科","yearStr":"2016"}],"majorId":"402881fb5fce61ac015fcec85ccb0009","majorName":"作物生产技术"},{"majorAdmissionsData":[{"area":"贵州","highestScore":"605","id":"402881fb5fce47d2015fce6030ca0003","lowestScore":"420","majorBasicId":"402881fb5fce47d2015fce6030ca0001","subjectType":"理科","yearStr":"2017"},{"area":"贵州","highestScore":"655","id":"402881fb5fce47d2015fce6030ca0004","lowestScore":"400","majorBasicId":"402881fb5fce47d2015fce6030ca0001","subjectType":"理科","yearStr":"2016"}],"majorId":"402881fb5fce47d2015fce6030ca0001","majorName":"哲学"}]
     */

    /**
     * majorAdmissionsData : [{"area":"贵州","highestScore":"650","id":"402881fb5fce61ac015fcec85ccb0001","lowestScore":"498","majorBasicId":"402881fb5fce61ac015fcec85ccb0009","subjectType":"理科","yearStr":"2017"},{"area":"贵州","highestScore":"688","id":"402881fb5fce61ac015fcec85ccb0002","lowestScore":"480","majorBasicId":"402881fb5fce61ac015fcec85ccb0009","subjectType":"理科","yearStr":"2016"}]
     * majorId : 402881fb5fce61ac015fcec85ccb0009
     * majorName : 作物生产技术
     */

    private String majorId;
    private String majorName;
    private List<MajorAdmissionsDataBean> majorAdmissionsData;

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

    public List<MajorAdmissionsDataBean> getMajorAdmissionsData() {
        return majorAdmissionsData;
    }

    public void setMajorAdmissionsData(List<MajorAdmissionsDataBean> majorAdmissionsData) {
        this.majorAdmissionsData = majorAdmissionsData;
    }

    public static class MajorAdmissionsDataBean {
        /**
         * area : 贵州
         * highestScore : 650
         * id : 402881fb5fce61ac015fcec85ccb0001
         * lowestScore : 498
         * majorBasicId : 402881fb5fce61ac015fcec85ccb0009
         * subjectType : 理科
         * yearStr : 2017
         */

        private String area;
        private String highestScore;
        private String id;
        private String lowestScore;
        private String majorBasicId;
        private String subjectType;
        private String yearStr;

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getHighestScore() {
            return highestScore;
        }

        public void setHighestScore(String highestScore) {
            this.highestScore = highestScore;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLowestScore() {
            return lowestScore;
        }

        public void setLowestScore(String lowestScore) {
            this.lowestScore = lowestScore;
        }

        public String getMajorBasicId() {
            return majorBasicId;
        }

        public void setMajorBasicId(String majorBasicId) {
            this.majorBasicId = majorBasicId;
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
}
