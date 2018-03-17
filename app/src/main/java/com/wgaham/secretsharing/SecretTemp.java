package com.wgaham.secretsharing;

import java.io.Serializable;

/**
 * 秘密数据的暂存类，用于下一步存入数据库
 *
 * @author Wgaham
 *         Created by Wgaham on 2018/3/17.
 */

public class SecretTemp implements Serializable {
    private String secretName, startTime, endTime;

    private int secretValue;

    public String getSecretName() {
        return secretName;
    }

    public void setSecretName(String secretName) {
        this.secretName = secretName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getSecretValue() {
        return secretValue;
    }

    public void setSecretValue(int secretValue) {
        this.secretValue = secretValue;
    }
}
