package com.gk.global;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by wei on 2016/6/7.
 */
public class YXXConstants {
    public static final int LOGIN_SET_RESULT = 512;
    public static final String HOST = "http://101.132.143.37/cloudsch/";//"http://101.132.143.37/cloudsch/";//http://101.132.143.37/cloudsch/";
    public static final int INVOKE_API_DEFAULT_TIME = 1;
    public static final int INVOKE_API_SECOND_TIME = 2;
    public static final int INVOKE_API_THREE_TIME = 3;
    public static final int INVOKE_API_FORTH_TIME = 4;
    public static final int INVOKE_API_FIFTH_TIME = 5;
    //序列化标志
    public static final String LOGIN_INFO_SERIALIZE_KEY = "loginBean";
    public static final String ADS_INFO_SERIALIZE_KEY = "ads";
    public static final String MAJOR_JSON_SERIALIZE_KEY = "majorJson";
    public static final String ERROR_INFO = "请求失败";

    public static JSONObject jsonObject = new JSONObject();

    static {
        jsonObject.put("yuwen", "语文");
        jsonObject.put("shuxue", "数学");
        jsonObject.put("yingyu", "英语");
        jsonObject.put("lizong", "理综");
        jsonObject.put("wenzong", "文综");
        jsonObject.put("wuli", "物理");
        jsonObject.put("huaxue", "化学");
        jsonObject.put("lishi", "历史");
        jsonObject.put("dili", "地理");
        jsonObject.put("zhengzhi", "政治");
        jsonObject.put("shengwu", "生物");
    }

}
