package com.gk.beans;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by JDRY-SJM on 2017/10/10.
 */
@Entity
public class VersionBean {
    @Id(autoincrement = false)
    private Long id;
    @Unique
    private String versionCode;
    private String downUrl;
    private long publishTime;
    private String updateContent;
    @Generated(hash = 1556720901)
    public VersionBean(Long id, String versionCode, String downUrl,
            long publishTime, String updateContent) {
        this.id = id;
        this.versionCode = versionCode;
        this.downUrl = downUrl;
        this.publishTime = publishTime;
        this.updateContent = updateContent;
    }
    @Generated(hash = 673304504)
    public VersionBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getVersionCode() {
        return this.versionCode;
    }
    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }
    public String getDownUrl() {
        return this.downUrl;
    }
    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }
    public long getPublishTime() {
        return this.publishTime;
    }
    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }
    public String getUpdateContent() {
        return this.updateContent;
    }
    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }
}
