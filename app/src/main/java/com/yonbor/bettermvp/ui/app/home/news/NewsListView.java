package com.yonbor.bettermvp.ui.app.home.news;

import com.yonbor.baselib.base.BaseView;
import com.yonbor.bettermvp.model.home.news.NewsListModel;

import java.util.List;


/**
 * @Description:
 * @Author: YinYongbo
 * @Time: 2018/8/13 15:21
 */
public interface NewsListView extends BaseView {


    void returnNewsListData(List<NewsListModel> list);

}
