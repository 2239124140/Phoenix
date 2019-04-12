package com.zjy.phoenix.module.admin.model;

import com.alibaba.fastjson.annotation.JSONField;

public class JSONVO {
    @JSONField(serialize = false)
    private String name;
    private int  age;
    private boolean isBoy;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isBoy() {
        return isBoy;
    }

    public void setBoy(boolean boy) {
        isBoy = boy;
    }
}
