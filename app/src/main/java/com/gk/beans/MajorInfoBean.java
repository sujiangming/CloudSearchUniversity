package com.gk.beans;

import java.util.List;

/**
 * Created by JDRY-SJM on 2017/11/23.
 */

public class MajorInfoBean {
    private int status;
    private String message;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean{
        /**
         * capacityRequirements : 只要是个人
         * developmentProspect : 非常好
         * employPersonsRequire : 只要是个人
         * graduationTo : 天桥下
         * jobProspects : 非常好
         * knowledgeSkills : 打农药坚持一个小时
         * majorGoal : 未来国家主席
         * majorId : 402881fb5fce47d2015fce6030ca0001
         * majorName : 哲学
         * majorProfile : 很好的专业
         * minorCourses : 打王者农药
         * necessaryCertificates : 傻逼证
         * requiredCourse : 吃饭睡觉拉屎
         * salary : 1000
         */

        private String capacityRequirements;
        private String developmentProspect;
        private String employPersonsRequire;
        private String graduationTo;
        private String jobProspects;
        private String knowledgeSkills;
        private String majorGoal;
        private String majorId;
        private String majorName;
        private String majorProfile;
        private String majorCode;
        private String majorType;
        private String minorCourses;
        private String necessaryCertificates;
        private String requiredCourse;
        private String salary;

        public String getMajorType() {
            return majorType;
        }

        public void setMajorType(String majorType) {
            this.majorType = majorType;
        }

        public String getCapacityRequirements() {
            return capacityRequirements;
        }

        public void setCapacityRequirements(String capacityRequirements) {
            this.capacityRequirements = capacityRequirements;
        }

        public String getDevelopmentProspect() {
            return developmentProspect;
        }

        public void setDevelopmentProspect(String developmentProspect) {
            this.developmentProspect = developmentProspect;
        }

        public String getEmployPersonsRequire() {
            return employPersonsRequire;
        }

        public void setEmployPersonsRequire(String employPersonsRequire) {
            this.employPersonsRequire = employPersonsRequire;
        }

        public String getGraduationTo() {
            return graduationTo;
        }

        public void setGraduationTo(String graduationTo) {
            this.graduationTo = graduationTo;
        }

        public String getJobProspects() {
            return jobProspects;
        }

        public void setJobProspects(String jobProspects) {
            this.jobProspects = jobProspects;
        }

        public String getKnowledgeSkills() {
            return knowledgeSkills;
        }

        public void setKnowledgeSkills(String knowledgeSkills) {
            this.knowledgeSkills = knowledgeSkills;
        }

        public String getMajorGoal() {
            return majorGoal;
        }

        public void setMajorGoal(String majorGoal) {
            this.majorGoal = majorGoal;
        }

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

        public String getMajorProfile() {
            return majorProfile;
        }

        public void setMajorProfile(String majorProfile) {
            this.majorProfile = majorProfile;
        }

        public String getMinorCourses() {
            return minorCourses;
        }

        public void setMinorCourses(String minorCourses) {
            this.minorCourses = minorCourses;
        }

        public String getNecessaryCertificates() {
            return necessaryCertificates;
        }

        public void setNecessaryCertificates(String necessaryCertificates) {
            this.necessaryCertificates = necessaryCertificates;
        }

        public String getRequiredCourse() {
            return requiredCourse;
        }

        public void setRequiredCourse(String requiredCourse) {
            this.requiredCourse = requiredCourse;
        }

        public String getSalary() {
            return salary;
        }

        public void setSalary(String salary) {
            this.salary = salary;
        }

        public String getMajorCode() {
            return majorCode;
        }

        public void setMajorCode(String majorCode) {
            this.majorCode = majorCode;
        }
    }
}
