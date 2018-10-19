package com.leew.biker.bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * author:Leew
 * date:2018/10/17  15:27
 * vesion:1.0
 * description:
 */
public class UserInfo extends BmobUser
{
    private BmobFile head;
    private String name;
    private String place;
    private String sex;
    private Integer age;
    private Integer high;
    private Integer weight;
    private Integer guanzhu;
    private Integer fensi;
    private Integer jifen;
    private String licheng;
    private String alllicheng;
    private String sporttime;
    private String localrick;


    public UserInfo() {
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getHigh() {
        return high;
    }

    public void setHigh(Integer high) {
        this.high = high;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public BmobFile getHead() {
        return head;
    }

    public void setHead(BmobFile head) {
        this.head = head;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGuanzhu() {
        return guanzhu;
    }

    public void setGuanzhu(Integer guanzhu) {
        this.guanzhu = guanzhu;
    }

    public Integer getFensi() {
        return fensi;
    }

    public void setFensi(Integer fensi) {
        this.fensi = fensi;
    }

    public Integer getJifen() {
        return jifen;
    }

    public void setJifen(Integer jifen) {
        this.jifen = jifen;
    }

    public String getLicheng() {
        return licheng;
    }

    public void setLicheng(String licheng) {
        this.licheng = licheng;
    }

    public String getAlllicheng() {
        return alllicheng;
    }

    public void setAlllicheng(String alllicheng) {
        this.alllicheng = alllicheng;
    }

    public String getSporttime() {
        return sporttime;
    }

    public void setSporttime(String sporttime) {
        this.sporttime = sporttime;
    }

    public String getLocalrick() {
        return localrick;
    }

    public void setLocalrick(String localrick) {
        this.localrick = localrick;
    }
}
