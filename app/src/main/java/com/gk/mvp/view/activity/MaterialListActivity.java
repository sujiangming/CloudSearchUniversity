package com.gk.mvp.view.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gk.R;
import com.gk.beans.CommonBean;
import com.gk.beans.MaterialItemBean;
import com.gk.global.YXXConstants;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.http.download.DownloadApi;
import com.gk.http.download.DownloadProgressHandler;
import com.gk.http.download.ProgressHelper;
import com.gk.mvp.presenter.PresenterManager;
import com.gk.mvp.view.adpater.MaterialListAdapter;
import com.gk.mvp.view.custom.TopBarView;
import com.gk.tools.GlideImageLoader;
import com.gk.tools.JdryFileUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by JDRY-SJM on 2017/11/26.
 */

public class MaterialListActivity extends SjmBaseActivity {
    @BindView(R.id.top_bar)
    TopBarView topBar;
    @BindView(R.id.lv_material)
    ListView lvMaterial;
    @BindView(R.id.smart_material)
    SmartRefreshLayout smartMaterial;

    List<MaterialItemBean.DataBean> list = new ArrayList<>();

    private int type;
    private int page = 0;
    private String course;
    private boolean isLoadMore = false;
    private JSONObject jsonObject = new JSONObject();
    private MaterialListAdapter adapter;

    @Override
    public int getResouceId() {
        return R.layout.activity_material_list;
    }


    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        initSmartRefreshLayout(smartMaterial, true);
        type = getIntent().getIntExtra("type", 0);
        course = getIntent().getStringExtra("course");
        setTitleByType();
        initAdapter();
        showProgress();
        invoke();

    }

    private void setTitleByType() {
        if (type == 1) {
            setTopBar(topBar, "名师讲堂", 0);
        } else if (type == 2) {
            setTopBar(topBar, "历年真题", 0);
        } else {
            setTopBar(topBar, "模拟试卷", 0);
        }
    }

    private void initAdapter() {
        adapter = new MaterialListAdapter(this, type);
        adapter.update(list);
        lvMaterial.setAdapter(adapter);
        lvMaterial.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (1 == list.get(i).getType()) {
                    Intent intent = new Intent();
                    intent.putExtra("bean", list.get(i));
                    openNewActivityByIntent(MsJtDetailActivity.class, intent);
                    return;
                }
                openOrDownloadFile(list.get(i));
            }
        });
    }

    private PresenterManager presenterManager = new PresenterManager();

    private void invoke() {
        jsonObject.put("page", page);
        jsonObject.put("type", type);
        jsonObject.put("course", course);
        presenterManager
                .setmIView(this)
                .setCall(RetrofitUtil.getInstance().createReq(IService.class).getMaterialsByType(jsonObject.toJSONString()))
                .request();
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        hideProgress();
        stopLayoutRefreshByTag(isLoadMore);
        CommonBean commonBean = (CommonBean) t;
        List<MaterialItemBean.DataBean> beanList = JSON.parseArray(commonBean.getData().toString(), MaterialItemBean.DataBean.class);
        if (isLoadMore) {
            if (beanList == null || beanList.size() == 0) {
                toast("已经扯到底啦");
                return;
            }
            list.addAll(beanList);
            adapter.update(list);
            return;
        }
        int currentSize = list.size();
        list.addAll(beanList);
        list = removeDuplicate(list);
        int afterSize = list.size();
        if (currentSize == afterSize) {
            return;
        }
        adapter.update(list);
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        toast(YXXConstants.ERROR_INFO);
        hideProgress();
        stopLayoutRefreshByTag(isLoadMore);
    }

    @Override
    public void refresh() {
        page = 0;
        isLoadMore = false;
        invoke();
    }

    @Override
    public void loadMore() {
        page++;
        isLoadMore = true;
        invoke();
    }

    public List<MaterialItemBean.DataBean> removeDuplicate(List<MaterialItemBean.DataBean> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).getId().equals(list.get(i).getId())) {
                    list.remove(j);
                }
            }
        }
        return list;
    }

    private String permissionInfo;
    private final int SDK_PERMISSION_REQUEST = 127;

    @TargetApi(26)
    private void getPersimmions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();
            /***
             * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
             */
            // 读写SD卡权限
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.CAMERA);
            }
            /*
             * 读写权限和电话状态权限非必要权限(建议授予)只会申请一次，用户同意或者禁止，只会弹一次
			 */
            // 读取电话状态权限
            if (addPermission(permissions, Manifest.permission.READ_PHONE_STATE)) {
                permissionInfo += "Manifest.permission.READ_PHONE_STATE Deny \n";
            }

            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            }
        }
    }

    @TargetApi(26)
    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) { // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
            if (shouldShowRequestPermissionRationale(permission)) {
                return true;
            } else {
                permissionsList.add(permission);
                return false;
            }
        } else {
            return true;
        }
    }

    @TargetApi(26)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void openOrDownloadFile(MaterialItemBean.DataBean dataBean) {
        File file = new File(Environment.getExternalStorageDirectory() + "/Download/", dataBean.getFileName());
        if (file.exists()) {
            openFile(file.getAbsolutePath());
        } else {
            retrofitDownload(dataBean);
        }
    }

    private String errorInfo = "文件下载失败";

    private void retrofitDownload(final MaterialItemBean.DataBean dataBean) {
        //监听下载进度
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setProgressNumberFormat("%1d KB/%2d KB");
        dialog.setTitle("下载");
        dialog.setMessage("正在下载，请稍后...");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setCancelable(false);
        dialog.show();

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(YXXConstants.HOST);//"http://msoftdl.360.cn"
        OkHttpClient.Builder builder = ProgressHelper.addProgress(null);
        DownloadApi retrofit = retrofitBuilder
                .client(builder.build())
                .build().create(DownloadApi.class);

        ProgressHelper.setProgressHandler(new DownloadProgressHandler() {
            @Override
            protected void onProgress(long bytesRead, long contentLength, boolean done) {
                Log.e("是否在主线程中运行", String.valueOf(Looper.getMainLooper() == Looper.myLooper()));
                Log.e("onProgress", String.format("%d%% done\n", (100 * bytesRead) / contentLength));
                Log.e("done", "--->" + String.valueOf(done));
                dialog.setMax((int) (contentLength / 1024));
                dialog.setProgress((int) (bytesRead / 1024));
                if (done) {
                    dialog.dismiss();
                    toast("下载成功！");
                }
            }
        });
        Call<ResponseBody> call = retrofit.retrofitDownload(dataBean.getFileUrl());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    File file = new File(Environment.getExternalStorageDirectory() + "/Download/", dataBean.getFileName());
                    String fileAbsolutePath = file.getAbsolutePath();
                    try {
                        InputStream is = response.body().byteStream();
                        FileOutputStream fos = new FileOutputStream(file);
                        BufferedInputStream bis = new BufferedInputStream(is);
                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = bis.read(buffer)) != -1) {
                            fos.write(buffer, 0, len);
                            fos.flush();
                        }
                        fos.close();
                        bis.close();
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    openFile(fileAbsolutePath);
                } else {
                    toast(errorInfo);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                toast(errorInfo);
            }
        });
    }

    private void openFile(String fileAbsolutePath) {
        Intent intent = JdryFileUtil.openFile(fileAbsolutePath);
        if (intent == null) {
            toast("该文件已损坏");
            return;
        }
        startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != presenterManager && null != presenterManager.getCall()) {
            presenterManager.getCall().cancel();
        }
        GlideImageLoader.stopLoad(getApplicationContext());
    }
}
