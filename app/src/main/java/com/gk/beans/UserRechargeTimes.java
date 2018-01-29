package com.gk.beans;

/**
 * Created by JDRY-SJM on 2018/1/29.
 */

public class UserRechargeTimes {
    private int status;
    private String message;
    private Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        private String admissionRiskNum;
        private String heartTestNum;
        private String sameScoreToNum;

        public String getAdmissionRiskNum() {
            return admissionRiskNum;
        }

        public void setAdmissionRiskNum(String admissionRiskNum) {
            this.admissionRiskNum = admissionRiskNum;
        }

        public String getHeartTestNum() {
            return heartTestNum;
        }

        public void setHeartTestNum(String heartTestNum) {
            this.heartTestNum = heartTestNum;
        }

        public String getSameScoreToNum() {
            return sameScoreToNum;
        }

        public void setSameScoreToNum(String sameScoreToNum) {
            this.sameScoreToNum = sameScoreToNum;
        }
    }
}
