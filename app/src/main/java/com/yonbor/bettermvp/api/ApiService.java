package com.yonbor.bettermvp.api;


import com.yonbor.bettermvp.model.Result;
import com.yonbor.bettermvp.model.ResultModel;
import com.yonbor.bettermvp.model.home.news.NewsListModel;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    // 聚合新闻头条接口
    @GET("index")
    Flowable<ResultModel<Result<List<NewsListModel>>>> getNews(@Query("type") String type, @Query("key") String key);

}
