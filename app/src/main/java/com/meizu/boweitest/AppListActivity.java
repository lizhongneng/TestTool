package com.meizu.boweitest;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by meizu on 2018/5/31.
 */

public class AppListActivity extends Activity {

    ListView appInfoListView = null;
    List<AppInfo> appInfos = null;
    AppInfosAdapter infosAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_list);
        appInfoListView = findViewById(R.id.appinfo_list);
        appInfos = getAppInfos();
        updateUI(appInfos);
    }

    public void updateUI(List<AppInfo> appInfos) {
        if (null != appInfos ) {
            infosAdapter = new AppInfosAdapter(getApplication(), appInfos);
            appInfoListView.setAdapter(infosAdapter);
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
            String appName = packgeInfo.applicationInfo.loadLabel(pm).toString();
            String packageName = packgeInfo.packageName;
            Drawable drawable = packgeInfo.applicationInfo.loadIcon(pm);
            AppInfo appInfo = new AppInfo(appName, packageName,activityName,drawable);
            appInfos.add(appInfo);
        }
        return appInfos;
    }



}
