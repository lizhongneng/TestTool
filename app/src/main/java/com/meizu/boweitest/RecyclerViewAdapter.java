package com.meizu.boweitest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import flyme.support.v7.widget.RecyclerView;

/**
 * User: YangYi
 * Email: yangyi2@meizu.com
 * Dete: 16-7-5
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<ViewInfo> mList;
    private OnRecyclerViewListener mOnRecyclerViewListener;
    private static final int FLYME7 = 0;
    private static final int TEXT_ICON = 1;
    private static final int LIST_ICON = 2;
    private static final int TITLE_ICON = 3;
    private static final int TIPS_ICON = 4;
    private static final int WIDGET_ICON = 5;
    private static final int BOTTOM_ICON = 6;
    private static final int POPUP_ICON = 7;
    private static final int ACTIVEVIEW_ICON = 8;
    private static final int IMAGE_ICON = 9;

    public RecyclerViewAdapter(Context context) {

        mContext = context;
    }

    public RecyclerViewAdapter(Context context, List<ViewInfo> list) {
        mContext = context;
        mList = list;
    }

    public void cleaAdapter() {
        mContext = null;
        mList.clear();
        mList = null;
        mOnRecyclerViewListener = null;
    }

    public void clear() {
        mContext = null;
        mOnRecyclerViewListener = null;
        mList.clear();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_item, parent,false);

        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GridViewHolder viewHolder = (GridViewHolder) holder;
        ViewInfo info = mList.get(position);
        viewHolder.position = position;
        viewHolder.titleView.setText(info.title);
        switch (position) {
            case FLYME7:
                viewHolder.iconView.setImageResource(R.drawable.punch_card);
            case TEXT_ICON:
                viewHolder.iconView.setImageResource(R.drawable.punch_card);
                break;
            case LIST_ICON:
                viewHolder.iconView.setImageResource(R.drawable.punch_card);
            default:
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
        mOnRecyclerViewListener = onRecyclerViewListener;
    }

    class GridViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        ImageView iconView;
        TextView titleView;
        int position;

        public GridViewHolder(View itemView) {
            super(itemView);
            iconView = (ImageView) itemView.findViewById(R.id.demo_icon);
            titleView = (TextView) itemView.findViewById(R.id.demo_type);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (null != mOnRecyclerViewListener) {
                mOnRecyclerViewListener.onItemClick(position);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (null != mOnRecyclerViewListener) {
                return mOnRecyclerViewListener.onItemLongClick(position);
            }
            return false;
        }
    }

    public interface OnRecyclerViewListener {
        void onItemClick(int position);

        boolean onItemLongClick(int position);
    }
}
