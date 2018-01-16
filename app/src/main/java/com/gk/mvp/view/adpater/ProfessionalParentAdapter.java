package com.gk.mvp.view.adpater;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.gk.R;
import com.gk.beans.MajorBean;
import com.gk.mvp.view.activity.ProfessionalDetailActivity;
import com.gk.tools.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/15.
 */
public class ProfessionalParentAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<MajorBean.DataBean.NodesBeanXX> mData;
    ViewHolder holder = null;

    public ProfessionalParentAdapter(Context context, List<MajorBean.DataBean.NodesBeanXX> data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public int getGroupCount() {
        return mData != null ? mData.size() : 0;
    }

    @Override
    public int getChildrenCount(int parentPosition) {
        return mData.get(parentPosition).getNodes() == null ? 0 : mData.get(parentPosition).getNodes().size();
    }

    @Override
    public Object getGroup(int parentPosition) {
        return mData.get(parentPosition);
    }

    @Override
    public MajorBean.DataBean.NodesBeanXX.NodesBeanX getChild(int parentPosition, int childPosition) {
        return mData.get(parentPosition).getNodes().get(childPosition);
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
     * 第一级菜单适配器布局
     *
     * @param parentPosition
     * @param isExpanded
     * @param convertView
     * @param viewGroup
     * @return
     */
    @Override
    public View getGroupView(int parentPosition, boolean isExpanded, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.parent_group_item, null);
            holder = new ViewHolder();
            holder.upImg = (ImageView) convertView.findViewById(R.id.kpi_back_img);
            holder.code = (TextView) convertView.findViewById(R.id.kpi_score);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //区分箭头往上还是
        if (isExpanded) {
            holder.upImg.setImageResource(R.drawable.dowm);
        } else {
            holder.upImg.setImageResource(R.drawable.up);
        }
        holder.title.setText(mData.get(parentPosition).getName());
        holder.code.setText("专业代码：" + mData.get(parentPosition).getCode());
        return convertView;
    }

    class ViewHolder {
        private TextView title;
        private TextView code;
        private ImageView upImg;
    }

    public ExpandableListView getExpandableListView() {
        ExpandableListView mExpandableListView = new ExpandableListView(
                mContext);
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, (int) mContext
                .getResources().getDimension(
                        R.dimen.space_50_dp));
        mExpandableListView.setLayoutParams(lp);
        mExpandableListView.setDividerHeight(0);// 取消group项的分割线
        mExpandableListView.setChildDivider(null);// 取消child项的分割线
        mExpandableListView.setGroupIndicator(null);// 取消展开折叠的指示图标
        return mExpandableListView;
    }

    /**
     * 第二级菜单式配
     *
     * @param parentPosition
     * @param childPosition
     * @param isExpanded
     * @param view
     * @param viewGroup
     * @return
     */
    @Override
    public View getChildView(final int parentPosition, final int childPosition, boolean isExpanded, View view, ViewGroup viewGroup) {
        final ExpandableListView childListView = getExpandableListView();
        //获取子菜单的数据
        final ArrayList<MajorBean.DataBean.NodesBeanXX.NodesBeanX> childData = new ArrayList<MajorBean.DataBean.NodesBeanXX.NodesBeanX>();
        final MajorBean.DataBean.NodesBeanXX.NodesBeanX secondNodesBeanX = getChild(parentPosition, childPosition);
        childData.add(secondNodesBeanX);
        if (childData.size() == 0) {
            return childListView;
        }
        ProfessionalChildAdapter adapter = new ProfessionalChildAdapter(mContext, childData, parentPosition);
        childListView.setAdapter(adapter);

        /**
         * 在这里对二级菜单的点击事件进行操作
         */
        childListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int position, long id) {
                Log.e("Xxx", "恭喜你,点击了" + parentPosition + "childpos>>>" + childPosition);
                List<MajorBean.DataBean.NodesBeanXX.NodesBeanX.NodesBean> nodesBeanXList = secondNodesBeanX.getNodes();
                if (nodesBeanXList == null || nodesBeanXList.size() == 0) {
                    ToastUtils.toast(mContext, "该专业类型没有更多子类型专业了");
                }
                return false;
            }
        });

        /**
         * 点击最小级菜单，调用该方法
         * */
        childListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView arg0, View arg1, int groupIndex, int childIndex, long arg4) {
                if (mListener != null) {
                    //mListener.onclick(parentPosition, childPosition, childIndex);
                    //点击三级菜单，跳转到编辑菜单界面
                    List<MajorBean.DataBean.NodesBeanXX.NodesBeanX.NodesBean> beans = childData.get(0).getNodes();
                    if (beans == null) {
                        ToastUtils.toast(mContext, "没有数据");
                        return false;
                    }
                    if (beans != null && beans.size() > 0) {
                        MajorBean.DataBean.NodesBeanXX.NodesBeanX.NodesBean nodesBean = beans.get(childIndex);
                        Intent intent = new Intent();
                        intent.setClass(mContext, ProfessionalDetailActivity.class);
                        intent.putExtra("id", nodesBean.getId());
                        mContext.startActivity(intent);
                    }
                }
                return false;
            }
        });
        /**
         *子ExpandableListView展开时，因为group只有一项，所以子ExpandableListView的总高度=
         * （子ExpandableListView的child数量 + 1 ）* 每一项的高度
         * */
        childListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                int mHeight = 0;
                List<MajorBean.DataBean.NodesBeanXX.NodesBeanX.NodesBean> nodesBeanXES = secondNodesBeanX.getNodes();
                if (nodesBeanXES == null || nodesBeanXES.size() == 0) {
                    mHeight = 1;
                } else {
                    mHeight = nodesBeanXES.size() + 1;
                }
                AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        (mHeight) * (int) mContext
                                .getResources().getDimension(R.dimen.space_50_dp));
                childListView.setLayoutParams(lp);
            }
        });
        /**
         *子ExpandableListView关闭时，此时只剩下group这一项，
         * 所以子ExpandableListView的总高度即为一项的高度
         * */
        childListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                Log.e("xxx", groupPosition + ">>onGroupCollapse");
                AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, (int) mContext
                        .getResources().getDimension(R.dimen.space_50_dp));
                childListView.setLayoutParams(lp);
                holder.upImg.setImageResource(R.drawable.up);
            }
        });
        return childListView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    /*接口回调*/
    public interface OnExpandClickListener {
        void onclick(int parentPosition, int childPosition, int childIndex);
    }

    OnExpandClickListener mListener;

    public void setOnChildListener(OnExpandClickListener listener) {
        this.mListener = listener;
    }
}
