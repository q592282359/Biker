package com.leew.biker.ui.mine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.leew.biker.R;


/**
 * author:Leew
 * date:2018/10/18  10:16
 * vesion:1.0
 * description:
 */
public class ImgGridAdapter extends BaseAdapter {

    private Context context;
    private int[] heads;

    public ImgGridAdapter(Context context, int[] heads) {
        this.context = context;
        this.heads = heads;
    }

    @Override
    public int getCount() {
        return heads.length;
    }

    @Override
    public Object getItem(int position) {
        return heads[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView head = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_pop_img, parent, false);
            head = convertView.findViewById(R.id.item_pop_img);
            convertView.setTag(head);
        } else {
            head = (ImageView) convertView.getTag();
        }
        Glide.with(context).load(heads[position]).into(head);
        return convertView;
    }
}
