package com.wgaham.secretsharing;

import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.annotation.SmartTable;

import org.litepal.crud.DataSupport;

/**
 * 秘密参与者的ORM映射类
 *
 * @author Wgaham
 *
 *Created by Wgaham on 2018/3/15.
 */
@SmartTable(name="参与者表")
public class Member extends DataSupport {
    private int id;

    @SmartColumn(id =2,name = "等级")
    private int level;

    private int threshold;

    @SmartColumn(id =3,name = "等级内序号")
    private int parson;

    @SmartColumn(id =4,name = "子份额数")
    private int share;

    @SmartColumn(id =1,name = "姓名")
    private String name;

    private Secret secrets;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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

    public Secret getSecrets() {
        return secrets;
    }

    public void setSecrets(Secret secrets) {
        this.secrets = secrets;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public int getShare() {
        return share;
    }

    public void setShare(int share) {
        this.share = share;
    }
}
