package com.gk.beans;

/**
 * Created by JDRY-SJM on 2017/11/18.
 */

public class LectureTypeBean {

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
