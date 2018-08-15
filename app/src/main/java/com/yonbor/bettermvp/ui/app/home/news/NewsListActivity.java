package com.yonbor.bettermvp.ui.app.home.news;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yonbor.baselib.base.CreatePresenter;
import com.yonbor.baselib.widght.NormalTitleBar;
import com.yonbor.baselib.widght.RecyclerViewDivider;
import com.yonbor.bettermvp.R;
import com.yonbor.bettermvp.adapter.home.news.NewsListAdapter;
import com.yonbor.bettermvp.base.BaseActivity;
import com.yonbor.bettermvp.model.home.news.NewsListModel;
import com.yonbor.bettermvp.ui.common.WebActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@CreatePresenter(presenter = {NewsListPresenter.class})
public class NewsListActivity extends BaseActivity<NewsListPresenter> implements NewsListView {

    @BindView(R.id.ntb)
    NormalTitleBar ntb;
    @BindView(R.id.rv_news)
    RecyclerView rvNews;
    private ArrayList<NewsListModel> newsList;
    private NewsListAdapter adapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_list;
    }

    @Override
    protected void initView() {
        ntb.setBackVisibility(true);
        ntb.setTitleText("新闻头条");
        ntb.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresenter().getNewsListData();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvNews.addItemDecoration(new RecyclerViewDivider(this,
                LinearLayoutManager.VERTICAL, 1, getResources().getColor(R.color.gray_divider)));
        rvNews.setLayoutManager(layoutManager);


    }


    @Override
    public void returnNewsListData(List<NewsListModel> list) {
        newsList = (ArrayList<NewsListModel>) list;
        adapter = new NewsListAdapter(R.layout.item_newslist, newsList);
        rvNews.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(NewsListActivity.this, WebActivity.class);
                intent.putExtra("title", "新闻详情");
                intent.putExtra("url", newsList.get(position).getUrl());
                startActivity(intent);
            }
        });
    }
}
