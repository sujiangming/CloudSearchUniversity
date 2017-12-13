package com.gk.beans;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by JDRY-SJM on 2017/11/29.
 */
@Entity
public class FileTable {
    @Id(autoincrement = true)
    private Long id;
    @NotNull
    @Unique
    private String docId;       //文档id
    private String docName;  //文档名称
    private String docSize;  //文档大小
    private long docUploadTime;  //上传时间
    private String docCreator;     //上传人
    private String docFilepath;    //文件路径
    private String docFormat;      //文件格式
    private String companyId;      //公司id
    private String localFilePath;  //文件存储的本地路径

    @Generated(hash = 732530104)
    public FileTable(Long id, @NotNull String docId, String docName, String docSize,
                     long docUploadTime, String docCreator, String docFilepath,
                     String docFormat, String companyId, String localFilePath) {
        this.id = id;
        this.docId = docId;
        this.docName = docName;
        this.docSize = docSize;
        this.docUploadTime = docUploadTime;
        this.docCreator = docCreator;
        this.docFilepath = docFilepath;
        this.docFormat = docFormat;
        this.companyId = companyId;
        this.localFilePath = localFilePath;
    }

    @Generated(hash = 1228698903)
    public FileTable() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocSize() {
        return docSize;
    }

    public void setDocSize(String docSize) {
        this.docSize = docSize;
    }

    public long getDocUploadTime() {
        return docUploadTime;
    }

    public void setDocUploadTime(long docUploadTime) {
        this.docUploadTime = docUploadTime;
    }

    public String getDocCreator() {
        return docCreator;
    }

    public void setDocCreator(String docCreator) {
        this.docCreator = docCreator;
    }

    public String getDocFilepath() {
        return docFilepath;
    }

    public void setDocFilepath(String docFilepath) {
        this.docFilepath = docFilepath;
    }

    public String getDocFormat() {
        return docFormat;
    }

    public void setDocFormat(String docFormat) {
        this.docFormat = docFormat;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getLocalFilePath() {
        return localFilePath;
    }

    public void setLocalFilePath(String localFilePath) {
        this.localFilePath = localFilePath;
    }
}
