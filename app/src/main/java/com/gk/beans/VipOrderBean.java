package com.gk.beans;

/**
 * Created by JDRY-SJM on 2017/12/11.
 */

public class VipOrderBean {

    /**
     * orderAmount : 0.01
     * orderNo : VIP20171209000002
     * sign : alipay_sdk=alipay-sdk-java-dynamicVersl...
     */

    private double orderAmount;
    private String orderNo;
    private String sign;

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
