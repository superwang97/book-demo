package com.example.demo.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

/**
 * 日期工具类
 * 提供常用的日期处理方法
 */
public class DateUtils {
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 获取当前日期
     * @return 当前日期
     */
    public static LocalDate getCurrentDate() {
        return LocalDate.now();
    }

    /**
     * 获取当前时间
     * @return 当前时间
     */
    public static LocalTime getCurrentTime() {
        return LocalTime.now();
    }

    /**
     * 获取当前日期时间
     * @return 当前日期时间
     */
    public static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }

    /**
     * 日期转字符串
     * @param date 日期
     * @return 格式化后的日期字符串
     */
    public static String formatDate(LocalDate date) {
        return date != null ? date.format(DATE_FORMATTER) : "";
    }

    /**
     * 时间转字符串
     * @param time 时间
     * @return 格式化后的时间字符串
     */
    public static String formatTime(LocalTime time) {
        return time != null ? time.format(TIME_FORMATTER) : "";
    }

    /**
     * 日期时间转字符串
     * @param dateTime 日期时间
     * @return 格式化后的日期时间字符串
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DATETIME_FORMATTER) : "";
    }

    /**
     * 字符串转日期
     * @param dateStr 日期字符串
     * @return 日期对象
     */
    public static LocalDate parseDate(String dateStr) {
        return LocalDate.parse(dateStr, DATE_FORMATTER);
    }

    /**
     * 字符串转时间
     * @param timeStr 时间字符串
     * @return 时间对象
     */
    public static LocalTime parseTime(String timeStr) {
        return LocalTime.parse(timeStr, TIME_FORMATTER);
    }

    /**
     * 字符串转日期时间
     * @param dateTimeStr 日期时间字符串
     * @return 日期时间对象
     */
    public static LocalDateTime parseDateTime(String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr, DATETIME_FORMATTER);
    }

    /**
     * 获取指定日期的开始时间
     * @param date 日期
     * @return 日期时间对象
     */
    public static LocalDateTime getStartOfDay(LocalDate date) {
        return date.atStartOfDay();
    }

    /**
     * 获取指定日期的结束时间
     * @param date 日期
     * @return 日期时间对象
     */
    public static LocalDateTime getEndOfDay(LocalDate date) {
        return date.atTime(LocalTime.MAX);
    }

    /**
     * 获取指定日期所在月份的第一天
     * @param date 日期
     * @return 日期对象
     */
    public static LocalDate getFirstDayOfMonth(LocalDate date) {
        return date.with(TemporalAdjusters.firstDayOfMonth());
    }

    /**
     * 获取指定日期所在月份的最后一天
     * @param date 日期
     * @return 日期对象
     */
    public static LocalDate getLastDayOfMonth(LocalDate date) {
        return date.with(TemporalAdjusters.lastDayOfMonth());
    }

    /**
     * 计算两个日期之间的天数
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 天数
     */
    public static long daysBetween(LocalDate startDate, LocalDate endDate) {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    /**
     * 计算两个日期时间之间的小时数
     * @param startDateTime 开始日期时间
     * @param endDateTime 结束日期时间
     * @return 小时数
     */
    public static long hoursBetween(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return ChronoUnit.HOURS.between(startDateTime, endDateTime);
    }

    /**
     * 判断日期是否在指定范围内
     * @param date 待判断的日期
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 是否在范围内
     */
    public static boolean isDateInRange(LocalDate date, LocalDate startDate, LocalDate endDate) {
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }

    /**
     * 判断日期时间是否在指定范围内
     * @param dateTime 待判断的日期时间
     * @param startDateTime 开始日期时间
     * @param endDateTime 结束日期时间
     * @return 是否在范围内
     */
    public static boolean isDateTimeInRange(LocalDateTime dateTime, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return !dateTime.isBefore(startDateTime) && !dateTime.isAfter(endDateTime);
    }
} 