package com.loveuu.vv.bean;

/**
 * Created by VV on 2016/9/29.
 */

public class HomeLastShareBean {

    /**
     * id : 1
     * hid : 1 // 楼盘id
     * type : 1 // 1-二手房 2-新房
     * browse_num : 1 // 浏览数量
     * user_id : 7 // 用户id
     * room_count : 0 //房数
     * hall_count : 0 //厅数
     * hasHB : 0  //是否有红包
     * hbType : 2 // 1-企业红包 2-个人红包 0-无红包
     * isNew : 0 // 是否为新房
     * name : 111 // 楼盘名称
     * really_area : 110 // 实用面积
     * is_share : 1 // 是否联合推广
     * favoriteNum : 1 // 想看房次数
     * picture : *** // 图片
     * activityId : 132 //红包活动ID
     * update_time : 1472634586  //更新时间
     * agentId : 7 红包用户ID
     */

    private String id;
    private String hid;
    private String type;
    private String browse_num;
    private String user_id;
    private int room_count;
    private int hall_count;
    private int hasHB;
    private int hbType;
    private int isNew;
    private String name;
    private String really_area;
    private String is_share;
    private String favoriteNum;
    private String picture;
    private String activityId;
    private String update_time;
    private String agentId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHid() {
        return hid;
    }

    public void setHid(String hid) {
        this.hid = hid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrowse_num() {
        return browse_num;
    }

    public void setBrowse_num(String browse_num) {
        this.browse_num = browse_num;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getRoom_count() {
        return room_count;
    }

    public void setRoom_count(int room_count) {
        this.room_count = room_count;
    }

    public int getHall_count() {
        return hall_count;
    }

    public void setHall_count(int hall_count) {
        this.hall_count = hall_count;
    }

    public int getHasHB() {
        return hasHB;
    }

    public void setHasHB(int hasHB) {
        this.hasHB = hasHB;
    }

    public int getHbType() {
        return hbType;
    }

    public void setHbType(int hbType) {
        this.hbType = hbType;
    }

    public int getIsNew() {
        return isNew;
    }

    public void setIsNew(int isNew) {
        this.isNew = isNew;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReally_area() {
        return really_area;
    }

    public void setReally_area(String really_area) {
        this.really_area = really_area;
    }

    public String getIs_share() {
        return is_share;
    }

    public void setIs_share(String is_share) {
        this.is_share = is_share;
    }

    public String getFavoriteNum() {
        return favoriteNum;
    }

    public void setFavoriteNum(String favoriteNum) {
        this.favoriteNum = favoriteNum;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }
}
