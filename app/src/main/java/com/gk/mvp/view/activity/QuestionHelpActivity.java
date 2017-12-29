package com.gk.mvp.view.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.gk.R;
import com.gk.beans.HelpCenterBean;
import com.gk.mvp.view.custom.TopBarView;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by JDRY-SJM on 2017/12/29.
 */

public class QuestionHelpActivity extends SjmBaseActivity {
    @BindView(R.id.top_bar)
    TopBarView topBar;
    @BindView(R.id.lv_help)
    ListView lvHelp;

    private List<HelpCenterBean> list = new ArrayList<>();
    private String[] questions = new String[]{
            "云寻校的数据可靠吗？",
            "云寻校使用了哪些先进技术？",
            "云寻校的VIP智能设计和专家1V1有什么区别？"};
    private String[] answers = new String[]{
            "云寻校的所有基础数据来源于各个大学和考试院，数据真实可靠。",
            "云寻校使用当前大数据技术，人工智能等先进技术来实现志愿设计。",
            "答：VIP智能设计是根据考生的情况，结合大数据有人工智能算法进行的志愿设计；专家1V1是在人工智能的基础上增加了资深专家对人工智能设计的报告进行审核和修改。"};

    @Override
    public int getResouceId() {
        return R.layout.activity_question_help;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(topBar, "疑问解答", 0);
        initData();
    }

    private void initData() {
        for (int i = 0; i < 3; i++) {
            HelpCenterBean helpCenterBean = new HelpCenterBean();
            helpCenterBean.setQuestion(questions[i]);
            helpCenterBean.setAnswer(answers[i]);
            list.add(helpCenterBean);
        }
        lvHelp.setAdapter(new CommonAdapter<HelpCenterBean>(this, R.layout.help_center_item, list) {
            @Override
            protected void convert(ViewHolder viewHolder, HelpCenterBean item, int position) {
                viewHolder.setText(R.id.tv_question, item.getQuestion());
                viewHolder.setText(R.id.tv_answer, item.getAnswer());
            }
        });
    }
}
