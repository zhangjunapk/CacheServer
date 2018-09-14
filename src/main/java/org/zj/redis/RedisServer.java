package org.zj.redis;

import org.zj.redis.bean.DataBean;
import org.zj.redis.util.ValUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Field;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RedisServer implements IRedisClient {

    static ServerSocket serverSocket;

    static Map<String, List<Object>> containerMap=new HashMap<String, List<Object>>();
    static Map<String,String> idMap=new HashMap<String, String>();
    public void init(int port) throws IOException, ClassNotFoundException {
        serverSocket=new ServerSocket(port);
        //将硬盘上的数据反序列化放到容器
        System.out.println("将对象从硬盘反序列化到内存");
        service();
    }

    public void service() throws IOException, ClassNotFoundException {
        //死循环处理客户端请求
        for(;;){
            Socket accept = serverSocket.accept();
            InputStream is = accept.getInputStream();
            ObjectInputStream ois=new ObjectInputStream(is);
            Object o = ois.readObject();
            DataBean dataBean= (DataBean) o;
            System.out.println(dataBean.toString());
            handleCommond(dataBean);
        }
    }

    /**
     * 处理客户端传送过来的命令及数据
     * @param dataBean
     */
    private void handleCommond(DataBean dataBean) {
        System.out.println("处理 ---"+dataBean.getCommond());
        switch (dataBean.getCommond()){
            case "add":
                add(dataBean.getData().getKey(),dataBean.getData().getObj());
                break;
        }

        for(Map.Entry<String,List<Object>> entry:containerMap.entrySet()){
            System.out.println("key:  "+entry.getKey());
            for(Object obj:entry.getValue()){
                System.out.println("     "+obj);
            }
        }

    }

    public void destroy(){
        //数据序列化到硬盘并关闭服务
        System.out.println("将对象持久化到硬盘");
    }

    public Object add(String key, Object o) {
        if(containerMap.get(key)==null){
            List<Object> list=new ArrayList<Object>();
            list.add(o);
            containerMap.put(key,list);
            System.out.println("service:容器里面没有，我要创建然后再添加");
            return o;
        }
        List<Object> objects = containerMap.get(key);
        System.out.println("service:容器里面有 我直接添加进去");
        objects.add(o);
        return o;
    }

    public List get(String key) {
    return containerMap.get(key);
    }

    public Object modify(String key, String idVal, Object o) throws NoSuchFieldException, IllegalAccessException {
        int index = getIndex(key, idMap.get(key), idVal);
        List<Object> objects = containerMap.get(key);
        objects.set(index,o);
        return o;
    }

    public boolean closeService() {
        destroy();
        return true;
    }

    /**
     * 根据id获得指定key的对象
     * @param key
     * @param idVal
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public Object get(String key, String idVal) throws NoSuchFieldException, IllegalAccessException {
        //先获得id的名字
        String s = idMap.get(key);
        List list = get(key);
        for(Object obj:list){
            if(getVal(obj,s).equals(idVal))
                return obj;
        }
        return null;
    }

    /**
     * 设置指定key的id字段
     * @param key
     * @param idName
     * @return
     */
    public boolean setId(String key, String idName) {
        idMap.put(key,idName);
        return true;
    }

    /**
     * 获得指定对象指定字段的值
     * @param obj
     * @return
     */
    private String getVal(Object obj,String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field declaredField = obj.getClass().getDeclaredField(fieldName);
        declaredField.setAccessible(true);
        return ValUtil.convertToString(declaredField.get(obj));
    }

    /**
     * 获得指定key使用了指定id的对象索引
     * @param key
     * @param idName
     * @param idVal
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private int getIndex(String key,String idName,String idVal) throws NoSuchFieldException, IllegalAccessException {
        List<Object> objects = containerMap.get(key);
        for(int i=0;i<objects.size();i++){
            if(getVal(objects.get(i),idName).equals(idVal))
                return i;
        }
        return -1;
    }


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        new RedisServer().init(8888);
    }
}
