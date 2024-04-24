package com.example.custom.imagebitmap;

import android.content.Context;
import android.widget.ImageView;

import com.example.common.adapter.CommonAdapter;
import com.example.common.adapter.CommonViewHolder;
import com.example.common.ImageLoader;
import com.example.custom.R;

import java.util.List;

public class ImageAdapter extends CommonAdapter<String> {

    public ImageAdapter(List<String> datas, int layoutId, Context context) {
        super(datas, layoutId, context);
    }

    @Override
    public void convert(CommonViewHolder holder, String s, int position) {
        ImageView imageView = holder.getConvertView().findViewById(R.id.item_image_iv);
        ImageLoader.build(holder.getConvertView().getContext()).bindBitmap(s,imageView,imageView.getWidth(),imageView.getHeight());
    }
}
