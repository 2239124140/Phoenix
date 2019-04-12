package com.zjy.phoenix.module.admin.model;

/**
 * @program: myproject01
 * @description:
 * @author: ZhangJianYong
 * @create: 2019-04-11 18:35
 **/
public class EchartsVO {
    private String name;
    private int score;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public EchartsVO(String name, int score) {this.name = name;
        this.score = score;
    }

    public EchartsVO() {
    }
}
