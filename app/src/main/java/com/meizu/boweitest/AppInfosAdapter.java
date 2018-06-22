package com.meizu.boweitest;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flyme.support.v7.widget.MzRecyclerView;
import flyme.support.v7.widget.RecyclerFastScrollLetter;


/**
 * Created by meizu on 2018/5/30.
 */

public class AppInfosAdapter extends MzRecyclerView.Adapter<AppInfosAdapter.ViewHolder> implements RecyclerFastScrollLetter.IScrollIndexer {
    Context context;
    List<AppInfo> appInfos;
    private final String mLetters = "$ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final int mLetterCount = mLetters.length();
    private final Map mFirstLetterMap = new HashMap();
    private Map<String, String> mColorMap;
    private String[] COLOR = {
            "#fdbd3b",
            "#f95c30",
            "#ee2931",
            "#6053ea",
            "#258fea",
            "#21c0ce",
            "#42bf6e"
    };
    private MyOnItemClickListener itemClickListener;


    public AppInfosAdapter(Context context, List<AppInfo> infos) {
        this.context = context;
        this.appInfos = infos;
        if (mFirstLetterMap.size() > 0) {
            mFirstLetterMap.clear();
        }
        mColorMap = new HashMap<>();
        updateFirstLetterPosition();

        Log.d("lzn", appInfos.size() + "");

    }


    // 录入 数据中的 分组在列表中的位置
    public  void updateFirstLetterPosition() {
        //String beginLetter = String.valueOf(mListItems.get(0).charAt(0));

        String flagtField = String.valueOf(appInfos.get(0).getAppName().charAt(0));
        String flagtLetter = HanziToPinyin.getInstance().transliterate(flagtField.toString().trim());
        mFirstLetterMap.put(flagtLetter, 0);
        mColorMap.put(flagtLetter, COLOR[0]);
        String firstLetter = flagtLetter;
        for (int i = 0; i < appInfos.size(); i++) {
            String firstField = String.valueOf(appInfos.get(i).getAppName().charAt(0));
            firstLetter = String.valueOf(HanziToPinyin.getInstance().transliterate(firstField.toString().trim()).charAt(0));
            if (!firstLetter.equals(flagtLetter)) {
                mFirstLetterMap.put(firstLetter, i);
                mColorMap.put(flagtLetter, COLOR[i % 7]);
            }
            flagtLetter = firstLetter;
        }
        //printFirstLetterMap();
    }

    public Map<String, String> getColorMap() {

        return mColorMap;
    }

    private String getTargetOverLayText(float proportion) {
        char touchLetter = mLetters.charAt(getTouchLetterPosition(proportion));
        return String.valueOf(touchLetter);
    }

    private int getTouchLetterPosition(float proportion) {
        return (int) getRangeValue(0, mLetterCount - 1, (proportion * mLetterCount));
    }

    private float getRangeValue(float min, float max, float value) {
        float minimum = Math.max(min, value);
        return Math.min(minimum, max);
    }

    @Override
    public String getOverlayText(float v) {
        String target = getTargetOverLayText(v);
        int position = -1;
        // 首先搜索LetterMap中有没有这个字母
        if (mFirstLetterMap.containsKey(target)) {
            position = (int) mFirstLetterMap.get(target);
        }
        if (position == -1) {
            // 没有找到相应的位置
            // 先往前找  不行就用第一个
            int targetPos = mLetters.indexOf(target);
            Log.e("lzn", target + " 的位置： " + " " + targetPos);
            String letter1 = null;
            int letter1Pos = -1;
            for (int i = targetPos - 1; i >= 0; i--) {
                letter1 = String.valueOf(mLetters.charAt(i));
                if (mFirstLetterMap.containsKey(letter1)) {
                    letter1Pos = (int) mFirstLetterMap.get(letter1);
                    break;
                }
            }
            if (letter1Pos == -1) {
                letter1 = String.valueOf(appInfos.get(0).getAppName().charAt(0));
            }
            return letter1;

        }
        return target;
    }

    @Override
    public int getScrollPosition(float v) {
        int position = 0;
        String letter = getOverlayText(v);
        // 遍历找到首字母匹配的第一个Item的位置
        position = (int) mFirstLetterMap.get(letter);
        return position;

    }

    public static class ViewHolder extends MzRecyclerView.ViewHolder {
        ImageView appIconImg;
        TextView appNameText;
        TextView appPackageText;
        TextView appActivityText;
        TextView appVersionText;

        public ViewHolder(View itemView) {
            super(itemView);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // TODO Auto-generated method stub
        ViewHolder viewHolder;
        LayoutInflater mInflater = LayoutInflater.from(context);
        View View = mInflater.inflate(R.layout.item_list, viewGroup, false);
        viewHolder = new ViewHolder(View);
        viewHolder.appIconImg = (ImageView) View.findViewById(R.id.image);
        viewHolder.appNameText = (TextView) View.findViewById(R.id.label);
        viewHolder.appPackageText = (TextView) View.findViewById(R.id.package_name);
//        viewHolder.appActivityText = (TextView)View.findViewById(R.id.activity_name);
        viewHolder.appVersionText = (TextView) View.findViewById(R.id.version_name);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.appIconImg.setBackground(appInfos.get(position).getDrawable());
        viewHolder.appNameText.setText(appInfos.get(position).getAppName());
        viewHolder.appPackageText.setText(appInfos.get(position).getPackageName());
//        viewHolder.appActivityText.setText(appInfos.get(position).getActivityName());
        viewHolder.appVersionText.setText(appInfos.get(position).getVersionName());

        /*将接收到的ViewHolder强转成自定义的VIewHolder*/
        final ViewHolder myViewHolder =  viewHolder;
         /*自定义item的点击事件不为null，设置监听事件*/
        if (itemClickListener != null) {
            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.OnItemClickListener(myViewHolder.itemView, myViewHolder.getLayoutPosition());
                }
            });
        }
    }


    @Override
    public long getItemId(int index) {
        // TODO Auto-generated method stub
        return index;
    }

    @Override
    public int getItemCount() {
        int count = appInfos == null ? 0 : appInfos.size();
        return count;
    }


    /**
     * item点击接口
     */
    public interface MyOnItemClickListener {
        void OnItemClickListener(View view, int position);
    }

    /**
     * 列表点击事件
     *
     * @param itemClickListener
     */
    public void setOnItemClickListener(MyOnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }





}
