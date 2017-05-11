package com.jerryl.auth.util;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by liuruijie on 2017/4/11.
 */
public class RedisUtil {
    private static Map<String, Object> attributes = new ConcurrentHashMap<>();

    public static void put(String key, Serializable value){
        attributes.put(key, value);
    }

    public static Object get(String key){
        return attributes.get(key);
    }

    public static boolean contains(String key){
        return attributes.containsKey(key);
    }

    public static Object remove(String key){
        return attributes.remove(key);
    }
}
