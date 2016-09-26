package com.loveuu.vv.widget.citypicker;

/**
 * Created by VV on 2016/9/22.
 */

public class SortModel {
    private String name;   // 城市名
    private String sortLetters;  // 排序字母

    private String adcode; // 城市国标码
    private double lng; // 经度
    private double lat; // 纬度

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }

    public String getAdcode() {
        return adcode;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

}
