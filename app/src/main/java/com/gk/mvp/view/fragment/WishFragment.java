package com.gk.mvp.view.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gk.R;
import com.gk.beans.CommonBean;
import com.gk.beans.LoginBean;
import com.gk.beans.WishProvinceBean;
import com.gk.beans.WishSchoolBean;
import com.gk.global.YXXConstants;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.mvp.presenter.PresenterManager;
import com.gk.mvp.view.activity.HldInterestActivity;
import com.gk.mvp.view.activity.MBTIActivity;
import com.gk.mvp.view.activity.VIPActivity;
import com.gk.mvp.view.custom.CommonTipDialog;
import com.gk.mvp.view.custom.RichText;
import com.gk.mvp.view.custom.actionsheet.ActionSheet;
import com.gk.tools.YxxEncoderUtils;
import com.gk.tools.YxxUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by JDRY-SJM on 2017/9/6.
 */

public class WishFragment extends SjmBaseFragment implements View.OnLayoutChangeListener {

    @BindView(R.id.ll_user_score)
    LinearLayout llUserScore;
    @BindView(R.id.rich_wish)
    RichText richWish;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.tv_rank)
    TextView tvRank;
    @BindView(R.id.tv_wenli)
    TextView tvWenli;
    @BindView(R.id.tv_yixiang)
    TextView tvYixiang;
    @BindView(R.id.tv_province)
    TextView tvProvince;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.et_comment_common)
    EditText et_comment_common;
    @BindView(R.id.et_school_1)
    EditText et_school_1;
    @BindView(R.id.et_school_2)
    EditText et_school_2;
    @BindView(R.id.et_school_3)
    EditText et_school_3;
    @BindView(R.id.et_school_4)
    EditText et_school_4;
    @BindView(R.id.et_school_5)
    EditText et_school_5;

    @BindView(R.id.lv_province)
    LinearLayout listView;

    @BindView(R.id.common_info_edit)
    View commonInfoEdit;

    @BindView(R.id.university_edit)
    View universityEdit;

    @BindView(R.id.choose_province)
    View chooseProvince;

    @BindView(R.id.root_view)
    View rootView;

    private LoginBean loginBean;
    private DialogInterface mDialog;
    private int requestCode = 0;

    private int keyHeight = 0;//软件盘弹起后所占高度阀值
    private String etHitStr = "请输入内容";
    private String emptyStr = "";
    private JSONObject jsonObject = new JSONObject();
    private EditText[] editTexts;

    @Override
    public int getResourceId() {
        return R.layout.fragment_wish;
    }

    @Override
    protected void onCreateViewByMe(Bundle savedInstanceState) {
        editTexts = new EditText[]{et_comment_common, et_school_1, et_school_2, et_school_3, et_school_4, et_school_5};
        initData();
        getUserIntentSch();
        getUserIntentArea();
        initKeyBoardParameter();
        rootView.addOnLayoutChangeListener(this);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            initData();
        }
    }

    private void initData() {
        loginBean = LoginBean.getInstance();
        if (loginBean != null) {
            setViewData(tvScore, loginBean.getScore());
            setViewData(tvRank, loginBean.getRanking());
            setViewData(tvWenli, loginBean.getWlDesc());
            setViewData(tvYixiang, loginBean.getWishUniversity());
            String isTest = loginBean.getIsHeartTest();
            if (null != isTest && !"".equals(isTest) && "1".equals(isTest)) {
                setViewData(tvStatus, "已完成");
            }
            setTvYixiang(tvYixiang, loginBean.getWishUniversity());
            setTvYixiang(tvProvince, loginBean.getWishProvince());
        }
    }

    private void setTvYixiang(TextView tv, String yxUniv) {
        if (yxUniv != null && !"".equals(yxUniv)) {
            String[] strings = yxUniv.split(",");
            tv.setText("已选" + strings.length + "个");
        }
    }

    private void setViewData(TextView tv, String value) {
        if (value != null && !"".equals(value)) {
            tv.setText(value);
        }
    }

    @OnClick({
            R.id.ll_user_score,
            R.id.ll_rank,
            R.id.ll_wenli,
            R.id.ll_yixiang,
            R.id.ll_yixiang_provinces,
            R.id.ll_heart_test,
            R.id.rich_wish,
            R.id.rtv_zj,
            R.id.tv_cancel_common,
            R.id.tv_submit_common,
            R.id.tv_cancel_university,
            R.id.tv_submit_university,
            R.id.tv_cancel_province,
            R.id.tv_submit_province})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_user_score:
                if (TextUtils.isEmpty(tvScore.getText())) {
                    updateInfo(tvScore, 1);
                }
                break;
            case R.id.ll_rank:
                if (TextUtils.isEmpty(tvRank.getText())) {
                    updateInfo(tvRank, 2);
                }
                break;
            case R.id.ll_wenli:
                String value = tvWenli.getText().toString();
                if (TextUtils.isEmpty(value)) {
                    showWenLiDialog();
                }
                break;
            case R.id.ll_yixiang:
                if (TextUtils.isEmpty(tvYixiang.getText())) {
                    requestCode = 4;
                    showEditDialogUniversity();
                    YxxUtils.showSoftInputFromWindow(et_school_5);
                }
                break;
            case R.id.ll_yixiang_provinces:
                if (TextUtils.isEmpty(tvProvince.getText())) {
                    showMultiChoiceDialog();
                }
                break;
            case R.id.ll_heart_test:
                showXgTest();
                break;
            case R.id.tv_cancel_common:
                hideEditDialog();
                hideSoftKey();
                break;
            case R.id.tv_submit_common:
                invokeService(0, 0);
                break;
            case R.id.rich_wish:
            case R.id.rtv_zj:
                showUpgradeDialog();
                break;
            case R.id.tv_cancel_university:
                hideEditDialogUniversity();
                hideSoftKey();
                break;
            case R.id.tv_submit_university:
                List<String> universities = new ArrayList<>();
                String school1 = et_school_1.getText().toString();
                String school2 = et_school_2.getText().toString();
                String school3 = et_school_3.getText().toString();
                String school4 = et_school_4.getText().toString();
                String school5 = et_school_5.getText().toString();

                setSchoolName(universities, school1);
                setSchoolName(universities, school2);
                setSchoolName(universities, school3);
                setSchoolName(universities, school4);
                setSchoolName(universities, school5);

                if (universities.size() == 0) {
                    toast("请输入一个您中意的学校");
                    return;
                }
                StringBuilder schoolsValue = new StringBuilder();
                for (int i = 0; i < universities.size(); i++) {
                    if (i == (universities.size() - 1)) {
                        schoolsValue.append(universities.get(i));
                    } else {
                        schoolsValue.append(universities.get(i)).append(",");
                    }
                }
                updateWishUniversity(schoolsValue.toString());
                LoginBean.getInstance().setWishUniversity(schoolsValue.toString()).save();
                hideEditDialogUniversity();
                tvYixiang.setText("已选" + universities.size() + "个");
                hideSoftKey();
                break;
            case R.id.tv_cancel_province:
                hideEditDialogProvince();
                hideSoftKey();
                yourChoicesName.clear();
                break;
            case R.id.tv_submit_province:
                if (yourChoicesName.size() == 0) {
                    toast("您还没有选择");
                    return;
                }
                StringBuilder provinceStr = new StringBuilder();
                for (int i = 0; i < yourChoicesName.size(); i++) {
                    if (i == (yourChoicesName.size() - 1)) {
                        provinceStr.append(yourChoicesName.get(i));
                    } else {
                        provinceStr.append(yourChoicesName.get(i)).append(",");
                    }
                }
                updateUserIntentArea(provinceStr.toString());
                LoginBean.getInstance().setWishProvince(provinceStr.toString()).save();
                hideEditDialogProvince();
                tvProvince.setText("已选" + yourChoicesName.size() + "个");
                break;
        }
    }

    private void showXgTest() {
        if (null != loginBean.getIsHeartTest() && !"".equals(loginBean.getIsHeartTest()) && !"0".equals(loginBean.getIsHeartTest())) {
            toast("您已经测试过了");
            return;
        }
        new ActionSheet.Builder(getContext(), getChildFragmentManager())
                .setCancelButtonTitle("取消")
                .setOtherButtonTitles("MBTI职业测试", "霍兰德性格测试")
                .setCancelableOnTouchOutside(true)
                .setListener(new ActionSheet.ActionSheetListener() {
                    @Override
                    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

                    }

                    @Override
                    public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                        switch (index) {
                            case 0:
                                openNewActivity(MBTIActivity.class);
                                break;
                            case 1:
                                openNewActivity(HldInterestActivity.class);
                                break;
                            default:
                                break;
                        }
                    }
                }).show();
    }

    private void setSchoolName(List<String> list, String str) {
        if (!TextUtils.isEmpty(str)) {
            list.add(str);
        }
    }

    private void updateInfo(TextView textView, int key) {
        if (TextUtils.isEmpty(textView.getText())) {
            requestCode = key;
            if (1 == key) { //修改分数
                showUpdateScoreDialog();
                return;
            }
            if (2 == key) { //修改排名
                et_comment_common.setInputType(InputType.TYPE_CLASS_NUMBER);
            } else {
                et_comment_common.setInputType(InputType.TYPE_CLASS_TEXT);
            }
            showCommonEditDialog(key);

        }
    }

    private void showUpdateScoreDialog() {
        final CommonTipDialog commonTipDialog = new CommonTipDialog(getContext());
        commonTipDialog.setTitle("温馨提示");
        commonTipDialog.setYesOnclickListener("确定", new CommonTipDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                String reScore = commonTipDialog.getMessageTv().getText().toString();
                if (TextUtils.isEmpty(reScore)) {
                    toast("请输入您的分数");
                    return;
                }
                et_comment_common.setText(reScore);
                invokeService(0, 0);
                commonTipDialog.dismiss();
            }
        });
        commonTipDialog.setNoOnclickListener("取消", new CommonTipDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                commonTipDialog.dismiss();
            }
        });
        CommonTipDialog selfDialog = commonTipDialog;
        commonTipDialog.show();
    }

    private void showCommonEditDialog(int key) {
        if (commonInfoEdit.getVisibility() == View.GONE) {
            commonInfoEdit.setVisibility(View.VISIBLE);
            YxxUtils.showSoftInputFromWindow(et_comment_common);
        }
    }

    private void hideEditDialog() {
        if (commonInfoEdit.getVisibility() == View.VISIBLE) {
            commonInfoEdit.setVisibility(View.GONE);
            YxxUtils.hideSoftInputKeyboard(et_comment_common);
        }
    }

    private void showEditDialogUniversity() {
        if (universityEdit.getVisibility() == View.GONE) {
            universityEdit.setVisibility(View.VISIBLE);
        }
    }

    private void hideEditDialogUniversity() {
        if (universityEdit.getVisibility() == View.VISIBLE) {
            universityEdit.setVisibility(View.GONE);
        }
    }

    private void showEditDialogProvince() {
        if (chooseProvince.getVisibility() == View.GONE) {
            chooseProvince.setVisibility(View.VISIBLE);
        }
    }

    private void hideEditDialogProvince() {
        if (chooseProvince.getVisibility() == View.VISIBLE) {
            chooseProvince.setVisibility(View.GONE);
        }
    }

    List<String> yourChoicesName = new ArrayList<>();
    public static final String[] PROVINCES = {"不限", "北京", "上海", "天津", "重庆", "河北", "山西", "内蒙古", "辽宁", "吉林", "黑龙江", "江苏", "浙江", "安徽", "福建", "江西", "山东", "河南", "湖北", "湖南", "广东", "广西", "海南", "四川", "贵州", "云南", "西藏", "陕西", "甘肃", "青海", "宁夏", "新疆", "台湾", "香港", "澳门"};

    private void showMultiChoiceDialog() {
        showEditDialogProvince();
        for (int i = 0; i < PROVINCES.length; ++i) {
            View view = View.inflate(getContext(), R.layout.province_item, null);
            TextView textView = view.findViewById(R.id.tv_province_name);
            textView.setText(PROVINCES[i]);
            final ImageView checkBox = view.findViewById(R.id.check_box);
            final int finalI = i;
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String tag = (String) view.getTag();
                    if ("0".equals(tag)) {
                        if (yourChoicesName.size() >= 5) {
                            toast("您最多只能选择五个意向省份");
                            return;
                        }
                        checkBox.setImageResource(R.drawable.gouxuan);
                        yourChoicesName.add(PROVINCES[finalI]);
                        view.setTag("1");
                    } else {
                        checkBox.setImageResource(R.drawable.not_gouxuan);
                        yourChoicesName.remove(PROVINCES[finalI]);
                        view.setTag("0");
                    }
                }
            });
            listView.addView(view);
        }
    }

    private void showUpgradeDialog() {
        int vip = loginBean.getVipLevel();
        if (vip <= 1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setIcon(android.R.drawable.ic_dialog_info);
            builder.setTitle("温馨提示");
            builder.setMessage("VIP会员才能使用，您想成为VIP会员吗？");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    openNewActivity(VIPActivity.class);
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

        } else {
            openNewActivity(VIPActivity.class);
        }
    }


    private void showWenLiDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("文理科选择");
        //定义单选的选项
        final String[] PROVINCES = new String[]{"文科", "理科"};
        //arg1：表示默认选中哪一项，-1表示没有默认选中项
        builder.setSingleChoiceItems(PROVINCES, -1, new DialogInterface.OnClickListener() {
            //点击任何一个单选选项都会触发这个侦听方法执行
            //arg1：点击的是哪一个选项
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 执行选中某个选项后的业务逻辑
                //点击某个选项后，触发onClick执行，要让对话框消失
                int value = which + 1;
                requestCode = 3;
                invokeService(1, value);
                mDialog = dialog;
                tvWenli.setText(PROVINCES[which]);
            }
        });
        builder.show();
    }

    /**
     * 初始化软键盘弹出和关闭时的参数
     */
    private void initKeyBoardParameter() {
        //获取屏幕高度
        int screenHeight = getActivity().getWindowManager().getDefaultDisplay().getHeight();
        //阀值设置为屏幕高度的1/3
        keyHeight = screenHeight / 3;
    }

    private void hideSoftKey() {
        for (EditText editText : editTexts) {
            //隐藏软盘
            YxxUtils.hideSoftInputKeyboard(editText);//需要修改
            //清空数据
            editText.setHint(etHitStr);
            editText.setText(emptyStr);
        }
    }

    private void clearAllEditView() {
        for (EditText editText : editTexts) {
            editText.clearFocus();
            //清空数据
            editText.setHint(etHitStr);
            editText.setText(emptyStr);
        }
    }

    private PresenterManager presenterManager = new PresenterManager().setmIView(this);

    /**
     * update user info
     *
     * @param value 文科 1 理科 2
     */
    private void invokeService(int flag, int value) {
        if (flag == 0 && TextUtils.isEmpty(et_comment_common.getText())) {
            toast(etHitStr);
            return;
        }
        showProgress();
        jsonObject.put("username", LoginBean.getInstance().getUsername());
        switch (requestCode) {
            case 1:
                jsonObject.put("score", et_comment_common.getText().toString());
                break;
            case 2:
                jsonObject.put("ranking", YxxUtils.URLEncode(et_comment_common.getText().toString()));
                break;
            case 3:
                jsonObject.put("subjectType", value);
                break;
        }
        presenterManager.setCall(RetrofitUtil.getInstance().createReq(IService.class).updateUserInfo(jsonObject.toJSONString()))
                .request(YXXConstants.INVOKE_API_DEFAULT_TIME);
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        CommonBean commonBean = (CommonBean) t;
        LoginBean loginBean = JSON.parseObject(commonBean.getData().toString(), LoginBean.class);
        switch (requestCode) {
            case 1:
                LoginBean.getInstance().setScore(loginBean.getScore()).save();
                tvScore.setText(loginBean.getScore());
                et_comment_common.setText("");
                hideEditDialog();
                break;
            case 2:
                LoginBean.getInstance().setRanking(loginBean.getRanking()).save();
                tvRank.setText(loginBean.getRanking());
                hideEditDialog();
                break;
            case 3:
                mDialog.dismiss();
                LoginBean.getInstance().setSubjectType(loginBean.getSubjectType()).save();
                tvWenli.setText(loginBean.getWlDesc());
                break;
        }
        hideProgress();
        hideSoftKey();
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        switch (requestCode) {
            case 1:
            case 2:
                hideEditDialog();
                break;
            case 3:
                mDialog.dismiss();
                break;
        }
        hideProgress();
        hideSoftKey();
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        //现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
        if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
            clearAllEditView();
            hideEditDialogUniversity();
        }
    }

    private static final String ERROR_MSG = "更改失败";

    private void updateWishUniversity(String intentSch) {
        showProgress();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", LoginBean.getInstance().getUsername());
        jsonObject.put("intentSch", YxxEncoderUtils.URLEncoder(intentSch));
        RetrofitUtil.getInstance().createReq(IService.class)
                .updateUserIntentSch(jsonObject.toJSONString())
                .enqueue(new Callback<CommonBean>() {
                    @Override
                    public void onResponse(@NonNull Call<CommonBean> call, @NonNull Response<CommonBean> response) {
                        if (response.isSuccessful()) {
                            CommonBean commonBean = response.body();
                            if (null == commonBean) {
                                toast(ERROR_MSG);
                                return;
                            }
                            toast(commonBean.getMessage());
                        } else {
                            toast(response.message());
                        }
                        hideProgress();
                    }

                    @Override
                    public void onFailure(@NonNull Call<CommonBean> call, @NonNull Throwable t) {
                        toast(ERROR_MSG);
                        hideProgress();
                    }
                });
    }

    private void updateUserIntentArea(String intentArea) {
        showProgress();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", LoginBean.getInstance().getUsername());
        jsonObject.put("intentArea", YxxEncoderUtils.URLEncoder(intentArea));
        RetrofitUtil.getInstance().createReq(IService.class)
                .updateUserIntentArea(jsonObject.toJSONString())
                .enqueue(new Callback<CommonBean>() {
                    @Override
                    public void onResponse(@NonNull Call<CommonBean> call, @NonNull Response<CommonBean> response) {
                        if (response.isSuccessful()) {
                            CommonBean commonBean = response.body();
                            if (null == commonBean) {
                                toast(ERROR_MSG);
                                return;
                            }
                            toast(commonBean.getMessage());
                        } else {
                            toast(response.message());
                        }
                        hideProgress();
                    }

                    @Override
                    public void onFailure(@NonNull Call<CommonBean> call, @NonNull Throwable t) {
                        toast(ERROR_MSG);
                        hideProgress();
                    }
                });
    }

    /**
     * 获取意向高校
     */
    private void getUserIntentSch() {
        showProgress();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", LoginBean.getInstance().getUsername());
        RetrofitUtil.getInstance()
                .createReq(IService.class)
                .getUserIntentSch(jsonObject.toJSONString())
                .enqueue(new Callback<CommonBean>() {
                    @Override
                    public void onResponse(@NonNull Call<CommonBean> call, @NonNull Response<CommonBean> response) {
                        if (response.isSuccessful()) {
                            CommonBean commonBean = response.body();
                            if (null == commonBean) {
                                return;
                            }
                            List<WishSchoolBean> wishSchoolBean = JSON.parseArray(commonBean.getData().toString(), WishSchoolBean.class);
                            if (wishSchoolBean != null && wishSchoolBean.size() > 0) {
                                tvYixiang.setText("已选" + wishSchoolBean.size() + "个");
                                for (int i = 0; i < wishSchoolBean.size(); i++) {
                                    EditText[] editTexts = {et_school_1, et_school_2, et_school_3, et_school_4, et_school_5};
                                    editTexts[i].setText(wishSchoolBean.get(i).getIntentSchool());
                                    editTexts[i].setEnabled(false);
                                    editTexts[i].clearFocus();
                                }
                            }
                        }
                        hideProgress();
                    }

                    @Override
                    public void onFailure(@NonNull Call<CommonBean> call, @NonNull Throwable t) {
                        hideProgress();
                    }
                });
    }

    /**
     * 获取意向省份
     */
    private void getUserIntentArea() {
        showProgress();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", LoginBean.getInstance().getUsername());
        RetrofitUtil.getInstance()
                .createReq(IService.class)
                .getUserIntentArea(jsonObject.toJSONString())
                .enqueue(new Callback<CommonBean>() {
                    @Override
                    public void onResponse(@NonNull Call<CommonBean> call, @NonNull Response<CommonBean> response) {
                        if (response.isSuccessful()) {
                            CommonBean commonBean = response.body();
                            if (null == commonBean) {
                                return;
                            }
                            List<WishProvinceBean> wishSchoolBean = JSON.parseArray(commonBean.getData().toString(), WishProvinceBean.class);
                            if (wishSchoolBean != null && wishSchoolBean.size() > 0) {
                                tvProvince.setText("已选" + wishSchoolBean.size() + "个");
                            }
                        }
                        hideProgress();
                    }

                    @Override
                    public void onFailure(@NonNull Call<CommonBean> call, @NonNull Throwable t) {
                        hideProgress();
                    }
                });
    }
}
