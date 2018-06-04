package com.meizu.boweitest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;




/**
 * Created by lizhongneng on 2018/5/23.
 */

public class FastMonkeyActivity extends AppCompatActivity {
    private View mActionView;


    private static final String TAG = "FastMonkeyActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fast_monkey);
        mActionView = (View) findViewById(R.id.ActionView);
        mActionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FastMonkeyActivity.this, "开始执行Monkey",Toast.LENGTH_LONG).show();
                new UiautomatorThread().start();

            }});
    }


    public void runActionViewMonkey(){
        Log.i(TAG, "runMyUiautomator: ");
                new UiautomatorThread().start();

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


}










