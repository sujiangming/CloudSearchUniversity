package com.gk.mvp.view.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gk.R;
import com.gk.beans.CommonBean;
import com.gk.beans.LoginBean;
import com.gk.beans.UniversityAreaEnum;
import com.gk.global.YXXConstants;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.mvp.presenter.PresenterManager;
import com.gk.mvp.view.activity.VIPActivity;
import com.gk.mvp.view.custom.RichText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


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
    @BindView(R.id.et_content)
    View rlContent;
    @BindView(R.id.et_school_1)
    EditText etComment;
    @BindView(R.id.et_school_2)
    EditText etComment2;
    @BindView(R.id.et_school_3)
    EditText etComment3;
    @BindView(R.id.et_school_4)
    EditText etComment4;
    @BindView(R.id.et_school_5)
    EditText etComment5;

    @BindView(R.id.lv_province)
    LinearLayout listView;

    @BindView(R.id.choose_province)
    View chooseProvince;

    private LoginBean loginBean = LoginBean.getInstance();
    private DialogInterface mDialog;
    private int requestCode = 0;

    private int screenHeight = 0;//屏幕高度
    private int keyHeight = 0;//软件盘弹起后所占高度阀值
    private String etHitStr = "请输入内容";
    private String emptyStr = "";
    private JSONObject jsonObject = new JSONObject();
    private List<String> areaNameList;

    @Override
    public int getResourceId() {
        return R.layout.fragment_wish;
    }

    @Override
    protected void onCreateViewByMe(Bundle savedInstanceState) {
        initData();
        initKeyBoardParameter();
    }

    private void initData() {
        if (loginBean != null) {
            tvScore.setText(loginBean.getScore() == null ? emptyStr : loginBean.getScore());
            tvRank.setText(loginBean.getRanking() == null ? emptyStr : loginBean.getRanking());
            tvProvince.setText(loginBean.getAddress() == null ? emptyStr : loginBean.getAddress());
            tvWenli.setText(loginBean.getWlDesc());
            tvYixiang.setText(loginBean.getSchool() == null ? emptyStr : loginBean.getSchool());
        }
    }

    @OnClick({R.id.tv_score, R.id.tv_rank, R.id.tv_wenli, R.id.tv_yixiang, R.id.tv_province, R.id.tv_status, R.id.tv_cancel, R.id.tv_submit, R.id.rich_wish, R.id.rtv_zj
            , R.id.tv_cancel_province, R.id.tv_submit_province})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_score:
                updateInfo(view, 1);
                break;
            case R.id.tv_rank:
                updateInfo(view, 2);
                break;
            case R.id.tv_wenli:
                showDialog();
                break;
            case R.id.tv_yixiang:
                requestCode = 4;
                showEditDialog();
                break;
            case R.id.tv_province:
                showMultiChoiceDialog();
                break;
            case R.id.tv_status:
                break;
            case R.id.tv_cancel:
                hideEditDialog();
                hideSoftKey();
                break;
            case R.id.tv_submit:
                invokeService(0, 0);
                break;
            case R.id.rich_wish:
            case R.id.rtv_zj:
                openNewActivity(VIPActivity.class);
                break;
            case R.id.tv_cancel_province:
                hideEditDialogProvince();
                hideSoftKey();
                break;
            case R.id.tv_submit_province:
                Integer[] choiceArray = yourChoices.toArray(new Integer[yourChoices.size()]);
                LoginBean.getInstance().setWishProvince(choiceArray).save();
                hideEditDialogProvince();
                tvProvince.setText("已选" + yourChoices.size() + "个");
                break;
        }
    }

    private void updateInfo(View view, int key) {
        TextView textView = (TextView) view;
        if (TextUtils.isEmpty(textView.getText())) {
            showEditDialog();
            requestCode = key;
        }
    }

    private void showEditDialog() {
        if (rlContent.getVisibility() == View.GONE) {
            rlContent.setVisibility(View.VISIBLE);
        }
    }

    private void hideEditDialog() {
        if (rlContent.getVisibility() == View.VISIBLE) {
            rlContent.setVisibility(View.GONE);
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

    List<Integer> yourChoices = new ArrayList<>();

    private void showMultiChoiceDialog() {
        showEditDialogProvince();
        final String[] items = YXXConstants.PROVINCES;
        for (int i = 0; i < items.length; ++i) {
            View view = View.inflate(getContext(), R.layout.province_item, null);
            TextView textView = view.findViewById(R.id.tv_province_name);
            textView.setText(items[i]);
            final ImageView checkBox = view.findViewById(R.id.check_box);
            final int finalI = i;
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String tag = (String) view.getTag();
                    if ("0".equals(tag)) {
                        if (yourChoices.size() >= 5) {
                            toast("您最多只能选择五个意向省份");
                            return;
                        }
                        checkBox.setImageResource(R.drawable.gouxuan);
                        yourChoices.add(UniversityAreaEnum.getIndex(items[finalI]));
                        view.setTag("1");
                    } else {
                        checkBox.setImageResource(R.drawable.not_gouxuan);
                        yourChoices.remove(finalI);
                        view.setTag("0");
                    }
                }
            });
            listView.addView(view);
        }
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("文理科选择");
        //定义单选的选项
        final String[] items = new String[]{"文科", "理科"};
        //arg1：表示默认选中哪一项，-1表示没有默认选中项
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
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
                tvWenli.setText(items[which]);
            }
        });
        builder.show();
    }

    /**
     * 初始化软键盘弹出和关闭时的参数
     */
    private void initKeyBoardParameter() {
        //获取屏幕高度
        screenHeight = getActivity().getWindowManager().getDefaultDisplay().getHeight();
        //阀值设置为屏幕高度的1/3
        keyHeight = screenHeight / 3;
    }

    private void hideSoftKey() {
        //隐藏软盘
        InputMethodManager imm = (InputMethodManager) etComment.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etComment.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        //editText失去焦点
        etComment.clearFocus();
        //清空数据
        etComment.setHint(etHitStr);
        etComment.setText(emptyStr);
    }

    /**
     * update user info
     *
     * @param value 文科 1 理科 2
     */
    private void invokeService(int flag, int value) {
        if (flag == 0 && isNull()) {
            return;
        }
        String text = etComment.getText().toString();
        if (requestCode >= 4) {
            switch (requestCode) {
                case 4:
                    LoginBean.getInstance().setWishUniversity(text).save();
                    hideEditDialog();
                    hideSoftKey();
                    tvYixiang.setText(text);
                    break;
            }
            return;
        }
        showProgress();
        PresenterManager presenterManager = PresenterManager.getInstance()
                .setmContext(getContext())
                .setmIView(this);
        jsonObject.put("username", LoginBean.getInstance().getUsername());
        switch (requestCode) {
            case 1:
                jsonObject.put("score", etComment.getText().toString());
                break;
            case 2:
                jsonObject.put("ranking", etComment.getText().toString());
                break;
            case 3:
                jsonObject.put("subjectType", value);
                break;
        }
        presenterManager.setCall(RetrofitUtil.getInstance().createReq(IService.class).updateUserInfo(jsonObject.toJSONString()))
                .request(YXXConstants.INVOKE_API_DEFAULT_TIME);
    }

    private boolean isNull() {
        if (TextUtils.isEmpty(etComment.getText())) {
            toast(etHitStr);
            return true;
        }
        return false;
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        CommonBean commonBean = (CommonBean) t;
        LoginBean loginBean = JSON.parseObject(commonBean.getData().toString(), LoginBean.class);
        switch (requestCode) {
            case 1:
                LoginBean.getInstance().setScore(loginBean.getScore()).save();
                break;
            case 2:
                LoginBean.getInstance().setRanking(loginBean.getRanking()).save();
                break;
            case 3:
                LoginBean.getInstance().setSubjectType(loginBean.getSubjectType()).save();
                break;
        }
        mDialog.dismiss();
        hideProgress();
        hideSoftKey();
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        toast((String) t);
        mDialog.dismiss();
        hideProgress();
        hideSoftKey();
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
            etComment.setHint(etHitStr);
            etComment.setText(emptyStr);
        }
    }
}
