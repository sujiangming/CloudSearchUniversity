package com.gk.custom;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import com.gk.R;


/**
 * Created by Administrator on 2016/9/9.
 */
public class SjmProgressBar extends ProgressDialog{
        public SjmProgressBar(Context context) {
            super(context);
        }
        public SjmProgressBar(Context context, int theme) {
            super(context,theme);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.layout_jdry_progress_dialog);

        }
        public static SjmProgressBar show (Context context) {
            SjmProgressBar jdryProgressBar = new SjmProgressBar(context, R.style.JdryProgressbar);
            jdryProgressBar.setCanceledOnTouchOutside(false);
            jdryProgressBar.show();
            return jdryProgressBar;
        }
}
