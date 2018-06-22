package com.meizu.boweitest;

import android.content.Intent;
import android.os.Environment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import flyme.support.v7.widget.LinearLayoutManager;
import flyme.support.v7.widget.MzRecyclerView;


/**
 * Created by lizhongneng on 2018/5/23.
 */
public class        MainActivity extends AppCompatActivity implements RecyclerViewAdapter.OnRecyclerViewListener {

    private static final String TAG = "MainActivity";
    LinearLayout mHomeParent;
    private MzRecyclerView mRecyclerView;
    private List<ViewInfo> mViewList;
    private List<ViewInfo> mData;
    private RecyclerViewAdapter mGridAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


//        final View mMonkey = (View) findViewById(R.id.one);
//        mMonkey.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setClass(MainActivity.this, FastMonkeyActivity.class);
//                startActivity(intent);
//
//                if (!Utils.isApplicationAvilible(MainActivity.this, "monkey")) {
//                    Utils.copyApkFromAssets(MainActivity.this, "monkey.apk", Environment.getExternalStorageDirectory().getAbsolutePath() + "/monkey.apk");
//                    Utils.install(Environment.getExternalStorageDirectory().getAbsolutePath()+"/monkey.apk");
////                    AppUtils.installAppSilent(Environment.getExternalStorageDirectory().getAbsolutePath()+"/monkey.apk");
////                    AppUtils.installAppSilent("/sdcard/monkey.apk");
//                } else {
//                    Toast.makeText(MainActivity.this, "已安装Monkey工具", Toast.LENGTH_SHORT).show();
//
//                }
//
//            }
//        });

//        final View mApplist = (View) findViewById(R.id.two);
//        mApplist.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setClass(MainActivity.this, AppListActivity.class);
//                startActivity(intent);
//            }
//
//
//        });


    }





    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    protected void initView() {
        mHomeParent = (LinearLayout) findViewById(R.id.home_parent);

        mRecyclerView = (MzRecyclerView) findViewById(R.id.grid_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        mData = ((MyApplication) getApplicationContext()).getDemoData().data;
        mViewList = new ArrayList<>();
        mViewList.addAll(mData);

        mGridAdapter = new RecyclerViewAdapter(this, mViewList);
        mGridAdapter.setOnRecyclerViewListener(this);
        mRecyclerView.setAdapter(mGridAdapter);
        mRecyclerView.setDrawSelectorOnTop(true);

//        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                mRecyclerView.setFocusableInTouchMode(true);
//                mRecyclerView.setFocusable(true);
//                mRecyclerView.requestFocus();
//                return false;
//            }
//        });
//        getSupportActionBar().hide();
    }


    @Override
    public void onItemClick(int position) {

        String title = mViewList.get(position).title;
        String activity = mViewList.get(position).activity;
        if (null != activity) {
            Intent intent = new Intent();
            intent.setClassName(this.getPackageName(), activity);
            intent.putExtra("title", title);
            intent.putExtra("isShowBack", true);
            startActivity(intent);
        }
    }

    @Override
    public boolean onItemLongClick(int position) {
        return false;
    }
}
