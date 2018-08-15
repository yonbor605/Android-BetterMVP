package com.yonbor.bettermvp.model;


/**
 * @Description: 聚合数据服务器返回  基类
 * @Author: YinYongbo
 * @Time: 2018/8/15 10:12
 */
public class ResultModel<T> {

    public String reason;
    public int error_code;
    public T result;

}