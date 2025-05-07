package com.freshfood.service.impl;

import com.freshfood.service.BaseRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class BaseRedisServiceImpl implements BaseRedisService {
    private final RedisTemplate<String, Object> redisTemplate;
//    private final HashOperations<String, String, Object> hashOperations;
    @Override
    public void save(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }
//
//    @Override
//    public void setTimeToLive(String key, long timeoutInDay) {
//        redisTemplate.expire(key, timeoutInDay, TimeUnit.DAYS);
//    }
//
//    @Override
//    public void hashSave(String key, String field, Object value) {
//        hashOperations.put(key, field, value);
//    }
//
//    @Override
//    public boolean hashExist(String key, String field) {
//        return hashOperations.hasKey(key, field);
//    }
//
//    @Override
//    public Object get(String key) {
//        return redisTemplate.opsForValue().get(key);
//    }
//
//    @Override
//    public Map<String, Object> getField(String key) {
//        return hashOperations.entries(key);
//    }
//
//    @Override
//    public Object hashGet(String key, String field) {
//        return hashOperations.get(key, field);
//    }
//
//    @Override
//    public List<Object> hashGetByFieldPrefix(String key, String fieldPrefix) {
//        List<Object> object = new ArrayList<>();
//
//        Map<String, Object> hashEntries = hashOperations.entries(key);
//
//        for(Map.Entry<String,Object> entry : hashEntries.entrySet()){
//            if(entry.getKey().startsWith(fieldPrefix)){
//                object.add(entry.getValue());
//            }
//        }
//        return object;
//    }
//
//    @Override
//    public Set<String> getFieldPrefix(String key) {
//        return hashOperations.entries(key).keySet();
//    }
//
//    @Override
//    public void delete(String key) {
//        redisTemplate.delete(key);
//    }
//
//    @Override
//    public void delete(String key, String field) {
//        hashOperations.delete(key, field);
//    }
//
//    @Override
//    public void delete(String key, List<String> field) {
//        for(String s : field){
//            hashOperations.delete(key, s);
//        }
//    }

    @Override
    public void clear() {
        redisTemplate.getConnectionFactory().getConnection().flushAll();
    }
}
