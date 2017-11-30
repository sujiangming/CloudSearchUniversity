package com.gk.mvp.view.adpater;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gk.R;
import com.gk.beans.UniversityAreaEnum;
import com.gk.beans.UniversityFeatureEnum;
import com.gk.beans.UniversityLevelEnum;
import com.gk.beans.UniversityTypeEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JDRY-SJM on 2017/11/29.
 */

public class GridViewChooseAdapter<T> extends JdryBaseAdapter {
    private List<Boolean> isCheck;
    private List<String> checkedArray;
    private int type;
    ViewHolder holder = null;

    public GridViewChooseAdapter(Context context, List<T> list1, int type) {
        super(context);
        this.mContext = context;
        checkedArray = new ArrayList<>();
        isCheck = new ArrayList<>();
        this.type = type;
        list.clear();
        list.addAll(list1);
        //给数组设置大小,并且全部赋值为false
        for (int i = 0; i < list.size(); i++) {
            isCheck.add(false);
        }
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<String> getCheckedArray() {
        return checkedArray;
    }

    public void setCheckedArray(List<String> checkedArray) {
        this.checkedArray = checkedArray;
    }

    public List<Boolean> getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(List<Boolean> isCheck) {
        this.isCheck = isCheck;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.university_choose_item, null);
            holder.textView = convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        switch (type) {
            case 1:
                UniversityAreaEnum universityAreaEnum = (UniversityAreaEnum) list.get(position);
                holder.textView.setText(universityAreaEnum.getName());
                holder.textView.setHint(String.valueOf(universityAreaEnum.getIndex()));
                break;
            case 2:
                UniversityTypeEnum universityTypeEnum = (UniversityTypeEnum) list.get(position);
                holder.textView.setText(universityTypeEnum.getName());
                holder.textView.setHint(String.valueOf(universityTypeEnum.getIndex()));
                break;
            case 3:
                UniversityFeatureEnum universityFeatureEnum = (UniversityFeatureEnum) list.get(position);
                holder.textView.setText(universityFeatureEnum.getName());
                holder.textView.setHint(String.valueOf(universityFeatureEnum.getIndex()));
                break;
            case 4:
                UniversityLevelEnum universityLevelEnum = (UniversityLevelEnum) list.get(position);
                holder.textView.setText(universityLevelEnum.getName());
                holder.textView.setHint(String.valueOf(universityLevelEnum.getIndex()));
                break;

        }
        return convertView;
    }

    /**
     * 改变某一个选项的状态
     *
     * @param post
     */
    public void choiceState(int post, View view) {
        /**
         *  传递过来所点击的position,如果是本身已经是选中状态,就让他变成不是选中状态,
         *  如果本身不是选中状态,就让他变成选中状态
         */
        TextView textView = (TextView) view;
        isCheck.set(post, isCheck.get(post).booleanValue() == true ? false : true);
        if (isCheck.get(post)) {
            textView.setBackgroundResource(R.color.color555555);
            textView.setTextColor(0xFFFFFFFF);
            checkedArray.add(textView.getHint().toString());
        } else {
            textView.setBackgroundResource(R.color.transparent);
            textView.setTextColor(0xFF353535);
            checkedArray.remove(textView.getHint().toString());
        }
        for (String str : checkedArray) {
            Log.e("增加删除后:", str);
        }
        this.notifyDataSetChanged();
    }


    public static class ViewHolder {
        TextView textView;
    }
}