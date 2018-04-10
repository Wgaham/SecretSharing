package com.wgaham.secretsharing;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * 秘密数据库的ORM映射模型
 *
 * @author Wgaham
 *         Created by Wgaham on 2018/3/15.
 */

public class Secret extends DataSupport{
    private int id;

    private String name;

    private int allLevel;

    private int allParson;

    private String timeOfStart;

    private String timeOfEnd;

    private int secret;

    private List<Member> memberList = new ArrayList<>();


    public String getTimeOfStart() {
        return timeOfStart;
    }

    public void setTimeOfStart(String timeOfStart) {
        this.timeOfStart = timeOfStart;
    }

    public String getTimeOfEnd() {
        return timeOfEnd;
    }

    public void setTimeOfEnd(String timeOfEnd) {
        this.timeOfEnd = timeOfEnd;
    }


    public List<Member> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<Member> memberList) {
        this.memberList = memberList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAllLevel() {
        return allLevel;
    }

    public void setAllLevel(int allLevel) {
        this.allLevel = allLevel;
    }

    public int getAllParson() {
        return allParson;
    }

    public void setAllParson(int allParson) {
        this.allParson = allParson;
    }

    public int getSecret() {
        return secret;
    }

    public void setSecret(int secret) {
        this.secret = secret;
    }
}
