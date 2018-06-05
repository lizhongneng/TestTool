package com.meizu.boweitest;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import flyme.support.v7.widget.RecyclerView;

/**
 * Created by meizu on 2018/5/30.
 */

public class AppInfosAdapter extends RecyclerView.Adapter<AppInfosAdapter.ViewHolder> {
    Context context;
    List<AppInfo> appInfos;

    public AppInfosAdapter(Context context , List<AppInfo> infos ){
        this.context = context;
        this.appInfos = infos;
        Log.d("lzn", appInfos.size() + "");
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView appIconImg;
        TextView  appNameText;
        TextView  appPackageText;
        TextView  appActivityText;
        TextView  appVersionText;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        // TODO Auto-generated method stub
        ViewHolder viewHolder;
        LayoutInflater mInflater = LayoutInflater.from(context);
        View View =  mInflater.inflate(R.layout.item_list,viewGroup, false);
        viewHolder = new ViewHolder(View);
        viewHolder.appIconImg = (ImageView)View.findViewById(R.id.image);
        viewHolder.appNameText = (TextView)View.findViewById(R.id.label);
        viewHolder.appPackageText = (TextView)View.findViewById(R.id.package_name);
//        viewHolder.appActivityText = (TextView)View.findViewById(R.id.activity_name);
        viewHolder.appVersionText = (TextView)View.findViewById(R.id.version_name);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.appIconImg.setBackground(appInfos.get(position).getDrawable());
        viewHolder.appNameText.setText(appInfos.get(position).getAppName());
        viewHolder.appPackageText.setText(appInfos.get(position).getPackageName());
//        viewHolder.appActivityText.setText(appInfos.get(position).getActivityName());
        viewHolder.appVersionText.setText(appInfos.get(position).getVersionName());
    }


    @Override
    public long getItemId(int index) {
        // TODO Auto-generated method stub
        return index;
    }

    @Override
    public int getItemCount() {
        int count = appInfos == null ? 0 : appInfos.size();
        return count;
    }


    //    @Override
//    public View getView(int position, View convertView, ViewGroup arg2) {
//        // TODO Auto-generated method stub
//        ViewHolder viewHolder = null;
//        if(null == convertView){
//            viewHolder = new ViewHolder();
//            LayoutInflater mInflater = LayoutInflater.from(context);
//
//            convertView = mInflater.inflate(R.layout.item_list, null);
//            viewHolder.appIconImg = (ImageView)convertView.findViewById(R.id.image);
//            viewHolder.appNameText = (TextView)convertView.findViewById(R.id.label);
//            viewHolder.appPackageText = (TextView)convertView.findViewById(R.id.package_name);
//            viewHolder.appActivityText = (TextView)convertView.findViewById(R.id.activity_name);
//
//            convertView.setTag(viewHolder);
//        }else{
//            viewHolder = (ViewHolder)convertView.getTag();
//        }
//        if(null != appInfos){
//            viewHolder.appIconImg.setBackground(appInfos.get(position).getDrawable());
//            viewHolder.appNameText.setText(appInfos.get(position).getAppName());
//            viewHolder.appPackageText.setText(appInfos.get(position).getPackageName());
//            viewHolder.appActivityText.setText(appInfos.get(position).getActivityName());
//
//        }
//
//        return convertView;
//    }



}
