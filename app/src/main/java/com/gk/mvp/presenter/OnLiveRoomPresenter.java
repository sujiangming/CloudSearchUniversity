package com.gk.mvp.presenter;

import com.alibaba.fastjson.JSON;
import com.gk.beans.CommonBean;
import com.gk.beans.OnLiveBean;
import com.gk.beans.OnLiveRoomInfo;
import com.gk.global.YXXConstants;
import com.gk.mvp.model.OnLiveRoomModel;
import com.gk.mvp.view.IView;

/**
 * Created by JDRY-SJM on 2017/12/22.
 */

public class OnLiveRoomPresenter<T> implements IPresenterCallback<T> {

    private OnLiveBean onLiveBean;
    private OnLiveRoomModel roomModel;
    private IView iView;
    public OnLiveRoomInfo onLiveRoomInfo;
    public OnLiveRoomInfo.FansSpeakBean fanSpeakBean;

    public OnLiveRoomPresenter(IView iView, OnLiveBean onLiveBean) {
        this.iView = iView;
        this.onLiveBean = onLiveBean;
        iView.showProgress();
        roomModel = new OnLiveRoomModel(this, onLiveBean);
    }

    public void fansExitLiveRooms() {
        roomModel.fansExitLiveRooms();
    }

    public void fansLiveRoomsSpeak(String fansSpeak) {
        roomModel.fansLiveRoomsSpeak(fansSpeak);
    }

    public void getLiveRoomsInfo() {
        roomModel.getLiveRoomsInfo();
    }

    @Override
    public void httpRequestSuccess(T o, int order) {
        CommonBean commonBean = (CommonBean) o;
        switch (order) {
            case YXXConstants.INVOKE_API_DEFAULT_TIME:
                break;
            case YXXConstants.INVOKE_API_SECOND_TIME:
                break;
            case YXXConstants.INVOKE_API_THREE_TIME:
                getOnLiveRoomFanSpeakSuccess(commonBean);
                break;
            case YXXConstants.INVOKE_API_FORTH_TIME:
                getOnLiveRoomInfoSuccess(commonBean);
                iView.hideProgress();
                break;
        }
    }

    @Override
    public void httpRequestFailure(T o, int order) {
        switch (order) {
            case YXXConstants.INVOKE_API_DEFAULT_TIME:
                break;
            case YXXConstants.INVOKE_API_SECOND_TIME:
                break;
            case YXXConstants.INVOKE_API_THREE_TIME:
                getOnLiveRoomFanSpeakFail((String) o);
                break;
            case YXXConstants.INVOKE_API_FORTH_TIME:
                getOnLiveRoomInfoFail((String) o);
                iView.hideProgress();
                break;
        }
    }

    private void getOnLiveRoomInfoSuccess(CommonBean commonBean) {
        if (commonBean.getData() == null) {
            return;
        }
        onLiveRoomInfo = JSON.parseObject(commonBean.getData().toString(), OnLiveRoomInfo.class);
        iView.fillWithData(onLiveRoomInfo, YXXConstants.INVOKE_API_FORTH_TIME);
    }

    private void getOnLiveRoomInfoFail(String string) {
        iView.fillWithNoData(string, YXXConstants.INVOKE_API_FORTH_TIME);
    }

    private void getOnLiveRoomFanSpeakSuccess(CommonBean commonBean) {
        if (commonBean.getData() == null) {
            return;
        }
        fanSpeakBean = JSON.parseObject(commonBean.getData().toString(), OnLiveRoomInfo.FansSpeakBean.class);
        iView.fillWithData(fanSpeakBean, YXXConstants.INVOKE_API_THREE_TIME);
    }

    private void getOnLiveRoomFanSpeakFail(String string) {
        iView.fillWithNoData(string, YXXConstants.INVOKE_API_THREE_TIME);
    }
}
