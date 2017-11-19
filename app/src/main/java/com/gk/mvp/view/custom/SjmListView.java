package com.gk.mvp.view.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by JDRY-SJM on 2017/11/19.
 */

public class SjmListView extends ListView {
    public SjmListView(Context context) {
        super(context);
    }

    public SjmListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SjmListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
