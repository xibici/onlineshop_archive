package com.xjh.domain;
//lottery乐透抽奖 转盘javabean
public class Prize {
    private int id;
    private int[] min;// 最小角
    private int[] max;// 最大角
    private String prize;// 奖品名
    private int v;// 概率

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int[] getMin() {
        return min;
    }

    public void setMin(int[] min) {
        this.min = min;
    }

    public int[] getMax() {
        return max;
    }

    public void setMax(int[] max) {
        this.max = max;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public int getV() {
        return v;
    }

    public void setV(int v) {
        this.v = v;
    }

    public Prize(int id, int[] min, int[] max, String prize, int v) {
        super();
        this.id = id;
        this.min = min;
        this.max = max;
        this.prize = prize;
        this.v = v;
    }

    public Prize() {
        super();
        // TODO Auto-generated constructor stub
    }

}

