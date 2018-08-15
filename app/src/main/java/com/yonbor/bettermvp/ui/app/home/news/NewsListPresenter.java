package com.yonbor.bettermvp.ui.app.home.news;


import android.widget.Toast;

import com.yonbor.baselib.base.BasePresenter;
import com.yonbor.bettermvp.api.HostType;
import com.yonbor.bettermvp.api.HttpApi2;
import com.yonbor.bettermvp.model.Result;
import com.yonbor.bettermvp.model.ResultModel;
import com.yonbor.bettermvp.model.home.news.NewsListModel;


import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @Description:
 * @Author: YinYongbo
 * @Time: 2018/8/13 15:22
 */
public class NewsListPresenter extends BasePresenter<NewsListView> {


    public void getNewsListData() {

        Flowable<ResultModel<Result<List<NewsListModel>>>> flowable = HttpApi2.getApiService(HostType.HOME_NEW_LIST).getNews("top", "9d5c5d5de8c5ff12244879da48f5bfb3");
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultModel<Result<List<NewsListModel>>>>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(ResultModel<Result<List<NewsListModel>>> resultResultModel) {
                        if (resultResultModel.result.data != null) {

                            List<NewsListModel> list = resultResultModel.result.data;
//                            String stat = resultResultModel.result.stat;
                            if (list.size() > 0) {
                                mView.returnNewsListData(list);
                            }
                        } else {
                            Toast.makeText(mContext, "暂无数据", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }
}
