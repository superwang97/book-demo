package com.example.demo.utils;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * List工具类
 * 提供常用的List处理方法
 */
public class ListUtils {

    /**
     * 判断List是否为空
     * @param list 待判断的List
     * @return 是否为空
     */
    public static <T> boolean isEmpty(List<T> list) {
        return list == null || list.isEmpty();
    }

    /**
     * 判断List是否不为空
     * @param list 待判断的List
     * @return 是否不为空
     */
    public static <T> boolean isNotEmpty(List<T> list) {
        return !isEmpty(list);
    }

    /**
     * 获取List的第一个元素
     * @param list 源List
     * @return 第一个元素，如果List为空则返回null
     */
    public static <T> T getFirst(List<T> list) {
        return isEmpty(list) ? null : list.get(0);
    }

    /**
     * 获取List的最后一个元素
     * @param list 源List
     * @return 最后一个元素，如果List为空则返回null
     */
    public static <T> T getLast(List<T> list) {
        return isEmpty(list) ? null : list.get(list.size() - 1);
    }

    /**
     * 将List转换为Set
     * @param list 源List
     * @return 转换后的Set
     */
    public static <T> Set<T> toSet(List<T> list) {
        return isEmpty(list) ? new HashSet<>() : new HashSet<>(list);
    }

    /**
     * 将List转换为Map
     * @param list 源List
     * @param keyMapper 键映射函数
     * @param valueMapper 值映射函数
     * @return 转换后的Map
     */
    public static <T, K, V> Map<K, V> toMap(List<T> list, Function<T, K> keyMapper, Function<T, V> valueMapper) {
        return isEmpty(list) ? new HashMap<>() : list.stream()
                .collect(Collectors.toMap(keyMapper, valueMapper, (v1, v2) -> v1));
    }

    /**
     * 将List按指定条件分组
     * @param list 源List
     * @param classifier 分组函数
     * @return 分组后的Map
     */
    public static <T, K> Map<K, List<T>> groupBy(List<T> list, Function<T, K> classifier) {
        return isEmpty(list) ? new HashMap<>() : list.stream()
                .collect(Collectors.groupingBy(classifier));
    }

    /**
     * 过滤List
     * @param list 源List
     * @param predicate 过滤条件
     * @return 过滤后的List
     */
    public static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
        return isEmpty(list) ? new ArrayList<>() : list.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    /**
     * 转换List中的元素
     * @param list 源List
     * @param mapper 转换函数
     * @return 转换后的List
     */
    public static <T, R> List<R> map(List<T> list, Function<T, R> mapper) {
        return isEmpty(list) ? new ArrayList<>() : list.stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    /**
     * 对List进行排序
     * @param list 源List
     * @param comparator 比较器
     * @return 排序后的List
     */
    public static <T> List<T> sort(List<T> list, Comparator<T> comparator) {
        if (isEmpty(list)) {
            return new ArrayList<>();
        }
        List<T> result = new ArrayList<>(list);
        result.sort(comparator);
        return result;
    }

    /**
     * 获取List中的最大值
     * @param list 源List
     * @param comparator 比较器
     * @return 最大值，如果List为空则返回null
     */
    public static <T> T max(List<T> list, Comparator<T> comparator) {
        return isEmpty(list) ? null : Collections.max(list, comparator);
    }

    /**
     * 获取List中的最小值
     * @param list 源List
     * @param comparator 比较器
     * @return 最小值，如果List为空则返回null
     */
    public static <T> T min(List<T> list, Comparator<T> comparator) {
        return isEmpty(list) ? null : Collections.min(list, comparator);
    }

    /**
     * 将List分页
     * @param list 源List
     * @param pageSize 每页大小
     * @param pageNum 页码（从1开始）
     * @return 分页后的List
     */
    public static <T> List<T> page(List<T> list, int pageSize, int pageNum) {
        if (isEmpty(list)) {
            return new ArrayList<>();
        }
        int fromIndex = (pageNum - 1) * pageSize;
        if (fromIndex >= list.size()) {
            return new ArrayList<>();
        }
        int toIndex = Math.min(fromIndex + pageSize, list.size());
        return list.subList(fromIndex, toIndex);
    }

    /**
     * 将List去重
     * @param list 源List
     * @return 去重后的List
     */
    public static <T> List<T> distinct(List<T> list) {
        return isEmpty(list) ? new ArrayList<>() : list.stream()
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * 将List转换为字符串
     * @param list 源List
     * @param separator 分隔符
     * @return 转换后的字符串
     */
    public static <T> String join(List<T> list, String separator) {
        return isEmpty(list) ? "" : list.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(separator));
    }
} 