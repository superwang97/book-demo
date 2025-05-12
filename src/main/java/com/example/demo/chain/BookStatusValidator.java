package com.example.demo.chain;

import com.example.demo.model.Book;
import com.example.demo.model.BookStatus;

/**
 * 图书状态验证器接口
 * 使用责任链模式实现图书状态变更的验证
 * 每个具体的验证器都需要实现这个接口
 */
public interface BookStatusValidator {
    /**
     * 验证图书状态变更是否合法
     * @param book 待验证的图书
     * @param newStatus 新的状态
     * @throws IllegalStateException 当状态变更不合法时抛出此异常
     */
    void validate(Book book, BookStatus newStatus);

    /**
     * 设置责任链中的下一个验证器
     * @param next 下一个验证器
     */
    void setNext(BookStatusValidator next);
} 