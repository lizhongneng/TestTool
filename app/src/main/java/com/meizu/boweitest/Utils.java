package com.meizu.boweitest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.content.Context;
import android.util.Log;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by lizhongneng on 2018/5/23.
 */

public class Utils  {
    private static final String TAG = "Utils";

//        mListAppInfo =Utils.queryAppInfo(this);

    /**
     * 查询APP信息
     * @param context
     * @return   result :List数组
     */
    public static List<InstalledAppInfo> queryAppInfo(Context context) {
        PackageManager pm = context.getApplicationContext().getPackageManager(); // 获得PackageManager对象
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        // 通过查询，获得所有ResolveInfo对象.
        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(mainIntent, PackageManager.MATCH_ALL);

        List<InstalledAppInfo> result = new ArrayList<>();

        Collections.sort(resolveInfos, new ResolveInfo.DisplayNameComparator(pm));

        result.clear();
        for (ResolveInfo reInfo : resolveInfos) {
            String activityName = reInfo.activityInfo.name; // 获得该应用程序的启动Activity的name
            String pkgName = reInfo.activityInfo.packageName; // 获得应用程序的包名
            String appLabel = (String) reInfo.loadLabel(pm); // 获得应用程序的Label
            Drawable icon = reInfo.loadIcon(pm); // 获得应用程序图标
            // 为应用程序的启动Activity 准备Intent
            Intent launchIntent = new Intent();
            launchIntent.setComponent(new ComponentName(pkgName,
                    activityName));
            // 创建一个AppInfo对象，并赋值
            InstalledAppInfo appInfo = new InstalledAppInfo();
            //appInfo.setAppLabel(appLabel);
            appInfo.setPkgName(pkgName);
            appInfo.setAppIcon(icon);
            appInfo.setIntent(launchIntent);

            result.add(appInfo); // 添加至列表中
        }
        mainIntent = null;
        pm = null;
        resolveInfos.clear();
        resolveInfos = null;
        return result;
    }


    /**
     * 拷贝Assets下apk至sdCard中
     * @param context
     * @param fileName  应用包名
     * @param path sdCard路径
     * @return   true：拷贝完成，false：拷贝失败
     */
    public static boolean copyApkFromAssets(Context context, String fileName, String path) {
        Log.i(TAG,"开始拷贝");
        boolean copyIsFinish = false;
        try {
            InputStream is = context.getAssets().open(fileName);
            File file = new File(path);
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            byte[] temp = new byte[1024];
            int i = 0;
            while ((i = is.read(temp)) > 0) {
                fos.write(temp, 0, i);
            }
            fos.close();
            is.close();
            copyIsFinish = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return copyIsFinish;
    }


    /**
     * 判断手机是否安装某个应用
     * @param context
     * @param appPackageName  应用包名
     * @return   true：安装，false：未安装
     */
    public static boolean isApplicationAvilible(Context context, String appPackageName) {
        PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.contains(appPackageName)) {
                    return true;
                }
            }
        }
        return false;
    }




    /**
     * 执行命令
     *
     * @param command         命令
     * @param isShowCommand   是否显示执行的命令
     * @param isNeedResultMsg 是否反馈执行的结果
     * @retrun CMD_Result
     */
    public static CMD_Result runCMD(String command, boolean isShowCommand,
                                    boolean isNeedResultMsg) {
        if (isShowCommand)
            Log.i(TAG, "runCMD:" + command);
        CMD_Result cmdRsult = null;
        int result;
        try {
            Process process = Runtime.getRuntime().exec(command);
            result = process.waitFor();
            if (isNeedResultMsg) {
                StringBuilder successMsg = new StringBuilder();
                StringBuilder errorMsg = new StringBuilder();
                BufferedReader successResult = new BufferedReader(
                        new InputStreamReader(process.getInputStream()));
                BufferedReader errorResult = new BufferedReader(
                        new InputStreamReader(process.getErrorStream()));
                String s;
                while ((s = successResult.readLine()) != null) {
                    successMsg.append(s);
                }
                while ((s = errorResult.readLine()) != null) {
                    errorMsg.append(s);
                }
                cmdRsult = new CMD_Result(result, errorMsg.toString(),
                        successMsg.toString());
            }
        } catch (Exception e) {
            Log.e(TAG, "run CMD:" + command + " failed");
            e.printStackTrace();
        }
        return cmdRsult;
    }

    public static class CMD_Result {
        public int resultCode;
        public String error;
        public String success;

        public CMD_Result(int resultCode, String error, String success) {
            this.resultCode = resultCode;
            this.error = error;
            this.success = success;
        }

    }




}
