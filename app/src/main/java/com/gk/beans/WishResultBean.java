package com.gk.beans;

import java.util.List;

/**
 * Created by JDRY-SJM on 2017/12/7.
 */

public class WishResultBean {

    /**
     * birthPlace : 贵州
     * cname : 张三
     * firstBatch : [{"admissionProbability":"30%","isAdjust":"1","lastYearLowestScore":"2016年最低分 666","recommend_majors":"土木工程、计算机科学与技术、岩土工程、软件工程、法学、数学与应用教学","schoolArea":"贵州","schoolName":"大学名称"},{"admissionProbability":"30%","isAdjust":"1","lastYearLowestScore":"2016年最低分 666","recommend_majors":"土木工程、计算机科学与技术、岩土工程、软件工程、法学、数学与应用教学","schoolArea":"贵州","schoolName":"大学名称"},{"admissionProbability":"30%","isAdjust":"1","lastYearLowestScore":"2016年最低分 666","recommend_majors":"土木工程、计算机科学与技术、岩土工程、软件工程、法学、数学与应用教学","schoolArea":"贵州","schoolName":"大学名称"}]
     * heartTest : 创新能力强、动手快...创新能力强、动手快...创新能力强、动手快...
     * intentArea : 北京、上海、山东
     * intentSch : 机械工程、电子、计算机
     * scoreRanking : 500分  28645名
     * secondBatch : [{"admissionProbability":"30%","isAdjust":"1","lastYearLowestScore":"2016年最低分 666","recommend_majors":"土木工程、计算机科学与技术、岩土工程、软件工程、法学、数学与应用教学","schoolArea":"贵州","schoolName":"大学名称"},{"admissionProbability":"30%","isAdjust":"1","lastYearLowestScore":"2016年最低分 666","recommend_majors":"土木工程、计算机科学与技术、岩土工程、软件工程、法学、数学与应用教学","schoolArea":"贵州","schoolName":"大学名称"},{"admissionProbability":"30%","isAdjust":"1","lastYearLowestScore":"2016年最低分 666","recommend_majors":"土木工程、计算机科学与技术、岩土工程、软件工程、法学、数学与应用教学","schoolArea":"贵州","schoolName":"大学名称"}]
     * subjectName : 理科
     */

    private String birthPlace;
    private String cname;
    private String heartTest;
    private String intentArea;
    private String intentSch;
    private String scoreRanking;
    private String reportStatus;
    private String subjectName;
    private List<FirstBatchBean> firstBatch;
    private List<SecondBatchBean> secondBatch;

    public String getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(String reportStatus) {
        this.reportStatus = reportStatus;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getHeartTest() {
        return heartTest;
    }

    public void setHeartTest(String heartTest) {
        this.heartTest = heartTest;
    }

    public String getIntentArea() {
        return intentArea;
    }

    public void setIntentArea(String intentArea) {
        this.intentArea = intentArea;
    }

    public String getIntentSch() {
        return intentSch;
    }

    public void setIntentSch(String intentSch) {
        this.intentSch = intentSch;
    }

    public String getScoreRanking() {
        return scoreRanking;
    }

    public void setScoreRanking(String scoreRanking) {
        this.scoreRanking = scoreRanking;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public List<FirstBatchBean> getFirstBatch() {
        return firstBatch;
    }

    public void setFirstBatch(List<FirstBatchBean> firstBatch) {
        this.firstBatch = firstBatch;
    }

    public List<SecondBatchBean> getSecondBatch() {
        return secondBatch;
    }

    public void setSecondBatch(List<SecondBatchBean> secondBatch) {
        this.secondBatch = secondBatch;
    }

    public static class FirstBatchBean {
        /**
         * admissionProbability : 30%
         * isAdjust : 1
         * lastYearLowestScore : 2016年最低分 666
         * recommend_majors : 土木工程、计算机科学与技术、岩土工程、软件工程、法学、数学与应用教学
         * schoolArea : 贵州
         * schoolName : 大学名称
         */

        private String admissionProbability;
        private String isAdjust;
        private String lastYearLowestScore;
        private String recommend_majors;
        private String schoolArea;
        private String schoolName;

        public String getAdmissionProbability() {
            return admissionProbability;
        }

        public void setAdmissionProbability(String admissionProbability) {
            this.admissionProbability = admissionProbability;
        }

        public String getIsAdjust() {
            return isAdjust;
        }

        public void setIsAdjust(String isAdjust) {
            this.isAdjust = isAdjust;
        }

        public String getLastYearLowestScore() {
            return lastYearLowestScore;
        }

        public void setLastYearLowestScore(String lastYearLowestScore) {
            this.lastYearLowestScore = lastYearLowestScore;
        }

        public String getRecommend_majors() {
            return recommend_majors;
        }

        public void setRecommend_majors(String recommend_majors) {
            this.recommend_majors = recommend_majors;
        }

        public String getSchoolArea() {
            return schoolArea;
        }

        public void setSchoolArea(String schoolArea) {
            this.schoolArea = schoolArea;
        }

        public String getSchoolName() {
            return schoolName;
        }

        public void setSchoolName(String schoolName) {
            this.schoolName = schoolName;
        }
    }

    public static class SecondBatchBean {
        /**
         * admissionProbability : 30%
         * isAdjust : 1
         * lastYearLowestScore : 2016年最低分 666
         * recommend_majors : 土木工程、计算机科学与技术、岩土工程、软件工程、法学、数学与应用教学
         * schoolArea : 贵州
         * schoolName : 大学名称
         */

        private String admissionProbability;
        private String isAdjust;
        private String lastYearLowestScore;
        private String recommend_majors;
        private String schoolArea;
        private String schoolName;

        public String getAdmissionProbability() {
            return admissionProbability;
        }

        public void setAdmissionProbability(String admissionProbability) {
            this.admissionProbability = admissionProbability;
        }

        public String getIsAdjust() {
            return isAdjust;
        }

        public void setIsAdjust(String isAdjust) {
            this.isAdjust = isAdjust;
        }

        public String getLastYearLowestScore() {
            return lastYearLowestScore;
        }

        public void setLastYearLowestScore(String lastYearLowestScore) {
            this.lastYearLowestScore = lastYearLowestScore;
        }

        public String getRecommend_majors() {
            return recommend_majors;
        }

        public void setRecommend_majors(String recommend_majors) {
            this.recommend_majors = recommend_majors;
        }

        public String getSchoolArea() {
            return schoolArea;
        }

        public void setSchoolArea(String schoolArea) {
            this.schoolArea = schoolArea;
        }

        public String getSchoolName() {
            return schoolName;
        }

        public void setSchoolName(String schoolName) {
            this.schoolName = schoolName;
        }
    }
}
