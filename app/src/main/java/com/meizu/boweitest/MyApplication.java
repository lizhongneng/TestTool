package com.meizu.boweitest;

import android.app.Application;


import com.google.gson.Gson;
import com.meizu.boweitest.ViewTreeData;
import com.google.gson.reflect.TypeToken;

/**
 * Created by cuicui on 2017/11/29.
 */
public class MyApplication extends Application {

    private static ViewTreeData mData;
    private static Gson sGson;

    @Override
    public void onCreate() {
        super.onCreate();
        sGson = new Gson();
        mData = getData();
    }

    public ViewTreeData getDemoData() {
        if (null == mData || mData.data.size() == 0) {
            mData = getData();
        }
        return mData;
    }

    public void clearData() {
        if (null != mData) {
            mData.data.clear();
            mData = null;
        }
    }

    private ViewTreeData getData() {
        String jsonname = "viewTree.json";
        return MyApplication.getGson().fromJson(Utils.getJson(this, jsonname), new TypeToken<ViewTreeData>() {
        }.getType());
    }

    private static Gson getGson() {
        return sGson;
    }
}
