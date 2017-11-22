package com.gk.beans;

import java.util.List;

/**
 * Created by JDRY-SJM on 2017/11/21.
 */

public class MajorQueryBean {

    /**
     * data : [{"majorId":"402881fb5fce47d2015fce6030ca0001","majorName":"哲学","majorParentName":"哲学类"}]
     * message : 查询成功
     * status : 1
     */

    private String message;
    private int status;
    private List<DataBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * majorId : 402881fb5fce47d2015fce6030ca0001
         * majorName : 哲学
         * majorParentName : 哲学类
         */

        private String majorId;
        private String majorName;
        private String majorParentName;

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

        public String getMajorParentName() {
            return majorParentName;
        }

        public void setMajorParentName(String majorParentName) {
            this.majorParentName = majorParentName;
        }
    }
}
