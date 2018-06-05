package com.meizu.boweitest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;


import java.util.ArrayList;
import java.util.List;

import flyme.support.v7.app.AppCompatActivity;
import flyme.support.v7.widget.LinearLayoutManager;
import flyme.support.v7.widget.MzItemDecoration;
import flyme.support.v7.widget.MzRecyclerView;


/**
 * Created by meizu on 2018/5/31.
 */

public class AppListActivity extends AppCompatActivity {

    MzRecyclerView appInfoListView = null;
    List<AppInfo> appInfos = null;
    AppInfosAdapter infosAdapter = null;
    private int mDensity;
    flyme.support.v7.app.ActionBar mActionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_list);
        appInfoListView = (MzRecyclerView) findViewById(R.id.appinfo_list);

        // 获得actionbar
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true); // 设置显示左边的返回按钮
        mActionBar.setTitle("所有应用"); // 设置标题栏Title


        mDensity = (int) getResources().getDisplayMetrics().density;
        appInfos = getAppInfos();
        updateUI(appInfos);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:   //返回键的id
                this.finish();
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void updateUI(List<AppInfo> appInfos) {
        if (null != appInfos ) {
            infosAdapter = new AppInfosAdapter(getApplication(), appInfos);
            appInfoListView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
            appInfoListView.setAdapter(infosAdapter);
            //设置item 修饰器
            MzItemDecoration itemDecoration = new MzItemDecoration(this);
            itemDecoration.setDividerPadding(new MzItemDecoration.DividerPadding() {
                @Override
                public int[] getDividerPadding(int position) {
                    return new int[]{64 * mDensity, 24 * mDensity};
                }
            });
            appInfoListView.addItemDecoration(itemDecoration);



        }
    }


    public List<AppInfo> getAppInfos(){
        PackageManager pm = getApplication().getPackageManager();
        List<PackageInfo>  packgeInfos = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
        appInfos = new ArrayList<AppInfo>();
        /* 获取应用程序的名称，不是包名，而是清单文件中的labelname
            String str_name = packageInfo.applicationInfo.loadLabel(pm).toString();
            appInfo.setAppName(str_name);
         */
        for(PackageInfo packgeInfo : packgeInfos){
            String activityName = packgeInfo.applicationInfo.name; // 获得该应用程序的启动Activity的name
            String appName = packgeInfo.applicationInfo.loadLabel(pm).toString();// 获得该应用名
            String packageName = packgeInfo.packageName;// 获得该应用的包名
            String versionName = packgeInfo.versionName;//获取该应用的版本号
            Drawable drawable = packgeInfo.applicationInfo.loadIcon(pm);// 获得该应用的图标
            AppInfo appInfo = new AppInfo(appName, packageName,activityName,versionName,drawable);
            appInfos.add(appInfo);
        }
        return appInfos;
    }



}
