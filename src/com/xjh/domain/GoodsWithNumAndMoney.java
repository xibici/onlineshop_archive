package com.xjh.domain;
import java.math.BigDecimal;
import java.util.Date;
public class GoodsWithNumAndMoney {
    private Integer id;
    private String name;
    private Date pubdate;
    private String picture;
    private BigDecimal price;
    private Integer star;
    private String intro;
    private Integer typeid;
    private GoodsType goodsType;
    private Integer num;
    private BigDecimal money;
    @Override
    public String toString() {
        return "GoodsWithNumAndMoney{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pubdate=" + pubdate +
                ", picture='" + picture + '\'' +
                ", price=" + price +
                ", star=" + star +
                ", intro='" + intro + '\'' +
                ", typeid=" + typeid +
                ", goodsType=" + goodsType +
                ", num=" + num +
                ", money=" + money +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getPubdate() {
        return pubdate;
    }

    public void setPubdate(Date pubdate) {
        this.pubdate = pubdate;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Integer getTypeid() {
        return typeid;
    }

    public void setTypeid(Integer typeid) {
        this.typeid = typeid;
    }

    public GoodsType getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(GoodsType goodsType) {
        this.goodsType = goodsType;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public GoodsWithNumAndMoney() {
    }

    public GoodsWithNumAndMoney(Integer id, String name, Date pubdate, String picture,
                                BigDecimal price, Integer star, String intro, Integer typeid,
                                GoodsType goodsType, Integer num, BigDecimal money) {
        this.id = id;
        this.name = name;
        this.pubdate = pubdate;
        this.picture = picture;
        this.price = price;
        this.star = star;
        this.intro = intro;
        this.typeid = typeid;
        this.goodsType = goodsType;
        this.num = num;
        this.money = money;
    }
}

