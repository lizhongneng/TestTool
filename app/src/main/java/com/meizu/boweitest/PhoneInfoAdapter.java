package com.meizu.boweitest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import flyme.support.v7.widget.MzRecyclerView;
import flyme.support.v7.widget.RecyclerView;

/**
 * Created by meizu on 2018/6/22.
 */

public class PhoneInfoAdapter extends ArrayAdapter {
    int resourceId;

    public PhoneInfoAdapter(Context context,int i ,List<PhoneInfo> info){
        super(context,i,info);

        resourceId = i;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PhoneInfo phoneInfo = (PhoneInfo) getItem(position); // 获取当前项的Fruit实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);//实例化一个对象
        TextView info = (TextView) view.findViewById(R.id.info);//获取该布局内的文本视图
        TextView name =(TextView) view.findViewById(R.id.title);
        name.setText(phoneInfo.getName());
        info.setText(phoneInfo.getInfo());//为文本视图设置文本内容
        return view;
    }



}






