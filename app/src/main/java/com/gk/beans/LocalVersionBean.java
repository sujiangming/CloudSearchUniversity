package com.gk.beans;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by JDRY-SJM on 2017/10/10.
 */
@Entity
public class LocalVersionBean {
    @Id(autoincrement = false)
    private Long id;
    @Unique
    private String versionName;
    @Generated(hash = 1944092114)
    public LocalVersionBean(Long id, String versionName) {
        this.id = id;
        this.versionName = versionName;
    }
    @Generated(hash = 892610013)
    public LocalVersionBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getVersionName() {
        return this.versionName;
    }
    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }
}
