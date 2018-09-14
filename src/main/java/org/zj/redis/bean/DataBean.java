package org.zj.redis.bean;

import java.io.Serializable;

public class DataBean implements Serializable {/*
    private static final long serialVersionUID = 1L;*/
    private String commond;
    private Data data;

    public String getCommond() {
        return commond;
    }

    public void setCommond(String commond) {
        this.commond = commond;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DataBean{" +
                "commond='" + commond + '\'' +
                ", data=" + data +
                '}';
    }
}
