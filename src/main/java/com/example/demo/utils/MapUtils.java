package com.example.demo.utils;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Map工具类
 * 提供常用的Map处理方法
 */
public class MapUtils {

    /**
     * 判断Map是否为空
     * @param map 待判断的Map
     * @return 是否为空
     */
    public static <K, V> boolean isEmpty(Map<K, V> map) {
        return map == null || map.isEmpty();
    }

    /**
     * 判断Map是否不为空
     * @param map 待判断的Map
     * @return 是否不为空
     */
    public static <K, V> boolean isNotEmpty(Map<K, V> map) {
        return !isEmpty(map);
    }

    /**
     * 获取Map的第一个键
     * @param map 源Map
     * @return 第一个键，如果Map为空则返回null
     */
    public static <K, V> K getFirstKey(Map<K, V> map) {
        return isEmpty(map) ? null : map.keySet().iterator().next();
    }

    /**
     * 获取Map的第一个值
     * @param map 源Map
     * @return 第一个值，如果Map为空则返回null
     */
    public static <K, V> V getFirstValue(Map<K, V> map) {
        return isEmpty(map) ? null : map.values().iterator().next();
    }

    /**
     * 获取Map的所有键
     * @param map 源Map
     * @return 键的Set集合
     */
    public static <K, V> Set<K> getKeys(Map<K, V> map) {
        return isEmpty(map) ? new HashSet<>() : new HashSet<>(map.keySet());
    }

    /**
     * 获取Map的所有值
     * @param map 源Map
     * @return 值的List集合
     */
    public static <K, V> List<V> getValues(Map<K, V> map) {
        return isEmpty(map) ? new ArrayList<>() : new ArrayList<>(map.values());
    }

    /**
     * 将Map转换为List
     * @param map 源Map
     * @param mapper 转换函数
     * @return 转换后的List
     */
    public static <K, V, R> List<R> toList(Map<K, V> map, Function<Map.Entry<K, V>, R> mapper) {
        return isEmpty(map) ? new ArrayList<>() : map.entrySet().stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    /**
     * 将Map的键转换为List
     * @param map 源Map
     * @param mapper 转换函数
     * @return 转换后的List
     */
    public static <K, V, R> List<R> keysToList(Map<K, V> map, Function<K, R> mapper) {
        return isEmpty(map) ? new ArrayList<>() : map.keySet().stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    /**
     * 将Map的值转换为List
     * @param map 源Map
     * @param mapper 转换函数
     * @return 转换后的List
     */
    public static <K, V, R> List<R> valuesToList(Map<K, V> map, Function<V, R> mapper) {
        return isEmpty(map) ? new ArrayList<>() : map.values().stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    /**
     * 过滤Map
     * @param map 源Map
     * @param predicate 过滤条件
     * @return 过滤后的Map
     */
    public static <K, V> Map<K, V> filter(Map<K, V> map, Predicate<Map.Entry<K, V>> predicate) {
        return isEmpty(map) ? new HashMap<>() : map.entrySet().stream()
                .filter(predicate)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * 转换Map的键
     * @param map 源Map
     * @param keyMapper 键转换函数
     * @return 转换后的Map
     */
    public static <K, V, R> Map<R, V> transformKeys(Map<K, V> map, Function<K, R> keyMapper) {
        return isEmpty(map) ? new HashMap<>() : map.entrySet().stream()
                .collect(Collectors.toMap(e -> keyMapper.apply(e.getKey()), Map.Entry::getValue));
    }

    /**
     * 转换Map的值
     * @param map 源Map
     * @param valueMapper 值转换函数
     * @return 转换后的Map
     */
    public static <K, V, R> Map<K, R> transformValues(Map<K, V> map, Function<V, R> valueMapper) {
        return isEmpty(map) ? new HashMap<>() : map.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> valueMapper.apply(e.getValue())));
    }

    /**
     * 将Map按值排序
     * @param map 源Map
     * @param comparator 比较器
     * @return 排序后的Map
     */
    public static <K, V> Map<K, V> sortByValue(Map<K, V> map, Comparator<V> comparator) {
        if (isEmpty(map)) {
            return new HashMap<>();
        }
        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(comparator))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
    }

    /**
     * 将Map按键排序
     * @param map 源Map
     * @param comparator 比较器
     * @return 排序后的Map
     */
    public static <K, V> Map<K, V> sortByKey(Map<K, V> map, Comparator<K> comparator) {
        if (isEmpty(map)) {
            return new HashMap<>();
        }
        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(comparator))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
    }

    /**
     * 将Map转换为字符串
     * @param map 源Map
     * @param keyValueSeparator 键值分隔符
     * @param entrySeparator 条目分隔符
     * @return 转换后的字符串
     */
    public static <K, V> String toString(Map<K, V> map, String keyValueSeparator, String entrySeparator) {
        if (isEmpty(map)) {
            return "";
        }
        return map.entrySet().stream()
                .map(e -> e.getKey() + keyValueSeparator + e.getValue())
                .collect(Collectors.joining(entrySeparator));
    }

    /**
     * 将Map转换为Properties
     * @param map 源Map
     * @return Properties对象
     */
    public static Properties toProperties(Map<String, String> map) {
        Properties properties = new Properties();
        if (isNotEmpty(map)) {
            properties.putAll(map);
        }
        return properties;
    }

    /**
     * 将Properties转换为Map
     * @param properties 源Properties
     * @return Map对象
     */
    public static Map<String, String> fromProperties(Properties properties) {
        Map<String, String> map = new HashMap<>();
        if (properties != null) {
            for (String key : properties.stringPropertyNames()) {
                map.put(key, properties.getProperty(key));
            }
        }
        return map;
    }
} 