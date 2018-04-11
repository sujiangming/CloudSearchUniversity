package com.gk.mvp.view.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gk.R;
import com.gk.beans.CommonBean;
import com.gk.beans.MaterialItemBean;
import com.gk.beans.SubjectTypeBean;
import com.gk.beans.SubjectTypeBeanDao;
import com.gk.global.YXXApplication;
import com.gk.global.YXXConstants;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.http.download.DownloadApi;
import com.gk.http.download.DownloadProgressHandler;
import com.gk.http.download.ProgressHelper;
import com.gk.mvp.presenter.PresenterManager;
import com.gk.mvp.view.adpater.CommonAdapter;
import com.gk.mvp.view.adpater.ViewHolder;
import com.gk.tools.GlideImageLoader;
import com.gk.tools.JdryFileUtil;
import com.gk.tools.JdryTime;
import com.gk.tools.YxxUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by JDRY-SJM on 2017/11/30.
 */

public class MaterialQueryActivity extends SjmBaseActivity {
    @BindView(R.id.back_image)
    ImageView backImage;
    @BindView(R.id.searchview)
    SearchView searchView;
    @BindView(R.id.lv_zy_query)
    ListView lvZyQuery;
    @BindView(R.id.smart_layout)
    SmartRefreshLayout smartLayout;


    private int mPage = 0;
    private boolean isLoadMore = false;
    private JSONObject jsonObject = new JSONObject();
    private String searchKey;
    List<MaterialItemBean.DataBean> list = new ArrayList<>();
    private CommonAdapter<MaterialItemBean.DataBean> adapter;

    @OnClick(R.id.back_image)
    public void onViewClicked() {
        closeActivity(this);
    }

    @Override
    public int getResouceId() {
        return R.layout.activity_material_query;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        initSmartRefreshLayout(smartLayout, true);
        initAdapter();
        showSearch();
        setSearchViewText(searchView);
    }

    private void showSearch() {
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchKey = s;
                invoke(s);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0); // 输入法如果是显示状态，那么就隐藏输入法
                }
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return true;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                adapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    private void initAdapter() {
        adapter = new CommonAdapter<MaterialItemBean.DataBean>(this, list, R.layout.material_item) {
            @Override
            public void convert(ViewHolder viewHolder, MaterialItemBean.DataBean item) {
                setTextViewValues((TextView) viewHolder.getView(R.id.tv_live_title), item.getName());
                setTextViewValues((TextView) viewHolder.getView(R.id.tv_time_content), JdryTime.getFullTimeBySec(item.getUploadTime()));
                SubjectTypeBean subjectTypeBean = YXXApplication.getDaoSession().getSubjectTypeBeanDao().queryBuilder().where(SubjectTypeBeanDao.Properties.Index.eq(item.getCourse())).unique();
                if (null != subjectTypeBean) {
                    setTextViewValues((TextView) viewHolder.getView(R.id.tv_km_content), subjectTypeBean.getName());
                } else {
                    setTextViewValues((TextView) viewHolder.getView(R.id.tv_km_content), "");
                }
                switch (item.getType()) {
                    case 1:
                        viewHolder.setText(R.id.tv_type_content, "名师讲堂");
                        break;
                    case 2:
                        viewHolder.setText(R.id.tv_type_content, "历史真题");
                        break;
                    case 3:
                        viewHolder.setText(R.id.tv_type_content, "模拟试卷");
                        break;
                }
                GlideImageLoader.displayImage(MaterialQueryActivity.this, item.getLogo(), (ImageView) viewHolder.getView(R.id.iv_item));
            }
        };
        lvZyQuery.setAdapter(adapter);
        lvZyQuery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
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

    private void invoke(String name) {
        showProgress();
        jsonObject.put("page", mPage);
        jsonObject.put("name", YxxUtils.URLEncode(name));
        presenterManager.setmIView(this)
                .setCall(RetrofitUtil.getInstance()
                        .createReq(IService.class).getMaterialsByName(jsonObject.toJSONString()))
                .request();
    }

    @Override
    public void refresh() {
        mPage = 0;
        isLoadMore = false;
        invoke(searchKey);
    }

    @Override
    public void loadMore() {
        mPage++;
        isLoadMore = true;
        invoke(searchKey);
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        searchView.setQueryHint("请输入关键字");
        searchView.clearFocus();
        hideProgress();
        stopLayoutRefreshByTag(isLoadMore);
        CommonBean commonBean = (CommonBean) t;
        assert null != commonBean;
        assert null != commonBean.getData();
        List<MaterialItemBean.DataBean> beanList = JSON.parseArray(commonBean.getData().toString(), MaterialItemBean.DataBean.class);
        if (beanList == null || beanList.size() == 0) {
            toast("没有查到相关数据");
            return;
        }
        if (isLoadMore) {
            list.addAll(beanList);
        } else {
            list = beanList;
        }
        adapter.setItems(list);
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        toast(YXXConstants.ERROR_INFO);
        hideProgress();
        stopLayoutRefreshByTag(isLoadMore);
    }


    private void openOrDownloadFile(MaterialItemBean.DataBean dataBean) {
        File file = new File(Environment.getExternalStorageDirectory() + "/Download/", dataBean.getFileName());
        if (file.exists()) {
            JdryFileUtil.openFile(this, file);
        } else {
            retrofitDownload(dataBean);
        }
    }

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
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    File file = new File(Environment.getExternalStorageDirectory() + "/Download/", dataBean.getFileName());
                    try {
                        assert null != response.body();
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
                    JdryFileUtil.openFile(MaterialQueryActivity.this, file);
                } else {
                    toast(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                toast(t.getMessage());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != presenterManager && null != presenterManager.getCall()) {
            presenterManager.getCall().cancel();
        }
    }
}
