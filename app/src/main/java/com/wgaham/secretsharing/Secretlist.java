package com.wgaham.secretsharing;

/**
 * 主页面列表的构建类
 *
 * @author Wgaham
 *         Created by Wgaham on 2018/3/13.
 */

public class Secretlist {

    private int id;

    private String name;

    public Secretlist(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
