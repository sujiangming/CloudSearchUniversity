package com.gk.beans;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * Created by JDRY-SJM on 2017/9/21.
 */

public class HomeItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public HomeItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        Log.d("-position-", "第" + position + "个");
        if (position >= 4 || position != 10) {
            outRect.top = space;
            outRect.bottom = space;
            if (position >= 4 && position < 10) {
                int odd = position % 2;
                if (odd == 0) {
                    outRect.right = space;
                }
            } else {
                int odd = position % 2;
                if (odd == 1) {
                    outRect.right = space;
                }
            }
        } else {
            outRect.top = 0;
            outRect.right = 0;
            outRect.bottom = 0;
        }
    }
}

