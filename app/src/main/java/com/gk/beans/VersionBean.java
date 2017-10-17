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
    private int versionCode;

    @Generated(hash = 57683877)
    public VersionBean(Long id, int versionCode) {
        this.id = id;
        this.versionCode = versionCode;
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

    public int getVersionCode() {
        return this.versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }
}
