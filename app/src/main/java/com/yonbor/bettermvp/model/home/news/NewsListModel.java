package com.yonbor.bettermvp.model.home.news;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @Description:
 * @Author: YinYongbo
 * @Time: 2018/8/13 16:47
 */
public class NewsListModel implements Parcelable {


    private String uniquekey;
    private String title;
    private String date;
    private String category;
    private String author_name;
    private String url;
    private String thumbnail_pic_s;
    private String thumbnail_pic_s02;
    private String thumbnail_pic_s03;


    public String getUniquekey() {
        return uniquekey;
    }

    public void setUniquekey(String uniquekey) {
        this.uniquekey = uniquekey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnail_pic_s() {
        return thumbnail_pic_s;
    }

    public void setThumbnail_pic_s(String thumbnail_pic_s) {
        this.thumbnail_pic_s = thumbnail_pic_s;
    }

    public String getThumbnail_pic_s02() {
        return thumbnail_pic_s02;
    }

    public void setThumbnail_pic_s02(String thumbnail_pic_s02) {
        this.thumbnail_pic_s02 = thumbnail_pic_s02;
    }

    public String getThumbnail_pic_s03() {
        return thumbnail_pic_s03;
    }

    public void setThumbnail_pic_s03(String thumbnail_pic_s03) {
        this.thumbnail_pic_s03 = thumbnail_pic_s03;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uniquekey);
        dest.writeString(this.title);
        dest.writeString(this.date);
        dest.writeString(this.category);
        dest.writeString(this.author_name);
        dest.writeString(this.url);
        dest.writeString(this.thumbnail_pic_s);
        dest.writeString(this.thumbnail_pic_s02);
        dest.writeString(this.thumbnail_pic_s03);
    }

    public NewsListModel() {
    }

    protected NewsListModel(Parcel in) {
        this.uniquekey = in.readString();
        this.title = in.readString();
        this.date = in.readString();
        this.category = in.readString();
        this.author_name = in.readString();
        this.url = in.readString();
        this.thumbnail_pic_s = in.readString();
        this.thumbnail_pic_s02 = in.readString();
        this.thumbnail_pic_s03 = in.readString();
    }

    public static final Creator<NewsListModel> CREATOR = new Creator<NewsListModel>() {
        @Override
        public NewsListModel createFromParcel(Parcel source) {
            return new NewsListModel(source);
        }

        @Override
        public NewsListModel[] newArray(int size) {
            return new NewsListModel[size];
        }
    };
}
