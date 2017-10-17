package com.gk.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.gk.custom.SjmProgressBar;

import java.util.ArrayList;
import java.util.List;

public abstract class JdryBaseAdapter<T> extends BaseAdapter {
    protected List<T> list = new ArrayList<T>();
    protected Context mContext;
    protected LayoutInflater inflater;
    private SjmProgressBar jdryProgressBar;


    public JdryBaseAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return convertView;
    }

    //向list中添加数据
    public void refreshAdapter(List<T> arrayList) {
        list.addAll(arrayList);
        notifyDataSetChanged();
    }

    public void update(List<T> arrayList) {
        list.clear();
        list.addAll(arrayList);
        notifyDataSetChanged();
    }

    //清空list集合
    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    //移除指定位置的对象
    public void remove(int index) {
        list.remove(index);
        notifyDataSetChanged();
    }

    //移除对象
    public void remove(T t) {
        list.remove(t);
        notifyDataSetChanged();
    }

    public void showProgressBar() {
        if (null == jdryProgressBar) {
            jdryProgressBar = SjmProgressBar.show(mContext);
        } else {
            jdryProgressBar.show();
        }
    }

    public void dismissProgressBar() {
        if (null != jdryProgressBar && jdryProgressBar.isShowing()) {
            jdryProgressBar.dismiss();
        }
    }

    /**
     * 局部更新数据，调用一次getView()方法；Google推荐的做法
     *
     * @param listView 要更新的listview
     * @param position 要更新的位置
     */
    public void notifyDataSetChanged(ListView listView, int position) {
        /*第一个可见的位置**/
        int firstVisiblePosition = listView.getFirstVisiblePosition();
        /*最后一个可见的位置**/
        int lastVisiblePosition = listView.getLastVisiblePosition();

        /*在看见范围内才更新，不可见的滑动后自动会调用getView方法更新**/
        if (position >= firstVisiblePosition && position <= lastVisiblePosition) {
            /*获取指定位置view对象**/
            View view = listView.getChildAt(position - firstVisiblePosition);
            getView(position, view, listView);
        }
    }

}