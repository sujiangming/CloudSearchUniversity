package com.gk.mvp.view.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baoyz.actionsheet.ActionSheet;
import com.gk.R;
import com.gk.beans.CommonBean;
import com.gk.beans.ImageBean;
import com.gk.beans.LoginBean;
import com.gk.global.YXXApplication;
import com.gk.global.YXXConstants;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.mvp.presenter.PresenterManager;
import com.gk.mvp.view.custom.CircleImageView;
import com.gk.mvp.view.custom.RichText;
import com.gk.mvp.view.custom.TopBarView;
import com.gk.tools.GlideImageLoader;
import com.gk.tools.YxxUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by JDRY-SJM on 2017/10/31.
 */

public class PersonInfoActivity extends SjmBaseActivity {
    @BindView(R.id.top_bar)
    TopBarView topBar;
    @BindView(R.id.iv_user_head)
    CircleImageView ivUserHead;
    @BindView(R.id.tv_user_cname)
    RichText tvUserCname;
    @BindView(R.id.tv_vip_level)
    RichText tvVipLevel;
    @BindView(R.id.tv_student_source)
    RichText tvStudentSource;
    @BindView(R.id.tv_student_score)
    RichText tvStudentScore;
    @BindView(R.id.tv_student_rank)
    RichText tvStudentRank;
    @BindView(R.id.tv_wen_li_ke)
    RichText tvWenLiKe;
    @BindView(R.id.tv_user_nick_name)
    RichText tvUserNickName;

    private LoginBean loginBean = LoginBean.getInstance();
    private static final String CODE_KEY = "code";
    private GlideImageLoader glideImageLoader = new GlideImageLoader();
    private DialogInterface mDialog;

