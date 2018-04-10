package com.gk.mvp.view.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.gk.R;
import com.gk.beans.MajorBean;

import java.util.ArrayList;

/**
 * 二级菜单适配器
 * Created by Administrator on 2016/7/18.
 */
public class ProfessionalChildAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private ArrayList<MajorBean.DataBean.NodesBeanXX.NodesBeanX> mDatas;
    int mPosition;

    public ProfessionalChildAdapter(Context context, ArrayList<MajorBean.DataBean.NodesBeanXX.NodesBeanX> data, int pos) {
        this.mContext = context;
        this.mDatas = data;
        this.mPosition = pos;
    }

    @Override
    public int getGroupCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    @Override
    public int getChildrenCount(int childPosition) {
        return mDatas.get(childPosition).getNodes() != null
                ? mDatas.get(childPosition).getNodes().size() : 0;
    }

    @Override
    public Object getGroup(int parentPosition) {
        return mDatas.get(parentPosition);
    }

    @Override
    public Object getChild(int parentPosition, int childPosition) {
        return mDatas.get(parentPosition).getNodes().get(childPosition);
    }

    @Override
    public long getGroupId(int parentPosition) {
        return parentPosition;
    }

    @Override
    public long getChildId(int parentPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * 主菜单布局
     *
     * @param parentPosition
     * @param isExpandabled  是否展开
     * @param view
     * @param viewGroup
     * @return
     */
    @Override
    public View getGroupView(int parentPosition, boolean isExpandabled, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.child_adapter, null);
            holder = new ViewHolder();
            holder.mChildGroupTV = view.findViewById(R.id.childGroupTV);
            holder.mTvScore = view.findViewById(R.id.score_value);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        MajorBean.DataBean.NodesBeanXX.NodesBeanX nodesBeanX = mDatas.get(parentPosition);
        if (nodesBeanX == null) {
            return view;
        }
        holder.mChildGroupTV.setText(nodesBeanX.getName());
        holder.mTvScore.setText("专业代码：" + nodesBeanX.getCode());
        return view;
    }

    class ViewHolder {

        private TextView mChildGroupTV;
        private TextView mTvScore;
    }

    /**
     * 子菜单布局
     *
     * @param parentPosition
     * @param childPosition
     * @param isExpandabled
     * @param view
     * @param viewGroup
     * @return
     */
    @Override
    public View getChildView(int parentPosition, int childPosition, boolean isExpandabled, View view, ViewGroup viewGroup) {
        ChildHolder holder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(
                    R.layout.child_child, null);
            holder = new ChildHolder();
            holder.childChildTV = view.findViewById(R.id.childGroupTV);
            holder.score = view.findViewById(R.id.score_value);
            view.setTag(holder);
        } else {
            holder = (ChildHolder) view.getTag();
        }
        holder.childChildTV.setText(mDatas.get(parentPosition).getNodes().get(childPosition).getName());
        holder.score.setText("专业代码：" + mDatas.get(parentPosition).getNodes().get(childPosition).getCode());
        return view;
    }

    class ChildHolder {

        private TextView childChildTV;
        private TextView score;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

}
