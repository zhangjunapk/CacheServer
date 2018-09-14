package org.zj.redis;

import org.zj.redis.bean.Data;
import org.zj.redis.bean.DataBean;
import org.zj.redis.bean.Student;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class RedisClient implements IRedisClient{
    static Socket socket;
    public RedisClient(String ip,int port) throws IOException {
        socket=new Socket(ip,port);
    }
    public Object add(String key, Object o) throws IOException {
        DataBean dataBean=new DataBean();
        dataBean.setCommond("add");
        Data data=new Data();
        data.setKey(key);
        data.setObj(o);
        dataBean.setData(data);
        ObjectOutputStream oos= new ObjectOutputStream( socket.getOutputStream());

        oos.writeObject(dataBean);
        return o;
    }

    public List get(String key) {
        return null;
    }

    public Object modify(String key, String id, Object o) {
        return null;
    }

    public boolean closeService() {
        return false;
    }

    public Object get(String key, String id) {
        return null;
    }

    public boolean setId(String key, String idName) {
        return false;
    }

    public static void main(String[] args) throws IOException {
        Student student=new Student();
        student.setUsername("张君");
        student.setPassword("zhangjun");
        new RedisClient("localhost",8888).add("student",student);
        student=null;
    }

}
