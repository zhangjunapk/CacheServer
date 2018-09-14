package org.zj.redis.bean;

import java.io.Serializable;

public class Data implements Serializable {/*
    private static final long serialVersionUID = 1L;*/
    private String key;
    private Object obj;
    private String idName;
    private String id;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Data{" +
                "key='" + key + '\'' +
                ", obj=" + obj.toString() +
                ", idName='" + idName + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
