package com.meizu.boweitest;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.blankj.utilcode.util.AppUtils;

import java.io.File;



/**
 * Created by lizhongneng on 2018/5/23.
 */
public class MainActivity extends AppCompatActivity {
    Context mContext;

//    private List<InstalledAppInfo> mListAppInfo = new ArrayList<>();

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final View mMonkey = (View) findViewById(R.id.one_layout);
        mMonkey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, FastMonkeyActivity.class);
                startActivity(intent);

                if (!Utils.isApplicationAvilible(MainActivity.this, "monkey")) {
                    Utils.copyApkFromAssets(MainActivity.this, "monkey.apk", Environment.getExternalStorageDirectory().getAbsolutePath() + "/monkey.apk");
                    install(Environment.getExternalStorageDirectory().getAbsolutePath()+"/monkey.apk");
//                    AppUtils.installAppSilent(Environment.getExternalStorageDirectory().getAbsolutePath()+"/monkey.apk");
//                    AppUtils.installAppSilent("/sdcard/monkey.apk");
                } else {
                    Toast.makeText(MainActivity.this, "已安装Monkey工具", Toast.LENGTH_SHORT).show();

                }

            }
        });





        final View mApplist = (View) findViewById(R.id.two_layout);
        mApplist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, AppListActivity.class);
                startActivity(intent);
            }


        });






    }



    public  void install(String filePath) {
        Log.i(TAG, "开始执行安装: " + filePath);
        File apkFile = new File(filePath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Log.w(TAG, "版本大于 N ，开始使用 fileProvider 进行安装");
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(mContext,"com.sta.zexuan.monkey.fileprovider",apkFile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            Log.w(TAG, "正常进行安装");
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        }
        startActivity(intent);
    }








}
