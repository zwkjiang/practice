package com.example.common.testcode;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.common.R;

import java.util.HashMap;
import java.util.List;

public class TestAdapter<T extends List> extends BaseAdapter {

    private Context context;

    private List<Object> list;

    public  TestAdapter(Context context, List<Object> list) {
        this.context = context;
        this.list = list;
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
    }

    public void get(){


    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Object item = getItem(position);
        View v;
        ViewHolder viewHolder;
        if (convertView == null) {
            v = LayoutInflater.from(context).inflate(R.layout.test_layout,null);
            viewHolder = new ViewHolder();
            viewHolder.ii = v.findViewById(R.id.iv);
            viewHolder.ss = v.findViewById(R.id.tv);
            v.setTag(viewHolder);
        } else {
            v = convertView;
            viewHolder = (ViewHolder) v.getTag();
        }
        return v;
    }

    class ViewHolder {
        TextView ss;
        ImageView ii;
    }
}
