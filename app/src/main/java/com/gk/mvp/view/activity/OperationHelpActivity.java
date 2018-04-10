package com.gk.mvp.view.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.gk.R;
import com.gk.beans.HelpCenterBean;
import com.gk.mvp.view.adpater.CommonAdapter;
import com.gk.mvp.view.adpater.ViewHolder;
import com.gk.mvp.view.custom.TopBarView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by JDRY-SJM on 2017/12/29.
 */

public class OperationHelpActivity extends SjmBaseActivity {
    @BindView(R.id.top_bar)
    TopBarView topBar;
    @BindView(R.id.lv_help)
    ListView lvHelp;

    private List<HelpCenterBean> list = new ArrayList<>();
    private String[] questions = new String[]{"云寻校如何注册？", "云寻校有哪些登录方式？", "如何找回密码？", "用户信息如何修改？", "云寻校志愿设计的针对哪些考生？"};
    private String[] answers = new String[]{"云寻校使用手机号码注册，一个手机号只能注册一个账号。",
            "云寻校可以使用手机号+验证码登录，手机号+密码和微信授权登录。",
            "在登录界面点击忘记密码，经过验证用户以后，密码会发到您的手机上。",
            "云寻校的用户信息在APP上只能填写一次，不能进行二次修改，如果在填写时出现重大失误，请联系客服，人工进行修改。",
            "云寻校的志愿设计，针对高考当年的平行志愿录取考生，服务不适用于艺术／体育／提前批次考生。"};

    @Override
    public int getResouceId() {
        return R.layout.activity_operation_help;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(topBar, "使用说明", 0);
        initData();
    }

    private void initData() {
        for (int i = 0; i < 5; i++) {
            HelpCenterBean helpCenterBean = new HelpCenterBean();
            helpCenterBean.setQuestion(questions[i]);
            helpCenterBean.setAnswer(answers[i]);
            list.add(helpCenterBean);
        }
        lvHelp.setAdapter(new CommonAdapter<HelpCenterBean>(this, list, R.layout.help_center_item) {
            @Override
            public void convert(ViewHolder viewHolder, HelpCenterBean item) {
                viewHolder.setText(R.id.tv_question, item.getQuestion());
                viewHolder.setText(R.id.tv_answer, item.getAnswer());
            }
        });
    }
}
