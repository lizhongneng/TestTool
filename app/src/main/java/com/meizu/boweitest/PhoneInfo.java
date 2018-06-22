package com.meizu.boweitest;

/**
 * Created by meizu on 2018/6/22.
 */

public class PhoneInfo {

    String info;
    String name;
    long info1;

    public PhoneInfo(String name, String info){
        this.name = name;
        this.info = info;
    }

    public PhoneInfo(String name, long info1){
        this.name = name;
        this.info1 = info1;
    }

    public String getName() {
        if(null == name)
            return "";
        else
            return name;
    }


    public String getInfo() {
        if(null == info)
            return "";
        else
            return info;
    }



}











