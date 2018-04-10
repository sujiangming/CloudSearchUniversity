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

public class VIPHelpActivity extends SjmBaseActivity {
    @BindView(R.id.top_bar)
    TopBarView topBar;
    @BindView(R.id.lv_help)
    ListView lvHelp;

    private List<HelpCenterBean> list = new ArrayList<>();
    private String[] questions = new String[]{
            "云寻校的VIP会员怎么开通？",
            "云寻校VIP会员服务包括哪些？",
            "云寻校VIP会员可以退费吗？",
            "云寻校VIP会员等级怎么付费？"};
    private String[] answers = new String[]{
            "请在云寻校APP页面的会员服务页面进行点击购买支付，支付成功以后，会员等级将会提升。",
            "VIP会员服务，包括进行志愿设计，心理测试，录取风险评估，同分去向等服务。",
            "云寻校VIP会员服务一经支付成功，一概不能降级，不能退费。请谨慎操作。",
            "根据云寻校会员等级价格，实行按价差付费，例如从银卡会员升级到金额会员，只需要补上差价即可。"};

    @Override
    public int getResouceId() {
        return R.layout.activity_vip_help;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(topBar, "VIP开通说明", 0);
        initData();
    }

    private void initData() {
        for (int i = 0; i < 4; i++) {
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
