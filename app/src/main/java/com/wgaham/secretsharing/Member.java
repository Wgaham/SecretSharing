package com.wgaham.secretsharing;

/**
 * 秘密参与者的ORM映射类
 *
 * @author Wgaham
 *         <p>
 *         Created by Wgaham on 2018/3/15.
 */

public class Member {
    private int id;

    private int secretId;

    private int level;

    private int threshold;

    private int parson;

    private String name;

    private Secret secret;

    public Secret getSecret() {
        return secret;
    }

    public void setSecret(Secret secret) {
        this.secret = secret;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSecretId() {
        return secretId;
    }

    public void setSecretId(int secretId) {
        this.secretId = secretId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public int getParson() {
        return parson;
    }

    public void setParson(int parson) {
        this.parson = parson;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
