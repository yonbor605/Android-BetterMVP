package com.yonbor.bettermvp.adapter.home.news;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yonbor.bettermvp.R;
import com.yonbor.bettermvp.app.GlideApp;
import com.yonbor.bettermvp.model.home.news.NewsListModel;

import java.util.List;

/**
 * @Description:
 * @Author: YinYongbo
 * @Time: 2018/8/15 14:02
 */
public class NewsListAdapter extends BaseQuickAdapter<NewsListModel, BaseViewHolder> {

    public NewsListAdapter(int layoutResId, @Nullable List<NewsListModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsListModel item) {

        helper.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_author, item.getAuthor_name())
                .setText(R.id.tv_date, item.getDate());

        ImageView iv_img = helper.getView(R.id.iv_img);
        GlideApp.with(mContext)
                .load(item.getThumbnail_pic_s())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .fitCenter().into(iv_img);
    }
}
