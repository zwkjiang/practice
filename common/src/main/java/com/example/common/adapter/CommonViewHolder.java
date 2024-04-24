package com.example.common.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class CommonViewHolder {


    private final SparseArray<View> viewArray;

    private int position;

    private  final View convertView;

    public CommonViewHolder(Context context, ViewGroup viewGroup, int layoutId, int position) {
        viewArray = new SparseArray<>();
        this.position = position;
        convertView = LayoutInflater.from(context).inflate(layoutId, viewGroup, false);
        convertView.setTag(this);
    }

    public static CommonViewHolder get(Context context,View convertView ,ViewGroup viewGroup,int layoutId,int position) {
        if (convertView == null){
            return new CommonViewHolder(context,viewGroup,layoutId,position);
        } else {
            CommonViewHolder convertViewTag = (CommonViewHolder) convertView.getTag();
            convertViewTag.position = position;
            return convertViewTag;
        }
    }

    public View getConvertView(){
        return convertView;
    }


}
