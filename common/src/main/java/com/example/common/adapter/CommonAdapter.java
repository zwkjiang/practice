package com.example.common.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class CommonAdapter<T> extends BaseAdapter {

    private List<T> datas;

    private int layoutId;

    private Context context;

    public CommonAdapter(List<T> datas, int layoutId, Context context) {
        this.datas = datas;
        this.layoutId = layoutId;
        this.context = context;
    }

    public void setDatas(List<T> datas){
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public T getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommonViewHolder holder = CommonViewHolder.get(context, convertView, parent, layoutId, position);
        convert(holder,getItem(position),position);
        return holder.getConvertView();
    }

    public abstract void convert(CommonViewHolder holder,T t,int position);
}
