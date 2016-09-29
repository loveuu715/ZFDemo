package com.loveuu.vv.mvp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.loveuu.vv.bean.HomeLastShareBean;

import java.util.List;

/**
 * Created by VV on 2016/9/29.
 */

public class HomeLastShareAdapter extends BaseAdapter {
    private List<HomeLastShareBean> mShareBeanList;
    private Context mContext;

    public HomeLastShareAdapter(Context context, List<HomeLastShareBean> shareBeanList) {
        this.mContext = context;
        mShareBeanList = shareBeanList;
    }

    @Override
    public int getCount() {
        return mShareBeanList == null ? 0:mShareBeanList.size();
    }

    @Override
    public HomeLastShareBean getItem(int position) {
        return mShareBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    static class ShareViewHolder {
        ImageView ivPic;//图片
        TextView tvName;//楼盘名
        TextView tvPrice;//价格
        TextView tvRedPack;//红包数量
        TextView tvLikeCount;//意向人数
    }

}
