package com.example.adapter;

import android.content.Context;
import android.widget.TextView;

import com.example.common.adapter.CommonAdapter;
import com.example.common.adapter.CommonViewHolder;
import com.example.textview.R;

import java.util.List;

public class TextAdapter extends CommonAdapter<String> {
    public TextAdapter(List<String> datas, int layoutId, Context context) {
        super(datas, layoutId, context);
    }

    @Override
    public void convert(CommonViewHolder holder, String s, int position) {
        TextView textView = holder.getConvertView().findViewById(R.id.item_message);
        textView.setText(s);
    }
}
