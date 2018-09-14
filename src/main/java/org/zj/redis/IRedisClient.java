package org.zj.redis;

import java.io.IOException;
import java.util.List;

public interface IRedisClient<T> {
    T add(String key,T t) throws IOException;
    List<T> get(String key);
    T modify(String key,String id,T t) throws NoSuchFieldException, IllegalAccessException;
    boolean closeService();
    T get(String key,String id) throws NoSuchFieldException, IllegalAccessException;
    boolean setId(String key,String idName);
    
}
