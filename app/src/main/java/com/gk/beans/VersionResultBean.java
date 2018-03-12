package com.gk.beans;

/**
 * Created by JDRY-SJM on 2018/3/12.
 */
public class VersionResultBean{

    /**
     * downUrl : http://localhost:18080/file/app_version/8CC7BDC7115B407AB2266DD3CA7AEEA8.txt
     * id : 4028819c6218eb2e01621903f4cf0001
     * publishTime : 1520838112000
     * version : V1.0.2
     * updateContent : xxxxx
     */

    private String id;
    private String downUrl;
    private long publishTime;
    private String version;
    private String updateContent;

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUpdateContent() {
        return updateContent;
    }

    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }
}
