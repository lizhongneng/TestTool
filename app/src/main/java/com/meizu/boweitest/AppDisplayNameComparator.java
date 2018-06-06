package com.meizu.boweitest;

import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.text.Collator;
import java.util.Comparator;

/**
 * 该类主要用来比较两个app的名字决定它们的排序顺序，比较规则是
 * 1.通过Collator比较，根据拼音字母排序显示
 *
 */
public class AppDisplayNameComparator implements Comparator<AppInfo>{

    private final Collator   mCollator;
//    private PackageManager   mPM;
    
    public AppDisplayNameComparator() {
//        mPM = pm;
        mCollator = Collator.getInstance();
        mCollator.setStrength(Collator.PRIMARY);
    }
    
    @Override
    public int compare(AppInfo lhs, AppInfo rhs) {

        return compareWithName(lhs, rhs);
    }
    
    /**
     * 根据app名字比较
     * 1.通过Collator比较
     * @param a
     * @param b
     * @return
     */
    public final int compareWithName(AppInfo a, AppInfo b) {

        if (a == null && b == null) {

            return 0;
        }

        if (a == null) {

            return 1;
        }

        if (b == null) {

            return -1;
        }

        CharSequence  sa = a.getAppName();
        if (sa == null) sa = a.getActivityName();
        CharSequence  sb = b.getAppName();
        if (sb == null) sb = b.getActivityName();

        String str1 = HanziToPinyin.getInstance().transliterate(sa.toString().trim());
        String str2 = HanziToPinyin.getInstance().transliterate(sb.toString().trim());

        return mCollator.compare(str1, str2);
    }
}
