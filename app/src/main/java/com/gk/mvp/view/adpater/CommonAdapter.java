package com.gk.mvp.view.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class CommonAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    private int layoutId;

    /**
     * @param context
     * @param datas    数据
     * @param layoutId 布局
     */
    public CommonAdapter(Context context, List<T> datas, int layoutId) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = datas;
        this.layoutId = layoutId;
    }

    public void setItems(List<T> datas) {
        if (datas != null) {
            this.mDatas = datas;
        }
        this.notifyDataSetChanged();
    }

    public void clearItems() {
        this.mDatas.clear();
        this.notifyDataSetInvalidated();
    }

    public void removeItem(T data){
        this.mDatas.remove(data);
        this.notifyDataSetChanged();
    }

    public List<T> getAllData() {
        return mDatas;
    }

    @Override
    public int getCount() {
        return null != mDatas ? mDatas.size() : 0;
    }

    @Override
    public T getItem(int position) {
        return null != mDatas ? mDatas.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(mContext, convertView, parent,
                layoutId, position);
        convert(holder, getItem(position));
        return holder.getConvertView();
    }

    public abstract void convert(ViewHolder holder, T info);

}
