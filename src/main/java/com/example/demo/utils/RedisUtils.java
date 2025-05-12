package com.example.demo.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 * 提供常用的Redis操作方法
 */
@Component
public class RedisUtils {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisUtils(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 设置缓存
     * @param key 键
     * @param value 值
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置缓存并设置过期时间
     * @param key 键
     * @param value 值
     * @param timeout 过期时间
     * @param unit 时间单位
     */
    public void set(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 获取缓存
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除缓存
     * @param key 键
     * @return 是否成功
     */
    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 批量删除缓存
     * @param keys 键集合
     * @return 成功删除的数量
     */
    public Long delete(Collection<String> keys) {
        return redisTemplate.delete(keys);
    }

    /**
     * 设置过期时间
     * @param key 键
     * @param timeout 过期时间
     * @param unit 时间单位
     * @return 是否成功
     */
    public Boolean expire(String key, long timeout, TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * 获取过期时间
     * @param key 键
     * @return 过期时间
     */
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 判断key是否存在
     * @param key 键
     * @return 是否存在
     */
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 递增
     * @param key 键
     * @param delta 递增因子
     * @return 递增后的值
     */
    public Long increment(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     * @param key 键
     * @param delta 递减因子
     * @return 递减后的值
     */
    public Long decrement(String key, long delta) {
        return redisTemplate.opsForValue().decrement(key, delta);
    }

    /**
     * 获取Hash中的值
     * @param key 键
     * @param hashKey Hash键
     * @return Hash值
     */
    public Object hGet(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 设置Hash值
     * @param key 键
     * @param hashKey Hash键
     * @param value 值
     */
    public void hSet(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    /**
     * 设置Hash值并设置过期时间
     * @param key 键
     * @param hashKey Hash键
     * @param value 值
     * @param timeout 过期时间
     * @param unit 时间单位
     */
    public void hSet(String key, String hashKey, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForHash().put(key, hashKey, value);
        redisTemplate.expire(key, timeout, unit);
    }

    /**
     * 获取Hash中的所有值
     * @param key 键
     * @return Hash中的所有值
     */
    public Map<Object, Object> hGetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 设置Hash中的所有值
     * @param key 键
     * @param map 值
     */
    public void hSetAll(String key, Map<String, Object> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 设置Hash中的所有值并设置过期时间
     * @param key 键
     * @param map 值
     * @param timeout 过期时间
     * @param unit 时间单位
     */
    public void hSetAll(String key, Map<String, Object> map, long timeout, TimeUnit unit) {
        redisTemplate.opsForHash().putAll(key, map);
        redisTemplate.expire(key, timeout, unit);
    }

    /**
     * 删除Hash中的值
     * @param key 键
     * @param hashKeys Hash键
     * @return 成功删除的数量
     */
    public Long hDelete(String key, Object... hashKeys) {
        return redisTemplate.opsForHash().delete(key, hashKeys);
    }

    /**
     * 判断Hash中是否存在指定的Hash键
     * @param key 键
     * @param hashKey Hash键
     * @return 是否存在
     */
    public Boolean hHasKey(String key, String hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    /**
     * Hash递增
     * @param key 键
     * @param hashKey Hash键
     * @param delta 递增因子
     * @return 递增后的值
     */
    public Long hIncrement(String key, String hashKey, long delta) {
        return redisTemplate.opsForHash().increment(key, hashKey, delta);
    }

    /**
     * Hash递减
     * @param key 键
     * @param hashKey Hash键
     * @param delta 递减因子
     * @return 递减后的值
     */
    public Long hDecrement(String key, String hashKey, long delta) {
        return redisTemplate.opsForHash().increment(key, hashKey, -delta);
    }

    /**
     * 获取Set中的所有值
     * @param key 键
     * @return Set中的所有值
     */
    public Set<Object> sMembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 判断Set中是否存在指定的值
     * @param key 键
     * @param value 值
     * @return 是否存在
     */
    public Boolean sIsMember(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 向Set中添加值
     * @param key 键
     * @param values 值
     * @return 成功添加的数量
     */
    public Long sAdd(String key, Object... values) {
        return redisTemplate.opsForSet().add(key, values);
    }

    /**
     * 向Set中添加值并设置过期时间
     * @param key 键
     * @param timeout 过期时间
     * @param unit 时间单位
     * @param values 值
     * @return 成功添加的数量
     */
    public Long sAdd(String key, long timeout, TimeUnit unit, Object... values) {
        Long count = redisTemplate.opsForSet().add(key, values);
        redisTemplate.expire(key, timeout, unit);
        return count;
    }

    /**
     * 获取Set的长度
     * @param key 键
     * @return Set的长度
     */
    public Long sSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * 从Set中删除值
     * @param key 键
     * @param values 值
     * @return 成功删除的数量
     */
    public Long sRemove(String key, Object... values) {
        return redisTemplate.opsForSet().remove(key, values);
    }

    /**
     * 获取List中的值
     * @param key 键
     * @param start 开始索引
     * @param end 结束索引
     * @return List中的值
     */
    public List<Object> lRange(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 获取List的长度
     * @param key 键
     * @return List的长度
     */
    public Long lSize(String key) {
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 获取List中指定索引的值
     * @param key 键
     * @param index 索引
     * @return 值
     */
    public Object lIndex(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * 向List中添加值
     * @param key 键
     * @param value 值
     * @return List的长度
     */
    public Long lRightPush(String key, Object value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 向List中添加值并设置过期时间
     * @param key 键
     * @param value 值
     * @param timeout 过期时间
     * @param unit 时间单位
     * @return List的长度
     */
    public Long lRightPush(String key, Object value, long timeout, TimeUnit unit) {
        Long size = redisTemplate.opsForList().rightPush(key, value);
        redisTemplate.expire(key, timeout, unit);
        return size;
    }

    /**
     * 向List中批量添加值
     * @param key 键
     * @param values 值
     * @return List的长度
     */
    public Long lRightPushAll(String key, Object... values) {
        return redisTemplate.opsForList().rightPushAll(key, values);
    }

    /**
     * 向List中批量添加值并设置过期时间
     * @param key 键
     * @param timeout 过期时间
     * @param unit 时间单位
     * @param values 值
     * @return List的长度
     */
    public Long lRightPushAll(String key, long timeout, TimeUnit unit, Object... values) {
        Long size = redisTemplate.opsForList().rightPushAll(key, values);
        redisTemplate.expire(key, timeout, unit);
        return size;
    }

    /**
     * 从List中删除值
     * @param key 键
     * @param count 数量
     * @param value 值
     * @return 成功删除的数量
     */
    public Long lRemove(String key, long count, Object value) {
        return redisTemplate.opsForList().remove(key, count, value);
    }
} 