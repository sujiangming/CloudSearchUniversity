package com.gk.config;

/**
 * Created by robinyang on 15/8/4.
 */
public class Config {
    public static final boolean DEBUG = true;//http://120.76.208.247:8080/PMS/
    public static final String HOST = "http://lwgj.gzjdry.com:8080/PMS/";//"http://120.76.65.54:8080/PMS/";//"http://192.168.1.68:3000";

    public static final String LOGIN_CLS = "com.jdry.pms.basicInfo.view.ProManagerView";
    public static final String LOGIN_METHOD = "managerLogin";

    public static final String CONTACT_CLS = "com.jdry.pms.basicInfo.service.impl.PropertyManagerServiceImpl";
    public static final String CONTACT_METHOD = "getEmpContacts";

    public static final String FORUM_CLS = "com.jdry.pms.bbs.controller.BbsAppController";
    public static final String FORUM_METHOD = "loadTopicList";

    //序列化标志
    public static final String LOGIN_INFO_SERIALIZE_KEY = "loginBean";
    public static final String LOGIN_NAME = "userName";

}
