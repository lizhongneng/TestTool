package com.meizu.boweitest;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Toast;

import com.meizu.common.interpolator.PathInterpolatorCompat;


import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import flyme.support.v7.app.ActionBar;
import flyme.support.v7.app.AppCompatActivity;
import flyme.support.v7.widget.LinearLayoutManager;
import flyme.support.v7.widget.MzItemDecoration;
import flyme.support.v7.widget.MzRecyclerView;
import flyme.support.v7.widget.RecyclerFastScrollLetter;
import flyme.support.v7.widget.RecyclerView;


/**
 * Created by lizhongneng on 2018/5/23.
 */

public class FastMonkeyActivity extends AppCompatActivity implements MzItemDecoration.DividerPadding{
    MzRecyclerView appInfoRecyclerView = null;
    List<AppInfo> appInfos = null;
    AppInfosAdapter infosAdapter = null;
    ActionBar mActionBar;
    int oldState;
    private int mDensity;


    private static final String TAG = "FastMonkeyActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_list);
        appInfoRecyclerView = (MzRecyclerView) findViewById(R.id.appinfo_list);
        // 获得actionbar
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true); // 设置显示左边的返回按钮
        mActionBar.setTitle("点击应用进行Monkey"); // 设置标题栏Title


        mDensity = (int) getResources().getDisplayMetrics().density;
        appInfos = getAppInfos();
        updateUI(appInfos);




        MzItemDecoration decoration = new MzItemDecoration(this);
        decoration.setDividerPadding(this);
        appInfoRecyclerView.addItemDecoration(decoration);
        final RecyclerFastScrollLetter fastScroller = (RecyclerFastScrollLetter) findViewById(R.id.fastscroller);
        fastScroller.setRecyclerView(appInfoRecyclerView);
        fastScroller.setBackgroundColorSet(infosAdapter.getColorMap());
        final long duration = 250;
        final Interpolator interpolator = new PathInterpolatorCompat(0.2f, 0, 0.2f, 1);
        oldState = appInfoRecyclerView.getScrollState();

        appInfoRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            /*
           added by chengminghai 2017-3-15
           * */
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if(oldState == RecyclerView.SCROLL_STATE_IDLE && newState != RecyclerView.SCROLL_STATE_IDLE){
                    if (fastScroller.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL){
                        fastScroller.animate().translationX(-fastScroller.getWidth()).alpha(0).setInterpolator(interpolator).setDuration(duration).start();
                    }else {
                        fastScroller.animate().translationX(fastScroller.getWidth()).alpha(0).setInterpolator(interpolator).setDuration(duration).start();
                    }
                }else if(oldState !=RecyclerView.SCROLL_STATE_IDLE && newState == RecyclerView.SCROLL_STATE_IDLE){
                    fastScroller.animate().translationX(0).alpha(1).setDuration(duration).setInterpolator(interpolator).start();
                }
                oldState = newState;
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
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

    public void updateUI(final List<AppInfo> appInfos) {
        if (null != appInfos ) {
            infosAdapter = new AppInfosAdapter(getApplication(), appInfos);
            appInfoRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
            //将list按字母排序
            if (appInfos.size() > 1) {
                AppDisplayNameComparator rComparator = new AppDisplayNameComparator();
                Collections.sort(appInfos, rComparator);
                infosAdapter.updateFirstLetterPosition();
            }

            appInfoRecyclerView.setAdapter(infosAdapter);
            //设置点击事件
            infosAdapter.setOnItemClickListener(new AppInfosAdapter.MyOnItemClickListener() {
                @Override
                public void OnItemClickListener(View view, int position) {
                    WriteStringToFile(position);
                    new UiautomatorThread().start();
                    Toast.makeText(getApplicationContext(), appInfos.get(position).getAppName() + "：数据已清除", Toast.LENGTH_SHORT).show();
                }
            });

            //设置item 修饰器
            MzItemDecoration itemDecoration = new MzItemDecoration(this);
            itemDecoration.setDividerPadding(new MzItemDecoration.DividerPadding() {
                @Override
                public int[] getDividerPadding(int position) {
                    return new int[]{64 * mDensity, 24 * mDensity};
                }
            });
            appInfoRecyclerView.addItemDecoration(itemDecoration);

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


    @Override
    public int[] getDividerPadding(int i) {
        int[] padding = {
                48, 48
        };
        return padding;
    }




        /**
         * 运行uiautomator是个费时的操作，不应该放在主线程，因此另起一个线程运行
         */
        class UiautomatorThread extends Thread {
            @Override
            public void run() {
                super.run();
                String command=generateCommand("com.lzn.u2", "AutoMonkey", "test001");
                Utils.CMD_Result rs= Utils.runCMD(command,true,true);
                Log.e(TAG, "run: " + rs.error + "-------" + rs.success);
            }

            /**
             * 生成命令
             * @param pkgName 包名
             * @param clsName 类名
             * @param mtdName 方法名
             * @return
             */
            public  String generateCommand(String pkgName, String clsName, String mtdName) {
                String command = "am instrument -w -r   -e debug false -e class "
                        + pkgName + "." + clsName + "#" + mtdName + " "
                        + pkgName + ".test/com.meizu.u2.runner.BaseRunner";
                Log.e("test1: ", command);
                return command;
            }
        }


    public void WriteStringToFile(int position) {
        try {
            FileOutputStream fos = new FileOutputStream("/sdcard/pakname.txt");
            String s = appInfos.get(position).getPackageName();
            fos.write(s.getBytes());
            fos.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }








}










