package com.gk.beans;

/**
 * Created by JDRY-SJM on 2017/11/18.
 */

public class ImageBean {


    /**
     * data : {"path":"13648514015/9ABA4675EF92433DAF7C4E5032D48471.JPEG"}
     * message : 上传成功
     * status : 1
     */

    private DataBean data;
    private String message;
    private String status;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class DataBean {
        /**
         * path : 13648514015/9ABA4675EF92433DAF7C4E5032D48471.JPEG
         */

        private String path;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}
