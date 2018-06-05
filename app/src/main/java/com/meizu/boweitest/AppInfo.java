package com.meizu.boweitest;

import android.graphics.drawable.Drawable;

/**
 * Created by meizu on 2018/5/30.
 */

public class AppInfo {

    String appName;
    String packageName;
    String activityName;
    String versionName;
    Drawable drawable;

    public AppInfo(){}

    public AppInfo(String appName){
        this.appName = appName;
    }

    public AppInfo(String appName, String packageName){
        this.appName = appName;
        this.packageName = packageName;
    }

    public AppInfo(String appName,String packageName, Drawable drawable){
        this.appName = appName;
        this.packageName = packageName;
        this.drawable = drawable;
    }

    public AppInfo(String appName,String packageName, String activityName,String versionName,Drawable drawable){
        this.appName = appName;
        this.packageName = packageName;
        this.drawable = drawable;
        this.activityName = activityName;
        this.versionName = versionName;

    }


    public String getAppName() {
        if(null == appName)
            return "";
        else
            return appName;
    }

    public String getPackageName() {
        if(null == packageName)
            return "";
        else
            return packageName;
    }

    public String getActivityName(){
        if(null == activityName)
            return "";
        else
            return activityName;
    }

    public String getVersionName(){
        if(null == versionName)
            return "";
        else
            return versionName;
    }


    public Drawable getDrawable() {

        return drawable;
    }

    public void setDrawable(Drawable drawable) {

        this.drawable = drawable;
    }



    public void setAppName(String appName) {
        this.appName = appName;
    }



    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

}
