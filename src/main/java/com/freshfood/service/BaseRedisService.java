package com.freshfood.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface BaseRedisService {
    void save(String key, Object value);

//    void setTimeToLive(String key, long timeoutInDay);
//
//    void hashSave(String key, String field, Object value);
//
//    boolean hashExist(String key, String field);
//
//    Object get(String key);
//
//    public Map<String, Object> getField(String key);
//
//    Object hashGet(String key, String field);
//
//    List<Object> hashGetByFieldPrefix(String key, String fieldPrefix);
//
//    Set<String> getFieldPrefix(String key);
//
//    void delete(String key);
//
//    void delete(String key, String field);
//
//    void delete(String key, List<String> field);

    void clear();
}