    @Override
    public int getResouceId() {
        return R.layout.activity_person_info;
    }


    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(topBar, "个人信息", 0);
        if (loginBean.getHeadImg() != null && !"".equals(loginBean.getHeadImg())) {
            glideImageLoader.displayImage(this, loginBean.getHeadImg(), ivUserHead);
        }
        setViewData(tvUserCname, loginBean.getCname());
        setViewData(tvUserNickName, loginBean.getNickName());
        setViewData(tvWenLiKe, loginBean.getWlDesc());
        setViewData(tvStudentRank, loginBean.getRanking());
        setViewData(tvStudentScore, loginBean.getScore());
        setViewData(tvStudentSource, loginBean.getAddress());
        setViewData(tvVipLevel, loginBean.getVipLevelDesc());

    }

    private void setViewData(TextView tv, String value) {
        if (value != null && !"".equals(value)) {
            tv.setText(value);
        }
    }


    @OnClick({R.id.iv_user_head, R.id.tv_user_cname,
            R.id.tv_user_nick_name, R.id.tv_vip_level,
            R.id.tv_student_source, R.id.tv_student_score,
            R.id.tv_student_rank, R.id.tv_wen_li_ke,
            R.id.tv_cancel_province, R.id.tv_submit_province})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.iv_user_head:
                showPhoto();
                break;
            case R.id.tv_vip_level:
                showVipDialog();
                break;
            case R.id.tv_user_cname:
                if (TextUtils.isEmpty(tvUserCname.getText())) {
                    intent.setClass(this, UpdateUserInfoActivity.class);
                    intent.putExtra(CODE_KEY, 2);
                    startActivityForResult(intent, 2);
                }
                break;
            case R.id.tv_student_source:
                if (TextUtils.isEmpty(tvStudentSource.getText())) {
                    showMultiChoiceDialog();
                }
                break;
            case R.id.tv_student_score:
                if (TextUtils.isEmpty(tvStudentScore.getText())) {
                    intent.setClass(this, UpdateUserInfoActivity.class);
                    intent.putExtra(CODE_KEY, 4);
                    startActivityForResult(intent, 4);
                }
                break;
            case R.id.tv_student_rank:
                if (TextUtils.isEmpty(tvStudentRank.getText())) {
                    intent.setClass(this, UpdateUserInfoActivity.class);
                    intent.putExtra(CODE_KEY, 5);
                    startActivityForResult(intent, 5);
                }
                break;
            case R.id.tv_wen_li_ke:
                if (TextUtils.isEmpty(tvWenLiKe.getText())) {
                    showDialog();
                }
                break;
            case R.id.tv_user_nick_name:
                intent.setClass(this, UpdateUserInfoActivity.class);
                intent.putExtra(CODE_KEY, 7);
                startActivityForResult(intent, 7);
                break;
            case R.id.tv_cancel_province:
                hideEditDialogProvince();
                yourChoicesName.clear();
                break;
            case R.id.tv_submit_province:
                invokeService(2, 0, yourChoicesName.get(0));
                break;
        }
    }

    @BindView(R.id.choose_province)
    View chooseProvince;

    @BindView(R.id.lv_province)
    LinearLayout listView;

    List<String> yourChoicesName = new ArrayList<>();

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

    private void showMultiChoiceDialog() {
        showEditDialogProvince();
        final String[] items = YXXConstants.PROVINCES;
        for (int i = 0; i < items.length; ++i) {
            View view = View.inflate(this, R.layout.province_item, null);
            TextView textView = view.findViewById(R.id.tv_province_name);
            textView.setText(items[i]);
            final ImageView checkBox = view.findViewById(R.id.check_box);
            final int finalI = i;
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String tag = (String) view.getTag();
                    if ("0".equals(tag)) {
                        if (yourChoicesName.size() >= 1) {
                            toast("生源地只能是一个");
                            return;
                        }
                        checkBox.setImageResource(R.drawable.gouxuan);
                        yourChoicesName.add(items[finalI]);
                        view.setTag("1");
                    } else {
                        checkBox.setImageResource(R.drawable.not_gouxuan);
                        yourChoicesName.remove(items[finalI]);
                        view.setTag("0");
                    }
                }
            });
            listView.addView(view);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        String value = data.getStringExtra("info");
        if (value == null) {
            return;
        }
        switch (requestCode) {
            case 2:
                tvUserCname.setText(value);
                break;
            case 3:
                tvStudentSource.setText(value);
                break;
            case 4:
                tvStudentScore.setText(value);
                break;
            case 5:
                tvStudentRank.setText(value);
                break;
            case 6:
                tvWenLiKe.setText(value);
                break;
            case 7:
                tvUserNickName.setText(value);
                break;
        }
    }

    private final int REQUEST_CODE_CAMERA = 1000;
    private final int REQUEST_CODE_GALLERY = 1001;

    private FunctionConfig functionConfig = YXXApplication.getFunctionConfig();

    private void showPhoto() {
        new ActionSheet.Builder(this, getSupportFragmentManager())
                .setCancelButtonTitle("取消(Cancel)")
                .setOtherButtonTitles("打开相册(Open Gallery)", "拍照(Camera)")
                .setCancelableOnTouchOutside(true)
                .setListener(new ActionSheet.ActionSheetListener() {
                    @Override
                    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

                    }

                    @Override
                    public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                        switch (index) {
                            case 0:
                                GalleryFinal.openGallerySingle(REQUEST_CODE_GALLERY, functionConfig, mOnHanlderResultCallback);
                                break;
                            case 1:
                                permission();
                                break;
                            default:
                                break;
                        }
                    }
                }).show();
    }

    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList != null) {
                String imagePath = resultList.get(0).getPhotoPath();
                glideImageLoader.displayImage(PersonInfoActivity.this, imagePath, ivUserHead);
                showProgress();
                uploadFile(imagePath);
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            Toast.makeText(PersonInfoActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
        }
    };

    private void uploadFile(String path) {
        File file = new File(path);
        // 创建 RequestBody，用于封装构建RequestBody
        final RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        // MultipartBody.Part  和后端约定好Key，这里的partName是用image
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        // 添加描述
        String descriptionString = file.getName();
        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);
        // 执行请求
        Call<ResponseBody> call = RetrofitUtil.getInstance().createReq(IService.class).uploadImage(LoginBean.getInstance().getUsername(), description, body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful()) {
                        String ret = response.body().string();
                        ImageBean imageBean = JSON.parseObject(ret, ImageBean.class);
                        invokeService(0, 0, imageBean.getData().getPath());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
                hideProgress();
            }
        });
    }

    private void showVipDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle("温馨提示");
        builder.setMessage("您需要升级为金卡或者银卡会员吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setClass(PersonInfoActivity.this, VIPActivity.class);
                intent.putExtra(CODE_KEY, 1);
                startActivityForResult(intent, 1);
                closeActivity(PersonInfoActivity.this);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
                invokeService(1, value, null);
                mDialog = dialog;
                tvWenLiKe.setText(items[which]);
            }
        });
        builder.show();
    }

    /**
     * update user info
     *
     * @param flag      flag = 0 表示上传图片 flag = 1 更新文理科
     * @param value     文科 1 理科 2
     * @param imagePath 上传图片的路径
     */
    private void invokeService(int flag, int value, String imagePath) {
        showProgress();
        PresenterManager presenterManager = PresenterManager.getInstance()
                .setmContext(this)
                .setmIView(this);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", LoginBean.getInstance().getUsername());
        if (flag == 0) {
            jsonObject.put("headImg", imagePath);
            presenterManager.setCall(RetrofitUtil.getInstance().createReq(IService.class).updateUserInfo(jsonObject.toJSONString()))
                    .request(YXXConstants.INVOKE_API_DEFAULT_TIME);
        } else if (flag == 1) {
            jsonObject.put("subjectType", value);
            presenterManager.setCall(RetrofitUtil.getInstance().createReq(IService.class).updateUserInfo(jsonObject.toJSONString()))
                    .request(YXXConstants.INVOKE_API_SECOND_TIME);
        } else {
            jsonObject.put("address", YxxUtils.URLEncode(imagePath));
            presenterManager.setCall(RetrofitUtil.getInstance().createReq(IService.class).updateUserInfo(jsonObject.toJSONString()))
                    .request(YXXConstants.INVOKE_API_THREE_TIME);
        }
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        CommonBean commonBean = (CommonBean) t;
        LoginBean loginBean = JSON.parseObject(commonBean.getData().toString(), LoginBean.class);
        switch (order) {
            case YXXConstants.INVOKE_API_DEFAULT_TIME:
                LoginBean.getInstance()
                        .setHeadImg(loginBean.getHeadImg())
                        .save();
                break;
            case YXXConstants.INVOKE_API_SECOND_TIME:
                LoginBean.getInstance()
                        .setSubjectType(loginBean.getSubjectType())
                        .save();
                mDialog.dismiss();
                break;
            case YXXConstants.INVOKE_API_THREE_TIME:
                LoginBean.getInstance().setAddress(yourChoicesName.get(0)).save();
                tvStudentSource.setText(yourChoicesName.get(0));
                hideEditDialogProvince();
                break;
        }
        hideProgress();
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        toast((String) t);
        switch (order) {
            case YXXConstants.INVOKE_API_DEFAULT_TIME:
                break;
            case YXXConstants.INVOKE_API_SECOND_TIME:
                mDialog.dismiss();
                break;
        }
        hideProgress();
    }

    private static final int PERMISSION_REQUESTCODE = 1;

    private void permission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            boolean isGrand = ActivityCompat.shouldShowRequestPermissionRationale(PersonInfoActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
            if (!isGrand) {
                //当拒绝了授权后，为提升用户体验，可以以弹窗的方式引导用户到设置中去进行设置
                new AlertDialog.Builder(PersonInfoActivity.this)
                        .setMessage("请开启存储空间和相机权限")
                        .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //引导用户到设置中去进行设置
                                Intent intent = new Intent();
                                intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                                intent.setData(Uri.fromParts("package", getPackageName(), null));
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("取消", null)
                        .create()
                        .show();
            }
        } else {
            //已经授权
            openCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUESTCODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //用户点击了同意授权
                    openCamera();
                } else {
                    toast("权限被拒绝");
                }
                break;
            default:
                break;
        }
    }

    private void openCamera() {
        GalleryFinal.openCamera(REQUEST_CODE_CAMERA, functionConfig, mOnHanlderResultCallback);
    }
}
