package com.meizu.boweitest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by meizu on 2018/5/30.
 */

public class AppInfosAdapter extends BaseAdapter {
    Context context;
    List<AppInfo> appInfos;

    public AppInfosAdapter(){}

    public AppInfosAdapter(Context context , List<AppInfo> infos ){
        this.context = context;
        this.appInfos = infos;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<AppInfo> getAppInfos() {
        return appInfos;
    }

    public void setAppInfos(List<AppInfo> appInfos) {
        this.appInfos = appInfos;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        int count = 0;
        if(null != appInfos){
            return appInfos.size();
        };
        return count;
    }

    @Override
    public Object getItem(int index) {
        // TODO Auto-generated method stub
        return appInfos.get(index);
    }

    @Override
    public long getItemId(int index) {
        // TODO Auto-generated method stub
        return index;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        // TODO Auto-generated method stub
        ViewHolder viewHolder = null;
        if(null == convertView){
            viewHolder = new ViewHolder();
            LayoutInflater mInflater = LayoutInflater.from(context);
            convertView = mInflater.inflate(R.layout.item_list, null);
            viewHolder.appIconImg = (ImageView)convertView.findViewById(R.id.image);
            viewHolder.appNameText = (TextView)convertView.findViewById(R.id.label);
            viewHolder.appPackageText = (TextView)convertView.findViewById(R.id.package_name);
            viewHolder.appActivityText = (TextView)convertView.findViewById(R.id.activity_name);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        if(null != appInfos){
            viewHolder.appIconImg.setBackground(appInfos.get(position).getDrawable());
            viewHolder.appNameText.setText(appInfos.get(position).getAppName());
            viewHolder.appPackageText.setText(appInfos.get(position).getPackageName());
            viewHolder.appActivityText.setText(appInfos.get(position).getActivityName());

        }

        return convertView;
    }

    private class ViewHolder{
        ImageView appIconImg;
        TextView  appNameText;
        TextView  appPackageText;
        TextView  appActivityText;
    }



}
