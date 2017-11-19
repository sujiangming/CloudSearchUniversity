package com.gk.beans;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.gk.global.YXXConstants;
import com.gk.tools.JdryPersistence;

import java.io.Serializable;
import java.util.List;

/**
 * Created by JDRY-SJM on 2017/11/19.
 */
public class AdsBean implements Serializable {

    private List<MDataBean> mData;

    private static final long serialVersionUID = 2L;
    private volatile static AdsBean instance = null;//volatile关键字来保证其线程间的可见性
    private Context mContext;

    private AdsBean() {
    }

    public static AdsBean getInstance() {
        if (instance == null) {
            synchronized (AdsBean.class) {
                if (instance == null) {
                    instance = new AdsBean();
                }
            }
        }
        return instance;
    }

    public AdsBean setmContext(Context context) {
        this.mContext = context;
        return instance;
    }

    public void load() {  // 加载本地数据
        try {
            String objectStr = JdryPersistence.getObject(mContext, YXXConstants.ADS_INFO_SERIALIZE_KEY);
            if (null == objectStr || "".equals(objectStr)) {
                return;
            }
            List<MDataBean> mDataBeans = JSON.parseArray(objectStr, MDataBean.class);
            saveAdsBean(mDataBeans);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveAdsBean(List<MDataBean> mDataBeans) {
        setMData(mDataBeans);
        save();
        Log.e(AdsBean.class.getName(), JSON.toJSONString(this.getMData()));
    }

    public void save() {
        try {
            JdryPersistence.saveObject(mContext, JSON.toJSONString(this.getMData()), YXXConstants.ADS_INFO_SERIALIZE_KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<MDataBean> getMData() {
        return mData;
    }

    public void setMData(List<MDataBean> mData) {
        this.mData = mData;
    }

    public static class MDataBean implements Serializable {
        /**
         * id : 2c948a825fba8f33015fbaafb16d001e
         * name : 你的心理有问题吗？
         * type : 1
         * url : http://192.168.1.123:8080/file/ads/9C1269DC3D0E409A9743E4EBD1826592.png
         */

        private String id;
        private String name;
        private int type;
        private String url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
