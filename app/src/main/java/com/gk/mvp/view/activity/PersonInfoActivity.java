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
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baoyz.actionsheet.ActionSheet;
import com.gk.R;
import com.gk.beans.CommonBean;
import com.gk.beans.ImageBean;
import com.gk.beans.LoginBean;
import com.gk.global.YXXApplication;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.mvp.presenter.PresenterManager;
import com.gk.mvp.view.custom.CircleImageView;
import com.gk.mvp.view.custom.RichText;
import com.gk.mvp.view.custom.TopBarView;
import com.gk.tools.GlideImageLoader;

import java.io.File;
import java.io.IOException;
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

    private LoginBean loginBean = LoginBean.getInstance();
    private static final String CODE_KEY = "code";
    private GlideImageLoader glideImageLoader = new GlideImageLoader();
    private String defaultImage = "https://www.baidu.com/img/bdlogo.png";

    @Override
    public int getResouceId() {
        return R.layout.activity_person_info;
    }


    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(topBar, "个人信息", 0);
        glideImageLoader.displayImage(this, defaultImage, ivUserHead);
    }


    @OnClick({R.id.iv_user_head, R.id.tv_user_cname, R.id.tv_vip_level, R.id.tv_student_source, R.id.tv_student_score, R.id.tv_student_rank, R.id.tv_wen_li_ke})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.iv_user_head:
                //带配置
                //GalleryFinal.openGallerySingle(REQUEST_CODE_GALLERY, functionConfig, mOnHanlderResultCallback);
                showPhoto();
                break;
            case R.id.tv_vip_level:
                intent.setClass(this, VIPActivity.class);
                intent.putExtra(CODE_KEY, 1);
                startActivityForResult(intent, 1);
                break;
            case R.id.tv_user_cname:
                intent.setClass(this, UpdateUserInfoActivity.class);
                intent.putExtra(CODE_KEY, 2);
                startActivityForResult(intent, 2);
                break;
            case R.id.tv_student_source:
                intent.setClass(this, UpdateUserInfoActivity.class);
                intent.putExtra(CODE_KEY, 3);
                startActivityForResult(intent, 3);
                break;
            case R.id.tv_student_score:
                intent.setClass(this, UpdateUserInfoActivity.class);
                intent.putExtra(CODE_KEY, 4);
                startActivityForResult(intent, 4);
                break;
            case R.id.tv_student_rank:
                intent.setClass(this, UpdateUserInfoActivity.class);
                intent.putExtra(CODE_KEY, 5);
                startActivityForResult(intent, 5);
                break;
            case R.id.tv_wen_li_ke:
                intent.setClass(this, UpdateUserInfoActivity.class);
                intent.putExtra(CODE_KEY, 6);
                startActivityForResult(intent, 6);
                break;
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
            case 0:
                break;
            case 1:
                break;
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
        }
    }

    private final int REQUEST_CODE_CAMERA = 1000;
    private final int REQUEST_CODE_GALLERY = 1001;
    private final int REQUEST_CODE_CROP = 1002;
    private final int REQUEST_CODE_EDIT = 1003;

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
                        String imagePath = defaultImage;
                        switch (index) {
                            case 0:
                                GalleryFinal.openGallerySingle(REQUEST_CODE_GALLERY, functionConfig, mOnHanlderResultCallback);
                                break;
                            case 1:
                                permission();
                                break;
                            case 2:
                                GalleryFinal.openCrop(REQUEST_CODE_CROP, functionConfig, imagePath, mOnHanlderResultCallback);
                                break;
                            case 3:
                                GalleryFinal.openEdit(REQUEST_CODE_EDIT, functionConfig, imagePath, mOnHanlderResultCallback);
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
                Log.v("Upload", "success");
                try {
                    String ret = response.body().string();
                    ImageBean imageBean = JSON.parseObject(ret, ImageBean.class);
                    Log.e("ret", ret);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("username", LoginBean.getInstance().getUsername());
                    jsonObject.put("headImg", imageBean.getData().getPath());
                    PresenterManager.getInstance()
                            .setmContext(PersonInfoActivity.this)
                            .setmIView(PersonInfoActivity.this)
                            .setCall(RetrofitUtil.getInstance().createReq(IService.class).updateUserInfo(jsonObject.toJSONString()))
                            .request();
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

    @Override
    public <T> void fillWithData(T t, int order) {
        CommonBean commonBean = (CommonBean) t;
        LoginBean loginBean = JSON.parseObject(commonBean.getData().toString(), LoginBean.class);
        LoginBean.getInstance().setmContext(this).saveLoginBean(loginBean);
        hideProgress();
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        toast((String) t);
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
                    //用户拒绝了授权
                    toast("权限被拒绝");//"",Toast.LENGTH_SHORT).show();
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
