package com.example.q.eathero.model;

import android.graphics.Bitmap;

import com.baidu.mapapi.model.LatLng;

import java.io.File;

/**
 * Created by Q on 2016/6/14.
 */
public class ShopBean {
    private String longitude;
    private String latitude;
    private String shopName;
    private String description;//描述
    private File bitmap;//bmobfile
    private int rank;//星级

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
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

    public File getBitmap() {
        return bitmap;
    }

    public void setBitmap(File bitmap) {
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

    private String comment;//评价

}
