package com.gk.beans;

import java.util.List;

/**
 * Created by JDRY-SJM on 2017/11/26.
 */

public class MaterialItemBean {
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

    public static class DataBean {
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
}
