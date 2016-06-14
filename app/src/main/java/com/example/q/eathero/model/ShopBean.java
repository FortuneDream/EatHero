package com.example.q.eathero.model;

import android.graphics.Bitmap;

import com.baidu.mapapi.model.LatLng;

/**
 * Created by Q on 2016/6/14.
 */
public class ShopBean {
    private LatLng latLng;
    private String shopName;
    private String description;//描述
    private Bitmap bitmap;
    private int rank;//星级
    private String comment;//评价

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
