package com.gk.beans;

import java.util.List;

/**
 * Created by JDRY-SJM on 2017/11/18.
 */

public class LectureBean {

    private List<LnztBean> lnzt;
    private List<MnsjBean> mnsj;
    private List<MsjtBean> msjt;

    public List<LnztBean> getLnzt() {
        return lnzt;
    }

    public void setLnzt(List<LnztBean> lnzt) {
        this.lnzt = lnzt;
    }

    public List<MnsjBean> getMnsj() {
        return mnsj;
    }

    public void setMnsj(List<MnsjBean> mnsj) {
        this.mnsj = mnsj;
    }

    public List<MsjtBean> getMsjt() {
        return msjt;
    }

    public void setMsjt(List<MsjtBean> msjt) {
        this.msjt = msjt;
    }

    public static class LnztBean {
        /**
         * attentionNum : 0
         * course : shuxue
         * fileName : 幼儿管理-幼儿考勤-考勤2.png
         * fileUrl : http://192.168.1.123:8080/file/materials/1F6FACD12E124AB6BD25E670C5916395.png
         * id : 2c948a825f96d8a4015f96db237c0000
         * logo : http://192.168.1.123:8080/file/child/692408C34EED45B6A36927FC7CB868DB.png
         * name : 真题
         * type : 2
         * uploadTime : 1510581576000
         */

        private int attentionNum;
        private String course;
        private String fileName;
        private String fileUrl;
        private String id;
        private String logo;
        private String name;
        private String type;
        private long uploadTime;

        public int getAttentionNum() {
            return attentionNum;
        }

        public void setAttentionNum(int attentionNum) {
            this.attentionNum = attentionNum;
        }

        public String getCourse() {
            return course;
        }

        public void setCourse(String course) {
            this.course = course;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getFileUrl() {
            return fileUrl;
        }

        public void setFileUrl(String fileUrl) {
            this.fileUrl = fileUrl;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public long getUploadTime() {
            return uploadTime;
        }

        public void setUploadTime(long uploadTime) {
            this.uploadTime = uploadTime;
        }
    }

    public static class MnsjBean {
        /**
         * attentionNum : 0
         * course : yuwen
         * fileName : 2017年事业单位综合能力试题及答案.docx
         * fileUrl : http://192.168.1.123:8080/file/materials/590F9DED92CC4275B45B25C52837ED2F.docx
         * id : 2c948a825fb5b5ee015fb5bd86a70000
         * logo : http://192.168.1.123:8080/file/
         * name : xxx
         * type : 3
         * uploadTime : 1510582618000
         */

        private int attentionNum;
        private String course;
        private String fileName;
        private String fileUrl;
        private String id;
        private String logo;
        private String name;
        private String type;
        private long uploadTime;

        public int getAttentionNum() {
            return attentionNum;
        }

        public void setAttentionNum(int attentionNum) {
            this.attentionNum = attentionNum;
        }

        public String getCourse() {
            return course;
        }

        public void setCourse(String course) {
            this.course = course;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getFileUrl() {
            return fileUrl;
        }

        public void setFileUrl(String fileUrl) {
            this.fileUrl = fileUrl;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public long getUploadTime() {
            return uploadTime;
        }

        public void setUploadTime(long uploadTime) {
            this.uploadTime = uploadTime;
        }
    }

    public static class MsjtBean {
        /**
         * attentionNum : 0
         * course : yuwen
         * fileName : 2017-10-17到2017-10-24 (小1班)的出勤明细.xls
         * fileUrl : http://192.168.1.123:8080/file/materials/908145A1D2E840E7995939129B17148C.xls
         * id : 2c948a825f96cbbf015f96d6378e0000
         * logo : http://192.168.1.123:8080/file/child/2AE90EBB9DE943AD938D48BB5EB63427.png
         * name : 高考语文
         * type : 1
         * uploadTime : 1510581567000
         */

        private int attentionNum;
        private String course;
        private String fileName;
        private String fileUrl;
        private String id;
        private String logo;
        private String name;
        private String type;
        private long uploadTime;

        public int getAttentionNum() {
            return attentionNum;
        }

        public void setAttentionNum(int attentionNum) {
            this.attentionNum = attentionNum;
        }

        public String getCourse() {
            return course;
        }

        public void setCourse(String course) {
            this.course = course;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getFileUrl() {
            return fileUrl;
        }

        public void setFileUrl(String fileUrl) {
            this.fileUrl = fileUrl;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public long getUploadTime() {
            return uploadTime;
        }

        public void setUploadTime(long uploadTime) {
            this.uploadTime = uploadTime;
        }
    }
}
