package com.gk.mvp.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gk.R;
import com.gk.mvp.view.custom.CircleImageView;
import com.gk.mvp.view.custom.SjmListView;
import com.gk.mvp.view.custom.TopBarView;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by JDRY-SJM on 2017/11/20.
 */

public class QWDetailActivity extends SjmBaseActivity implements View.OnLayoutChangeListener {
    @BindView(R.id.top_bar)
    TopBarView topBar;
    @BindView(R.id.civ_header)
    CircleImageView civHeader;
    @BindView(R.id.tv_nick_name)
    TextView tvNickName;
    @BindView(R.id.tv_time_right)
    TextView tvTimeRight;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_care)
    TextView tvCare;
    @BindView(R.id.tv_care_count)
    TextView tvCareCount;
    @BindView(R.id.tv_scan_count)
    TextView tvScanCount;
    @BindView(R.id.tv_view_count)
    TextView tvViewCount;
    @BindView(R.id.lv_qw_jd)
    SjmListView lvQwJd;
    @BindView(R.id.btn_comment)
    Button btnComment;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.et_comment)
    EditText etComment;

    @BindView(R.id.include_comment)
    View includeComment;

    private int screenHeight = 0;//屏幕高度
    private int keyHeight = 0;//软件盘弹起后所占高度阀值
    private List<String> stringList = new ArrayList<>();

    @Override
    public int getResouceId() {
        return R.layout.activity_qw_detail;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(topBar, "问答详情", 0);
        initKeyBoardParameter();
        for (int i = 0; i < 20; i++) {
            stringList.add("权威解答-" + i);
        }
        lvQwJd.setAdapter(new CommonAdapter<String>(this, R.layout.qw_detail_item, stringList) {
            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {
                if (position == 0) {
                    viewHolder.setBackgroundColor(R.id.tv_top_line, 0x00000000);
                }
                viewHolder.setText(R.id.tv_nick_name, item);
            }
        });
    }

    /**
     * 初始化软键盘弹出和关闭时的参数
     */
    private void initKeyBoardParameter() {
        //获取屏幕高度
        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
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
        etComment.setHint("我来说两句");
        etComment.setText("");
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
            etComment.setHint("我来说两句");
            etComment.setText("");
        }
    }

    @OnClick({R.id.btn_comment, R.id.tv_cancel, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_comment:
                includeComment.setVisibility(View.VISIBLE);
                etComment.setFocusable(true);
                etComment.setFocusableInTouchMode(true);
                break;
            case R.id.tv_cancel:
                includeComment.setVisibility(View.GONE);
                hideSoftKey();
                break;
            case R.id.tv_submit:
                toast("解答成功");
                includeComment.setVisibility(View.GONE);
                hideSoftKey();
                break;
        }
    }
}
