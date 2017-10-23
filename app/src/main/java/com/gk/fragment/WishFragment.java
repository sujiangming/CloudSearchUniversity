package com.gk.fragment;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.gk.R;
import com.gk.activity.VIPActivity;
import com.gk.custom.RichText;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by JDRY-SJM on 2017/9/6.
 */

public class WishFragment extends SjmBaseFragment {

    @BindView(R.id.ll_user_score)
    LinearLayout llUserScore;
    @BindView(R.id.rich_wish)
    RichText richWish;

    @OnClick(R.id.rich_wish)
    public void onViewClicked() {
        openNewActivity(VIPActivity.class);
    }

    @Override
    public int getResourceId() {
        return R.layout.fragment_wish;
    }

    @Override
    protected void onCreateViewByMe(Bundle savedInstanceState) {

    }

}
