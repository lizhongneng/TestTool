package com.meizu.boweitest;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import flyme.support.v7.app.AppCompatActivity;

/**
 * Created by meizu on 2018/6/22.
 */

public class PhoneInfoActivity extends AppCompatActivity {
    private List<PhoneInfo> phoneInfos = new ArrayList<PhoneInfo>();
    flyme.support.v7.app.ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_info_activity);
        // 获得actionbar
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true); // 设置显示左边的返回按钮
        mActionBar.setTitle("系统信息"); // 设置标题栏Title
        initView();
        PhoneInfoAdapter adapter = new PhoneInfoAdapter(this, R.layout.phone_info, phoneInfos);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
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

    private void initView() {
        phoneInfos.add(new PhoneInfo("机型: ",android.os.Build.BOARD));
        phoneInfos.add(new PhoneInfo("系统版本: ",android.os.Build.DISPLAY));
        phoneInfos.add(new PhoneInfo("类型: ",android.os.Build.TYPE));
        phoneInfos.add(new PhoneInfo("Android版本: ",android.os.Build.VERSION.RELEASE));


    }


}
