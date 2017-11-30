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
         * fileName : 2017-10-17到2017-10-24 (小1班)的出勤明细.xls
         * fileUrl : http://101.132.143.37/file/materials/908145A1D2E840E7995939129B17148C.xls
         * id : 2c948a825f96cbbf015f96d6378e0000
         * logo : http://101.132.143.37/file/child/2AE90EBB9DE943AD938D48BB5EB63427.png
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
        private int type;
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

        public int getType() {
            return type;
        }

        public void setType(int type) {
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
